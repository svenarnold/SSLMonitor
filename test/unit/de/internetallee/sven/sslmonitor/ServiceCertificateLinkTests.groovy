package de.internetallee.sven.sslmonitor

import grails.test.mixin.*
import org.joda.time.DateTime
import org.junit.*

@TestFor(ServiceCertificateLink)
@Mock([MonitoredServer, X509CertificateInformation])
class ServiceCertificateLinkTests {

    @Before
    void setUp() {

    }

    @After
    void tearDown() {

    }

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
