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

import org.springframework.dao.DataIntegrityViolationException

class MonitorController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [monitoredServerInstanceList: MonitoredServer.list(params), monitoredServerInstanceTotal: MonitoredServer.count()]
    }

    def create() {
        [monitoredServerInstance: new MonitoredServer(params)]
    }

    def save() {
        def monitoredServerInstance = new MonitoredServer()
        bindData(monitoredServerInstance, params, [exclude: 'certificateInformationChain'])

        if (!monitoredServerInstance.save(flush: true)) {
            render(view: "create", model: [monitoredServerInstance: monitoredServerInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'monitoredServer.label', default: 'MonitoredServer'), monitoredServerInstance.id])
        redirect(action: "show", id: monitoredServerInstance.id)
    }

    def show(Long id) {
        def monitoredServerInstance = MonitoredServer.get(id)
        if (!monitoredServerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'monitoredServer.label', default: 'MonitoredServer'), id])
            redirect(action: "list")
            return
        }

        [monitoredServerInstance: monitoredServerInstance]
    }

    def edit(Long id) {
        def monitoredServerInstance = MonitoredServer.get(id)
        if (!monitoredServerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'monitoredServer.label', default: 'MonitoredServer'), id])
            redirect(action: "list")
            return
        }

        [monitoredServerInstance: monitoredServerInstance]
    }

    def update(Long id, Long version) {
        def monitoredServerInstance = MonitoredServer.get(id)

        if (!monitoredServerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'monitoredServer.label', default: 'MonitoredServer'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (monitoredServerInstance.version > version) {
                monitoredServerInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'monitoredServer.label', default: 'MonitoredServer')] as Object[],
                        "Another user has updated this MonitoredServer while you were editing")
                render(view: "edit", model: [monitoredServerInstance: monitoredServerInstance])
                return
            }
        }

        bindData(monitoredServerInstance, params, [exclude: 'certificateInformationChain'])

        if (!monitoredServerInstance.save(flush: true)) {
            render(view: "edit", model: [monitoredServerInstance: monitoredServerInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'monitoredServer.label', default: 'MonitoredServer'), monitoredServerInstance.id])
        redirect(action: "show", id: monitoredServerInstance.id)
    }

    def delete(Long id) {
        def monitoredServerInstance = MonitoredServer.get(id)
        if (!monitoredServerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'monitoredServer.label', default: 'MonitoredServer'), id])
            redirect(action: "list")
            return
        }

        try {
            monitoredServerInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'monitoredServer.label', default: 'MonitoredServer'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'monitoredServer.label', default: 'MonitoredServer'), id])
            redirect(action: "show", id: id)
        }
    }
}
