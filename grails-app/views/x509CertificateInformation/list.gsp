<%@ page import="de.internetallee.sven.sslmonitor.X509CertificateInformation" %>
<%@ page import="org.joda.time.DateTime" %>

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'x509CertificateInformation.label', default: 'X509CertificateInformation')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-x509CertificateInformation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="updateChains"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-x509CertificateInformation" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="subjectPrincipal" title="${message(code: 'x509CertificateInformation.subjectPrincipal.label', default: 'Subject Principal')}" />
					
						<g:sortableColumn property="issuerDN" title="${message(code: 'x509CertificateInformation.issuerDN.label', default: 'Issuer DN')}" />
					
						<g:sortableColumn property="validNotAfter" title="${message(code: 'x509CertificateInformation.validNotAfter.label', default: 'Valid Not After')}" />

                        <g:sortableColumn property="server" title="${message(code: 'server.hostname.label', default: 'Server')}" />

					</tr>
				</thead>
				<tbody>
				<g:each in="${x509CertificateInformationInstanceList}" status="i" var="x509CertificateInformationInstance">
					<g:set var="server" value="${x509CertificateInformationInstance.serviceCertificateLinks?.monitoredServer}" />
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                        <td><g:link action="show" id="${x509CertificateInformationInstance.id}">${x509CertificateInformationInstance.subjectPrincipal?.truncate(30).encodeAsHTML()}</g:link></td>

                        <td>${x509CertificateInformationInstance.issuerDN?.truncate(20).encodeAsHTML()}</td>

                        <td>${x509CertificateInformationInstance.validNotAfter.toString("YYYY-MM-dd")}</td>

                        <td>${server}</td>

					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${x509CertificateInformationInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
