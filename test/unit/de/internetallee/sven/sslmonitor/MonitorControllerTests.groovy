package de.internetallee.sven.sslmonitor

import grails.test.mixin.*

@TestFor(MonitorController)
@Mock(MonitoredService)
class MonitorControllerTests {

    def populateValidParams(params) {
        assert params != null

        params.name = 'A Server'
        params.hostname = 'localhost'
        params.port = 443
    }

    void testIndex() {
        controller.index()
        assert "/monitoredServer/list" == response.redirectedUrl
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
        assert view == '/monitoredServer/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/monitoredServer/show/1'
        assert controller.flash.message != null
        assert MonitoredService.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/monitoredServer/list'

        populateValidParams(params)
        def monitoredServer = new MonitoredService(params)

        assert monitoredServer.save() != null

        params.id = monitoredServer.id

        def model = controller.show()

        assert model.monitoredServerInstance == monitoredServer
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/monitoredServer/list'

        populateValidParams(params)
        def monitoredServer = new MonitoredService(params)

        assert monitoredServer.save() != null

        params.id = monitoredServer.id

        def model = controller.edit()

        assert model.monitoredServerInstance == monitoredServer
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/monitoredServer/list'

        response.reset()

        populateValidParams(params)
        def monitoredServer = new MonitoredService(params)

        assert monitoredServer.save() != null

        // test invalid parameters in update
        params.id = monitoredServer.id
        params.name=''

        controller.update()

        assert view == "/monitoredServer/edit"
        assert model.monitoredServerInstance != null

        monitoredServer.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/monitoredServer/show/$monitoredServer.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        monitoredServer.clearErrors()

        populateValidParams(params)
        params.id = monitoredServer.id
        params.version = -1
        controller.update()

        assert view == "/monitoredServer/edit"
        assert model.monitoredServerInstance != null
        assert model.monitoredServerInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/monitoredServer/list'

        response.reset()

        populateValidParams(params)
        def monitoredServer = new MonitoredService(params)

        assert monitoredServer.save() != null
        assert MonitoredService.count() == 1

        params.id = monitoredServer.id

        controller.delete()

        assert MonitoredService.count() == 0
        assert MonitoredService.get(monitoredServer.id) == null
        assert response.redirectedUrl == '/monitoredServer/list'
    }
}
