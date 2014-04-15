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

class ServiceCertificateLink {

    DateTime dateCreated
    DateTime lastUpdated

    MonitoredServer monitoredServer
    X509CertificateInformation x509CertificateInformation

    static belongsTo = [MonitoredServer, X509CertificateInformation]

    String toString() { monitoredServer.name + " : " + x509CertificateInformation.subjectPrincipal }

    static ServiceCertificateLink create(MonitoredServer server, X509CertificateInformation certificate, boolean flush = false) {
        withTransaction { status ->
            def link = new ServiceCertificateLink(monitoredServer: server, x509CertificateInformation: certificate)
            server.addToCertificateInformationChain(link)
            certificate.addToServiceCertificateLinks(link)
            certificate.save(flush: flush)
            server.save(flush: flush)
            link.save(flush: flush)
        }
    }

    static boolean remove(MonitoredServer server, X509CertificateInformation certificate, boolean flush = false) {
        ServiceCertificateLink instance = ServiceCertificateLink.findByMonitoredServerAndX509CertificateInformation(server, certificate)
        if (!instance) {
            return false
        } else {
            withTransaction { status ->
                server.removeFromCertificateInformationChain(instance)
                certificate.removeFromServiceCertificateLinks(instance)
                server.save()
                certificate.save()
                instance.delete(flush: flush)
            }
        }
    }
}
