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

        newCertInfo.subjectPrincipal = ''
        newCertInfo.issuerDN = ''
        newCertInfo.sha1Fingerprint = 'sha1'
        newCertInfo.md5Fingerprint = 'md5'
        newCertInfo.validNotBefore = new DateTime()
        newCertInfo.validNotAfter = new DateTime()
        assertFalse newCertInfo.validate()
        assertEquals 'Subject principal must not be blank', 'blank', newCertInfo.errors['subjectPrincipal']
        assertEquals 'Issuer DN must not be blank', 'blank', newCertInfo.errors['issuerDN']
        assertEquals 'SHA1 fingerprint must be unique', 'unique', newCertInfo.errors['sha1Fingerprint']
        assertEquals 'MD5 fingerprint must be unique', 'unique', newCertInfo.errors['md5Fingerprint']

        newCertInfo.subjectPrincipal = 'x'
        newCertInfo.issuerDN = 'x'
        newCertInfo.sha1Fingerprint = 'x'
        newCertInfo.md5Fingerprint = 'x'
        assertTrue newCertInfo.validate()
    }
}
