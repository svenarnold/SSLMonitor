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

@TestFor(MonitorController)
@Mock(MonitoredServer)
class MonitorControllerTests {

    def populateValidParams(params) {
        assert params != null

        params.name = 'A Server'
        params.hostname = 'localhost'
        params.port = 443
    }

    void testIndex() {
        controller.index()
        assert "/monitor/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.monitoredServerInstanceList.size() == 0
        assert model.monitoredServerInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.monitoredServerInstance != null
    }

    void testSave() {
        controller.save()

        assert model.monitoredServerInstance != null
        assert view == '/monitor/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/monitor/show/1'
        assert controller.flash.message != null
        assert MonitoredServer.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/monitor/list'

        populateValidParams(params)
        def monitoredServer = new MonitoredServer(params)

        assert monitoredServer.save() != null

        params.id = monitoredServer.id

        def model = controller.show()

        assert model.monitoredServerInstance == monitoredServer
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/monitor/list'

        populateValidParams(params)
        def monitoredServer = new MonitoredServer(params)

        assert monitoredServer.save() != null

        params.id = monitoredServer.id

        def model = controller.edit()

        assert model.monitoredServerInstance == monitoredServer
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/monitor/list'

        response.reset()

        populateValidParams(params)
        def monitoredServer = new MonitoredServer(params)

        assert monitoredServer.save() != null

        // test invalid parameters in update
        params.id = monitoredServer.id
        params.name=''

        controller.update()

        assert view == "/monitor/edit"
        assert model.monitoredServerInstance != null

        monitoredServer.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/monitor/show/$monitoredServer.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        monitoredServer.clearErrors()

        populateValidParams(params)
        params.id = monitoredServer.id
        params.version = -1
        controller.update()

        assert view == "/monitor/edit"
        assert model.monitoredServerInstance != null
        assert model.monitoredServerInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/monitor/list'

        response.reset()

        populateValidParams(params)
        def monitoredServer = new MonitoredServer(params)

        assert monitoredServer.save() != null
        assert MonitoredServer.count() == 1

        params.id = monitoredServer.id

        controller.delete()

        assert MonitoredServer.count() == 0
        assert MonitoredServer.get(monitoredServer.id) == null
        assert response.redirectedUrl == '/monitor/list'
    }
}
