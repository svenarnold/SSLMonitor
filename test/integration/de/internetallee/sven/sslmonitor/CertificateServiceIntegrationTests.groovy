/*
 * This file is part of SSLMonitor
 *
 * Copyright (c) 2014 Sven Arnold
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

import static org.junit.Assert.*
import org.junit.*

class CertificateServiceIntegrationTests {

    def certificateService

    @Test
    void testUpdateAllCertificateChainsWithoutServers() {
        certificateService.updateAllCertificateChains()
    }

    @Test
    void testUpdateAllCertificateChainsDoesNotDuplicateData() {
        def server = new MonitoredServer(name: 'GitHub', hostname: 'github.com', port: 443)
        assert server.save()

        certificateService.updateAllCertificateChains()
        assertEquals 'There must be exactly 2 certificates in the database', 2, X509CertificateInformation.count()

        certificateService.updateAllCertificateChains()
        assertEquals 'There *still* must be exactly 2 certificates in the database', 2, X509CertificateInformation.count()

        server.hostname = 'www.mozilla.org'
        server.save()
        assertEquals 'There must be still one server in the database', 1, MonitoredServer.count()

        certificateService.updateAllCertificateChains()
        assertEquals 'There must be exactly 2 certificates in the database', 2, X509CertificateInformation.count()
    }
}
