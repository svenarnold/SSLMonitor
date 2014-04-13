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

import grails.rest.Resource

@Resource(uri='/monitoredServices', formats=['json', 'xml'])
class MonitoredServer {

    String name
    String hostname
    int port

    boolean connectionSuccess = true
    String lastError = ''

    List certificateInformationChain

   static hasMany = [certificateInformationChain: ServiceCertificateLink]

    static constraints = {
        name(blank: false, unique: true)
        hostname(blank: false)
        port(min: 0)
        lastError(nullable: true)
    }

    String toString() { name + ' (' + hostname + ':' + port + ')' }
}
