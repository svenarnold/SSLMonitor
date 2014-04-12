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

package de.internetallee.sslmonitor

import de.internetallee.sven.sslmonitor.X509CertificateInformation
import grails.util.Holders

class CheckCertificatesJob {

    def grailsApplication
    def certificateService
    def mailService

    static triggers = {

        cron cronExpression: Holders.grailsApplication.config.sslMonitor?.cron?: "0 0 8 * * ?", startDelay: 10000
    }

    def execute() {
        log.debug("Executing job to check certificates")
        certificateService.updateAllCertificateChains()
        def days = grailsApplication?.config?.sslMonitor?.notification?.intervalInDays ?: 30
        log.debug("Interval is ${days} days")
        def certificatesQuery = X509CertificateInformation.certificatesDueInDays(days)
        if (certificatesQuery.count() > 0) {
            log.info("At least one certificate triggers notification.")
            mailService.sendMail {
                from grailsApplication?.config?.sslMonitor?.notification?.sender ?: 'sslMonitor@localhost'
                to grailsApplication?.config?.sslMonitor?.notification?.recipient ?: 'root@localhost'
                subject "*** SSLMonitor Notification - ${certificatesQuery.count()} certificate(s) need your attention ***"
                body (view: '/x509CertificateInformation/alert', model: [certificates: certificatesQuery.list()])
            }
        }
    }
}
