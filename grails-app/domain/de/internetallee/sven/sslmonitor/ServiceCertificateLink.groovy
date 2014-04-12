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
            server.removeFromCertificateInformationChain(instance)
            certificate.removeFromServiceCertificateLinks(instance)
            server.save()
            certificate.save()
            instance.delete(flush: flush)
        }
    }
}
