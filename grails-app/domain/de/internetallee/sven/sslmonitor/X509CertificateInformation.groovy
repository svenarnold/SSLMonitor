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

class X509CertificateInformation {

    String subjectPrincipal
    String issuerDN
    String sha1Fingerprint
    String md5Fingerprint
    Date validNotBefore
    Date validNotAfter

    MonitoredServer server

    static belongsTo = MonitoredServer

    static constraints = {
        subjectPrincipal(blank: false)
        issuerDN(blank: false)
        sha1Fingerprint(blank: false)
        md5Fingerprint(blank: false)
        validNotBefore()
        validNotAfter()
    }

    String toString() { "Subject: ${subjectPrincipal}\nIssuer: ${issuerDN}" }
}
