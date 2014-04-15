/*
 * This file is part of SSLMonitor
 *
 * Copyright (c) 2014 Sven Arnold
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

class X509CertificateInformationController {

    def certificateService

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

    def updateChains() {
        certificateService.updateAllCertificateChains()
        redirect(action: 'list')
    }

    def checkDueCertificates(Integer days) {
        days = days ?: 0
        def dueCertificates = X509CertificateInformation.certificatesDueInDays(days)
        render(
                model: [
                        x509CertificateInformationInstanceList: dueCertificates.list(),
                        x509CertificateInformationInstanceTotal: dueCertificates.count()],
                view: 'list'
        )
    }
}
