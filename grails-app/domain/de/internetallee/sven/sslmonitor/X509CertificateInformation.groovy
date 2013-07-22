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

class X509CertificateInformation {

    String subjectPrincipal
    String issuerDN
    String sha1Fingerprint
    String md5Fingerprint
    DateTime validNotBefore
    DateTime validNotAfter

    MonitoredServer server

    static belongsTo = MonitoredServer

    static constraints = {
        subjectPrincipal(blank: false, maxSize: 1024)
        issuerDN(blank: false, maxSize: 1024)
        sha1Fingerprint()
        md5Fingerprint()
        validNotBefore()
        validNotAfter()
    }

    static namedQueries = {
        certificatesDueInDays { days ->
            def dueDate = new DateTime().plus(Period.days(days))
            lte 'validNotAfter', dueDate
        }
    }

    String toString() { "Subject: ${subjectPrincipal}\nIssuer: ${issuerDN}" }

    @Override
    int hashCode() {
        return sha1Fingerprint.hashCode()
    }

    @Override
    boolean equals(Object obj) {
        (obj instanceof X509CertificateInformation) ? sha1Fingerprint.equals(obj.sha1Fingerprint) : false
    }
}
