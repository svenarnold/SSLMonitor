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
import org.joda.time.DateTime
import org.junit.*

@TestFor(ServiceCertificateLink)
@Mock([MonitoredServer, X509CertificateInformation])
class ServiceCertificateLinkTests {

    @Test
    void testCreate() {
        def server = new MonitoredServer(name: 'Sample Service', hostname: 'localhost', port: 443)
        assert server.save()
        def certificate = new X509CertificateInformation(
                subjectPrincipal: 'principal', issuerDN: 'issuer',
                sha1Fingerprint: 'sha1', md5Fingerprint: 'md5',
                validNotAfter: new DateTime(), validNotBefore: new DateTime())
        assert certificate.save()
        def link = ServiceCertificateLink.create(server, certificate)
        assert link.save(flush: true)

        assertEquals(1, server.certificateInformationChain.size())
        assertEquals(1, certificate.serviceCertificateLinks.size())
    }
}
