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

import junit.framework.Assert
import org.joda.time.DateTime
import org.joda.time.Period
import org.junit.After
import org.junit.Before
import org.junit.Test

import static junit.framework.Assert.*
import static junit.framework.Assert.assertEquals
import static junit.framework.Assert.assertTrue

class X509CertificateInformationIntegrationTests {

    @Before
    void setUp() {
        def monitoredServer = new MonitoredServer(
                name: 'example',
                hostname: 'host',
                port: 443
        )
        assert monitoredServer.save()

        def currentDateTime = new DateTime()

        def cert0 = new X509CertificateInformation(
                subjectPrincipal: 'cert0', issuerDN: 'me',
                sha1Fingerprint: 'cert0sha1', md5Fingerprint: 'cert0md5',
                validNotBefore: currentDateTime,
                validNotAfter: currentDateTime.plus(Period.days(9)),
        )
        monitoredServer.addToCertificateInformationChain(cert0)

        def cert1 = new X509CertificateInformation(
                subjectPrincipal: 'cert1', issuerDN: 'me',
                sha1Fingerprint: 'cert1sha1', md5Fingerprint: 'cert1md5',
                validNotBefore: currentDateTime,
                validNotAfter: currentDateTime.plus(Period.days(10))
        )
        monitoredServer.addToCertificateInformationChain(cert1)

        def cert2 = new X509CertificateInformation(
                subjectPrincipal: 'cert2', issuerDN: 'me',
                sha1Fingerprint: 'cert2sha1', md5Fingerprint: 'cert2md5',
                validNotBefore: currentDateTime,
                validNotAfter: currentDateTime.plus(Period.days(11))
        )
        monitoredServer.addToCertificateInformationChain(cert2)
        assert monitoredServer.save()
    }

    @After
    void tearDown() {
        MonitoredServer.list().each { it.delete() }
    }

    @Test
    void testCertificatesDueInDays() {
        def dueCerts = X509CertificateInformation.certificatesDueInDays(10)
        assertEquals 'Two certificates should be due.', 2, dueCerts.count()
        assertTrue 'Cert0 should be due.', dueCerts.list().contains(X509CertificateInformation.findBySubjectPrincipal('cert0'))
    }

}
