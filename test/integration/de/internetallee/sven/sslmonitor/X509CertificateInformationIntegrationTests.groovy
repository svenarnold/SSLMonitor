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

import org.joda.time.DateTime
import org.joda.time.Period
import org.junit.After
import org.junit.Before
import org.junit.Test

import static junit.framework.Assert.assertEquals
import static junit.framework.Assert.assertTrue

class X509CertificateInformationIntegrationTests {

    @Before
    void setUp() {
        def currentDateTime = new DateTime()
        def monitoredServer = new MonitoredServer(name: 'example', hostname: 'host', port: 443)

        monitoredServer.addToCertificateInformationChain(
                subjectPrincipal: 'cert0', issuerDN: 'me',
                sha1Fingerprint: 'cert0sha1', md5Fingerprint: 'cert0md5',
                validNotBefore: currentDateTime,
                validNotAfter: currentDateTime.plus(Period.days(9)),
        )

        monitoredServer.addToCertificateInformationChain(
                subjectPrincipal: 'cert1', issuerDN: 'me',
                sha1Fingerprint: 'cert1sha1', md5Fingerprint: 'cert1md5',
                validNotBefore: currentDateTime,
                validNotAfter: currentDateTime.plus(Period.days(10))
        )

        monitoredServer.addToCertificateInformationChain(
                subjectPrincipal: 'cert2', issuerDN: 'me',
                sha1Fingerprint: 'cert2sha1', md5Fingerprint: 'cert2md5',
                validNotBefore: currentDateTime,
                validNotAfter: currentDateTime.plus(Period.days(11))
        )

        assert monitoredServer.save()
    }

    @After
    void tearDown() {
        MonitoredServer.list().each { it.delete() }
    }

    @Test
    void testSetup() {
        assertEquals 1, MonitoredServer.count()
        assertEquals 3, X509CertificateInformation.count()
    }

    @Test
    void testCertificatesDueInDays() {
        assertEquals 'All three certificates should be due in 11 days.', 3, X509CertificateInformation.certificatesDueInDays(11).count()

        def dueCerts = X509CertificateInformation.certificatesDueInDays(10)
        assertEquals 'Two certificates should be due in 10 days.', 2, dueCerts.count()
        assertTrue 'Cert0 should be due in 10 days.', dueCerts.list().contains(X509CertificateInformation.findBySubjectPrincipal('cert0'))

        assertEquals 'Only one certificate should be due in 9 days.', 1, X509CertificateInformation.certificatesDueInDays(9).count()

        assertEquals 'All certificates should be ok for the next 8 days.', 0, X509CertificateInformation.certificatesDueInDays(8).count()
    }
}
