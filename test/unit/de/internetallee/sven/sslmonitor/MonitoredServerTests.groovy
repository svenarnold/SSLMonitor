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

@TestFor(MonitoredServer)
class MonitoredServerTests {

    void testConstraints() {
        def monitoredService = new MonitoredServer(name: 'Unique Service', hostname: 'localhost', port: 443)
        mockForConstraintsTests(MonitoredServer, [monitoredService])

        def newService = new MonitoredServer()
        assertFalse newService.validate()
        assertEquals 'Name must not be nullable.', 'nullable', newService.errors['name']
        assertEquals 'Hostname must not be nullable.', 'nullable', newService.errors['hostname']

        newService.name =''
        newService.hostname = ''
        newService.port = -1
        assertFalse newService.validate()
        assertEquals 'Name must not be blank', 'blank', newService.errors['name']
        assertEquals 'Hostname must not be blank.', 'blank', newService.errors['hostname']
        assertEquals 'Port must not be negative.', 'min', newService.errors['port']

        newService.name = 'Unique Service'
        newService.hostname = 'localhost'
        newService.port = 443
        assertFalse newService.validate()
        assertEquals 'Name must be unique', 'unique', newService.errors['name']

        newService.name = 'New Service'
        assertTrue newService.validate()
    }

    void testToString() {
        def monitoredService = new MonitoredServer(name: 'service', hostname: 'host', port: 80)
        assertEquals 'service (host:80)', monitoredService.toString()
    }
}
