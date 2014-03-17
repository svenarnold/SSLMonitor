package de.internetallee.sven.sslmonitor

import org.springframework.dao.DataIntegrityViolationException

class MonitorController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [monitoredServerInstanceList: MonitoredService.list(params), monitoredServerInstanceTotal: MonitoredService.count()]
    }

    def create() {
        [monitoredServerInstance: new MonitoredService(params)]
    }

    def save() {
        def monitoredServerInstance = new MonitoredService()
        bindData(monitoredServerInstance, params, [exclude: 'certificateInformationChain'])

        if (!monitoredServerInstance.save(flush: true)) {
            render(view: "create", model: [monitoredServerInstance: monitoredServerInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'monitoredServer.label', default: 'MonitoredService'), monitoredServerInstance.id])
        redirect(action: "show", id: monitoredServerInstance.id)
    }

    def show(Long id) {
        def monitoredServerInstance = MonitoredService.get(id)
        if (!monitoredServerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'monitoredServer.label', default: 'MonitoredService'), id])
            redirect(action: "list")
            return
        }

        [monitoredServerInstance: monitoredServerInstance]
    }

    def edit(Long id) {
        def monitoredServerInstance = MonitoredService.get(id)
        if (!monitoredServerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'monitoredServer.label', default: 'MonitoredService'), id])
            redirect(action: "list")
            return
        }

        [monitoredServerInstance: monitoredServerInstance]
    }

    def update(Long id, Long version) {
        def monitoredServerInstance = MonitoredService.get(id)

        if (!monitoredServerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'monitoredServer.label', default: 'MonitoredService'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (monitoredServerInstance.version > version) {
                monitoredServerInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'monitoredServer.label', default: 'MonitoredService')] as Object[],
                        "Another user has updated this MonitoredService while you were editing")
                render(view: "edit", model: [monitoredServerInstance: monitoredServerInstance])
                return
            }
        }

        bindData(monitoredServerInstance, params, [exclude: 'certificateInformationChain'])

        if (!monitoredServerInstance.save(flush: true)) {
            render(view: "edit", model: [monitoredServerInstance: monitoredServerInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'monitoredServer.label', default: 'MonitoredService'), monitoredServerInstance.id])
        redirect(action: "show", id: monitoredServerInstance.id)
    }

    def delete(Long id) {
        def monitoredServerInstance = MonitoredService.get(id)
        if (!monitoredServerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'monitoredServer.label', default: 'MonitoredService'), id])
            redirect(action: "list")
            return
        }

        try {
            monitoredServerInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'monitoredServer.label', default: 'MonitoredService'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'monitoredServer.label', default: 'MonitoredService'), id])
            redirect(action: "show", id: id)
        }
    }
}
