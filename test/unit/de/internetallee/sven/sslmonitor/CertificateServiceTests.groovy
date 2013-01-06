package de.internetallee.sven.sslmonitor



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CertificateService)
class CertificateServiceTests {

    void testSHA1HexString() {
        assertEquals 'a94a8fe5ccb19ba61c4c0873d391e987982fbbd3', service.sha1HexString('test'.getBytes())
    }

    void testMD5HexString() {
        assertEquals '098f6bcd4621d373cade4e832627b4f6', service.md5HexString('test'.getBytes())
    }

    void testGetX509CertificatesInformation() {
        def certInfos = service.getX509CertificatesInformation(new MonitoredServer(name: 'GitHub', hostname: 'github.com', port: 443))
        assertTrue (certInfos.size > 0)
        assertTrue (certInfos[0] instanceof X509CertificateInformation)
        assertTrue (certInfos[0].subjectPrincipal.startsWith('CN=github.com'))
        assertTrue (certInfos[0].issuerDN.startsWith('CN=DigiCert'))
    }
}
