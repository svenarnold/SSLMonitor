
<%@ page import="de.internetallee.sven.sslmonitor.MonitoredServer" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'monitoredServer.label', default: 'MonitoredServer')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-monitoredServer" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-monitoredServer" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'monitoredServer.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'monitoredServer.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="hostname" title="${message(code: 'monitoredServer.hostname.label', default: 'Hostname')}" />
					
						<g:sortableColumn property="port" title="${message(code: 'monitoredServer.port.label', default: 'Port')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${monitoredServerInstanceList}" status="i" var="monitoredServerInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${monitoredServerInstance.id}">${fieldValue(bean: monitoredServerInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: monitoredServerInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: monitoredServerInstance, field: "hostname")}</td>
					
						<td>${fieldValue(bean: monitoredServerInstance, field: "port")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${monitoredServerInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
