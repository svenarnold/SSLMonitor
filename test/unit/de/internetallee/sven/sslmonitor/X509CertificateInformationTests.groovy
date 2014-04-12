package de.internetallee.sven.sslmonitor



import grails.test.mixin.*
import org.joda.time.DateTime

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(X509CertificateInformation)
class X509CertificateInformationTests {

    void testConstraints() {

        def certInfo = new X509CertificateInformation(
                subjectPrincipal: '', issuerDN: '',
                sha1Fingerprint: 'sha1', md5Fingerprint: 'md5',
                validNotBefore: new DateTime(),
                validNotAfter: new DateTime()
        )

        mockForConstraintsTests(X509CertificateInformation, [certInfo])

        def newCertInfo = new X509CertificateInformation()
        assertFalse newCertInfo.validate()
        assertEquals 'Subject principal must not be null', 'nullable', newCertInfo.errors['subjectPrincipal']
        assertEquals 'Issuer DN must not be null', 'nullable', newCertInfo.errors['issuerDN']
        assertEquals 'SHA1 fingerprint must not be null', 'nullable', newCertInfo.errors['sha1Fingerprint']
        assertEquals 'MD5 fingerprint must not be null', 'nullable', newCertInfo.errors['md5Fingerprint']
        assertEquals 'Valid not before must not be null', 'nullable', newCertInfo.errors['validNotBefore']
        assertEquals 'Valid not after must not be null', 'nullable', newCertInfo.errors['validNotAfter']
        //assertEquals 'Server must not be null', 'nullable', newCertInfo.errors['server']

        newCertInfo.subjectPrincipal = ''
        newCertInfo.issuerDN = ''
        newCertInfo.sha1Fingerprint = 'sha1'
        newCertInfo.md5Fingerprint = 'md5'
        newCertInfo.validNotBefore = new DateTime()
        newCertInfo.validNotAfter = new DateTime()
        //newCertInfo.server = new MonitoredServer(name: 'A Server', hostname: 'localhost', port: 443)
        assertFalse newCertInfo.validate()
        assertEquals 'Subject principal must not be blank', 'blank', newCertInfo.errors['subjectPrincipal']
        assertEquals 'Issuer DN must not be blank', 'blank', newCertInfo.errors['issuerDN']
        assertEquals 'SHA1 fingerprint must be unique', 'unique', newCertInfo.errors['sha1Fingerprint']
        assertEquals 'MD5 fingerprint must be unique', 'unique', newCertInfo.errors['md5Fingerprint']

        newCertInfo.subjectPrincipal ='x'
        newCertInfo.issuerDN = 'x'
        newCertInfo.sha1Fingerprint = 'x'
        newCertInfo.md5Fingerprint = 'x'
        assertTrue newCertInfo.validate()
    }
}
