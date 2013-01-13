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
        if (! sslSocketFactory) {
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

    def getX509CertificatesInformation(MonitoredServer server) throws IOException, UnknownHostException {

        def timeoutInMillis = grailsApplication.config.timeoutInMillis ?: 5000

        SSLSocket sslSocket = getSSLSocketFactory().createSocket() as SSLSocket
        sslSocket.connect(new InetSocketAddress(server.hostname, server.port), timeoutInMillis)
        sslSocket.startHandshake();
        SSLSession session = sslSocket.getSession();

        Certificate[] serverCertificates = session.getPeerCertificates();
        serverCertificates.findAll { "X.509".equals(it.type) }.collect { cert ->
            new X509CertificateInformation(
                    subjectPrincipal: cert.subjectX500Principal.name,
                    issuerDN: cert.issuerDN.name,
                    sha1Fingerprint: sha1HexString(cert.encoded),
                    md5Fingerprint: md5HexString(cert.encoded),
                    validNotBefore: cert.notBefore,
                    validNotAfter: cert.notAfter
            )
        }
    }

    def updateAllCertificateChains() {
        MonitoredServer.list().each { server ->
            if (server.certificateInformationChain)
                server.certificateInformationChain.clear()
            try {
                getX509CertificatesInformation(server).each {
                    server.addToCertificateInformationChain(it)
                }
                server.connectionSuccess = true
                server.lastError = ''
            } catch (Exception e) {
                log.warn('Exception caught while trying to retrieve certificate information', e)
                org.apache.log4j.LogManager.getLogger("StackTrace").error ('Exception caught while trying to retrieve certificate information:', e)
                switch (e) {
                    case SocketTimeoutException:
                        server.lastError = 'Socket Timeout'
                        break
                    case UnknownHostException:
                        server.lastError = 'Host Unknown'
                        break
                    default:
                        server.lastError = e.toString()
                        break
                }
                server.connectionSuccess = false
            }
            server.save()
        }
    }
}
