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
    }
}
