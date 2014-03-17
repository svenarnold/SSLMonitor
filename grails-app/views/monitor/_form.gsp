<%@ page import="de.internetallee.sven.sslmonitor.MonitoredService" %>



<div class="fieldcontain ${hasErrors(bean: monitoredServerInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="monitoredServer.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${monitoredServerInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: monitoredServerInstance, field: 'hostname', 'error')} required">
	<label for="hostname">
		<g:message code="monitoredServer.hostname.label" default="Hostname" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="hostname" required="" value="${monitoredServerInstance?.hostname}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: monitoredServerInstance, field: 'port', 'error')} required">
	<label for="port">
		<g:message code="monitoredServer.port.label" default="Port" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="port" type="number" min="0" value="${monitoredServerInstance.port}" required=""/>
</div>

