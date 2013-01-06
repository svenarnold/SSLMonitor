package de.internetallee.sven.sslmonitor



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(X509CertificateInformation)
class X509CertificateInformationTests {

    void testConstraints() {
        mockForConstraintsTests(X509CertificateInformation)

        def certInfo = new X509CertificateInformation()
        assertFalse certInfo.validate()
        assertEquals 'Subject principal must not be null', 'nullable', certInfo.errors['subjectPrincipal']
        assertEquals 'Issuer DN must not be null', 'nullable', certInfo.errors['issuerDN']
        assertEquals 'SHA1 fingerprint must not be null', 'nullable', certInfo.errors['sha1Fingerprint']
        assertEquals 'MD5 fingerprint must not be null', 'nullable', certInfo.errors['md5Fingerprint']
        assertEquals 'Valid not before must not be null', 'nullable', certInfo.errors['validNotBefore']
        assertEquals 'Valid not after must not be null', 'nullable', certInfo.errors['validNotAfter']
        assertEquals 'Server must not be null', 'nullable', certInfo.errors['server']

        certInfo.subjectPrincipal = ''
        certInfo.issuerDN = ''
        certInfo.sha1Fingerprint = ''
        certInfo.md5Fingerprint = ''
        certInfo.validNotBefore = new Date()
        certInfo.validNotAfter = new Date()
        certInfo.server = new MonitoredServer(name: 'A Server', hostname: 'localhost', port: 443)
        assertFalse certInfo.validate()
        assertEquals 'Subject principal must not be blank', 'blank', certInfo.errors['subjectPrincipal']
        assertEquals 'Issuer DN must not be blank', 'blank', certInfo.errors['issuerDN']
        assertEquals 'SHA1 fingerprint must not be blank', 'blank', certInfo.errors['sha1Fingerprint']
        assertEquals 'MD5 fingerprint must not be blank', 'blank', certInfo.errors['md5Fingerprint']

        certInfo.subjectPrincipal ='x'
        certInfo.issuerDN = 'x'
        certInfo.sha1Fingerprint = 'x'
        certInfo.md5Fingerprint = 'x'
        assertTrue certInfo.validate()
    }
}
