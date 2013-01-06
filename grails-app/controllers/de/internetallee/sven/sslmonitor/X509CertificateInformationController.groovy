package de.internetallee.sven.sslmonitor

class X509CertificateInformationController {

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [x509CertificateInformationInstanceList: X509CertificateInformation.list(params), x509CertificateInformationInstanceTotal: X509CertificateInformation.count()]
    }

    def show(Long id) {
        def x509CertificateInformationInstance = X509CertificateInformation.get(id)
        if (!x509CertificateInformationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'x509CertificateInformation.label', default: 'X509CertificateInformation'), id])
            redirect(action: "list")
            return
        }

        [x509CertificateInformationInstance: x509CertificateInformationInstance]
    }
}
