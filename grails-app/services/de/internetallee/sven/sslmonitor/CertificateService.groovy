/*
 * This file is part of SSLMonitor
 *
 * Copyright (c) 2013 Sven Arnold
 *
 * SSLMonitor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SSLMonitor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SSLMonitor. If not, see <http://www.gnu.org/licenses/>.
 */

package de.internetallee.sven.sslmonitor

import org.apache.log4j.LogManager
import org.joda.time.DateTime

import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import java.security.MessageDigest
import java.security.cert.Certificate

class CertificateService {

    def grailsApplication

    private SSLSocketFactory sslSocketFactory

    def getSSLSocketFactory() {
        if (!sslSocketFactory) {
            SSLContext insecureSSLContext = SSLContext.getInstance("TLS")
            def insecureTrustManager = new InsecureTrustManager()
            insecureSSLContext.init(null, (TrustManager[]) [insecureTrustManager].toArray(), null)
            sslSocketFactory = insecureSSLContext.socketFactory
        }
        sslSocketFactory
    }

    def sha1HexString(byte[] data) {
        MessageDigest sha1Digest = MessageDigest.getInstance("SHA1")
        sha1Digest.update(data)
        sha1Digest.digest().encodeHex().toString()
    }

    def md5HexString(byte[] data) {
        MessageDigest md5Digest = MessageDigest.getInstance("MD5")
        md5Digest.update(data)
        md5Digest.digest().encodeHex().toString()
    }

    def getX509CertificatesForService(MonitoredServer server) throws IOException, UnknownHostException {
        def timeoutInMillis = grailsApplication.config.sslMonitor.timeoutInMillis ?: 5000
        log.debug("  Socket timeout set to ${timeoutInMillis} milliseconds.")

        SSLSocket sslSocket = getSSLSocketFactory().createSocket() as SSLSocket
        sslSocket.connect(new InetSocketAddress(server.hostname, server.port), timeoutInMillis)
        sslSocket.startHandshake();
        SSLSession session = sslSocket.getSession();

        Certificate[] serverCertificates = session.getPeerCertificates();
        serverCertificates.findAll { "X.509".equals(it.type) }
    }

    def getX509CertificatesInformation(MonitoredServer server) throws IOException, UnknownHostException {
        def certificateInformations = []

        getX509CertificatesForService(server).each { cert ->
            def certInfo = X509CertificateInformation.findBySha1Fingerprint(sha1HexString(cert.encoded))
            if (! certInfo) {
                certInfo = new X509CertificateInformation(
                        subjectPrincipal: cert.subjectX500Principal.name,
                        issuerDN: cert.issuerDN.name,
                        sha1Fingerprint: sha1HexString(cert.encoded),
                        md5Fingerprint: md5HexString(cert.encoded),
                        validNotBefore: new DateTime(cert.notBefore),
                        validNotAfter: new DateTime(cert.notAfter)
                )
                assert certInfo.save()
            }
            certificateInformations.add(certInfo)
        }
        certificateInformations
    }

    def cleanupCertificates() {

        log.info 'Deleting stale certificate informations'

        X509CertificateInformation.findAll("\
                from X509CertificateInformation xci \
                left outer join xci.serviceCertificateLinks li \
                where li is null \
        ").each {
            it[0].delete()
        }

        // I would prefer to perform a bulk delete but it seems that is not possible because of
        // this bug in hibernate (which is not fixed in 3.6:
        // https://hibernate.atlassian.net/browse/HHH-1657
//        X509CertificateInformation.executeUpdate("\
//            delete from X509CertificateInformation xi \
//            where xi.id in \
//                (select xci.id as xcid \
//                from X509CertificateInformation xci \
//                left outer join xci.serviceCertificateLinks li \
//                where li is null) \
//        ")
    }

    def updateAllCertificateChains() {

        log.info ("Updating all certificates.")

        MonitoredServer.list().each { server ->

            log.debug("Updating certificate info for server " + server.hostname)

            try {

                def certificates = getX509CertificatesInformation(server)

                certificates.each { certificate ->
                    log.debug ("examining certificate: " + certificate)
                    def link = ServiceCertificateLink.findByMonitoredServerAndX509CertificateInformation(server, certificate)
                    if (link) {
                        log.debug("  Certificate already in database for this server")
                    } else {
                        link = ServiceCertificateLink.create(server, certificate)
                        log.debug("  Adding new certificate: " + certificate)
                    }
                }

                log.debug("Removing obsolete certificates")
                ServiceCertificateLink.findAllByMonitoredServer(server)
                        .findAll { ! (certificates.contains(it.x509CertificateInformation)) }
                        .each {
                    log.debug("  Removing certificate: " + it.x509CertificateInformation)
                    ServiceCertificateLink.remove(server, it.x509CertificateInformation, true)
                }

                server.connectionSuccess = true
                server.lastError = ''
                server.save()

            }  catch (Exception e) {

                log.warn('Exception caught while trying to retrieve certificate information', e)
                LogManager.getLogger("StackTrace").error('Exception caught while trying to retrieve certificate information:', e)
                switch (e) {
                    case SocketTimeoutException:
                        server.lastError = 'Socket Timeout'
                        break
                    case UnknownHostException:
                        server.lastError = 'Host Unknown'
                        break
                    default:
                        server.lastError = e.toString().truncate(255)
                        break
                }
                server.connectionSuccess = false
            }

            assert server.save(flush: true)
        }

        cleanupCertificates()
    }


}
