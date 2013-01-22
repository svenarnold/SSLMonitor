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

import de.internetallee.sslmonitor.CheckCertificatesJob
import grails.plugin.mail.MailService
import grails.test.mixin.Mock
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.joda.time.DateTime
import org.joda.time.Period
import org.junit.After
import org.junit.Before
import org.junit.Test

@TestMixin(GrailsUnitTestMixin)
@Mock([MonitoredServer, X509CertificateInformation, MailService])
class CheckCertificatesJobTests {

    @Before
    def void setUp() {
        grailsApplication.config.sslMonitor.notification.intervalInDays = 10

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

        assert monitoredServer.save()
    }

    @After
    def void tearDown() {
        MonitoredServer.list().each { it.delete() }
        X509CertificateInformation.list().each { it.delete() }
    }

    @Test
    def void testExecute() {
        def certificateServiceMockControl = mockFor(CertificateService)
        certificateServiceMockControl.demand.updateAllCertificateChains(1) {}

        def mailServiceMockControl = mockFor(MailService)
        mailServiceMockControl.demand.sendMail(1) { to, subject, html -> }

        def job = new CheckCertificatesJob()
        job.certificateService = certificateServiceMockControl.createMock()
        job.mailService = mailServiceMockControl.createMock()

        job.execute()

        certificateServiceMockControl.verify()
        mailServiceMockControl.verify()
    }
}
