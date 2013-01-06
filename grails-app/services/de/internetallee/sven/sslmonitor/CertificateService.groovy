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

import javax.net.ssl.SSLSession
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import java.security.MessageDigest
import java.security.cert.Certificate

class CertificateService {

    SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault()

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

    def getX509CertificatesInformation(MonitoredServer server) {
        SSLSocket sslSocket = sslSocketFactory.createSocket(server.hostname, server.port) as SSLSocket
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
        MonitoredServer.findAll().each { server ->
            if (server.certificateInformationChain)
                server.certificateInformationChain.clear()
            else
                server.certificateInformationChain = []
            server.certificateInformationChain.addAll(getX509CertificatesInformation(server))
            server.save()
        }
    }
}
