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

import grails.test.mixin.*
import org.junit.*

import javax.net.ssl.SSLSocketFactory

@TestFor(CertificateService)
@Mock([MonitoredServer, X509CertificateInformation])
class CertificateServiceTests {

    @Test
    void testGetSSLSocketFactory() {
        def socketFactory = service.getSSLSocketFactory()
        assertNotNull 'Should get an object', socketFactory
        assertTrue 'Should get an instance of SSLSocketFactory', socketFactory instanceof SSLSocketFactory
        assertEquals 'Should get the same instance twice', socketFactory, service.getSSLSocketFactory()
    }

    @Test
    void testSHA1HexString() {
        assertEquals 'a94a8fe5ccb19ba61c4c0873d391e987982fbbd3', service.sha1HexString('test'.getBytes())
    }

    @Test
    void testMD5HexString() {
        assertEquals '098f6bcd4621d373cade4e832627b4f6', service.md5HexString('test'.getBytes())
    }

    @Test
    void testGetX509CertificatesInformation() {
        def certInfos = service.getX509CertificatesInformation(new MonitoredServer(name: 'GitHub', hostname: 'github.com', port: 443))
        assertTrue (certInfos.size > 0)
        assertTrue (certInfos[0] instanceof X509CertificateInformation)
        assertTrue (certInfos[0].subjectPrincipal.startsWith('CN=github.com'))
        assertTrue (certInfos[0].issuerDN.startsWith('CN=DigiCert'))
    }

    @Test(expected = UnknownHostException)
    void testGetX509CertificatesInformationThrowsUnknownHostException() {
        service.getX509CertificatesInformation(new MonitoredServer(name: 'Unknonw', hostname: 'unknown', port: 443))
    }
}
