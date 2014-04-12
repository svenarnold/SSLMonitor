
<%@ page import="de.internetallee.sven.sslmonitor.X509CertificateInformation" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'x509CertificateInformation.label', default: 'X509CertificateInformation')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-x509CertificateInformation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-x509CertificateInformation" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list x509CertificateInformation">
			
				<g:if test="${x509CertificateInformationInstance?.subjectPrincipal}">
				<li class="fieldcontain">
					<span id="subjectPrincipal-label" class="property-label"><g:message code="x509CertificateInformation.subjectPrincipal.label" default="Subject Principal" /></span>
					
						<span class="property-value" aria-labelledby="subjectPrincipal-label"><g:fieldValue bean="${x509CertificateInformationInstance}" field="subjectPrincipal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${x509CertificateInformationInstance?.issuerDN}">
				<li class="fieldcontain">
					<span id="issuerDN-label" class="property-label"><g:message code="x509CertificateInformation.issuerDN.label" default="Issuer DN" /></span>
					
						<span class="property-value" aria-labelledby="issuerDN-label"><g:fieldValue bean="${x509CertificateInformationInstance}" field="issuerDN"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${x509CertificateInformationInstance?.sha1Fingerprint}">
				<li class="fieldcontain">
					<span id="sha1Fingerprint-label" class="property-label"><g:message code="x509CertificateInformation.sha1Fingerprint.label" default="Sha1 Fingerprint" /></span>
					
						<span class="property-value" aria-labelledby="sha1Fingerprint-label"><g:fieldValue bean="${x509CertificateInformationInstance}" field="sha1Fingerprint"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${x509CertificateInformationInstance?.md5Fingerprint}">
				<li class="fieldcontain">
					<span id="md5Fingerprint-label" class="property-label"><g:message code="x509CertificateInformation.md5Fingerprint.label" default="Md5 Fingerprint" /></span>
					
						<span class="property-value" aria-labelledby="md5Fingerprint-label"><g:fieldValue bean="${x509CertificateInformationInstance}" field="md5Fingerprint"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${x509CertificateInformationInstance?.validNotBefore}">
				<li class="fieldcontain">
					<span id="validNotBefore-label" class="property-label"><g:message code="x509CertificateInformation.validNotBefore.label" default="Valid Not Before" /></span>
					
						<span class="property-value" aria-labelledby="validNotBefore-label">${x509CertificateInformationInstance?.validNotBefore}</span>
					
				</li>
				</g:if>
			
				<g:if test="${x509CertificateInformationInstance?.validNotAfter}">
				<li class="fieldcontain">
					<span id="validNotAfter-label" class="property-label"><g:message code="x509CertificateInformation.validNotAfter.label" default="Valid Not After" /></span>
					
						<span class="property-value" aria-labelledby="validNotAfter-label">${x509CertificateInformationInstance?.validNotAfter}</span>

				</li>
				</g:if>
			
				<g:if test="${x509CertificateInformationInstance?.serviceCertificateLinks}">
                <g:set var="links" value="${x509CertificateInformationInstance.serviceCertificateLinks}" />
				<li class="fieldcontain">
					<span id="server-label" class="property-label"><g:message code="server.hostname.label" default="Server" /></span>
					
						<span class="property-value" aria-labelledby="server-label">
                            <g:each var="link" in="${links}">
                                <g:link controller="monitor" action="show" id="${link.monitoredServer?.id}">${link.monitoredServer?.hostname?.encodeAsHTML()}</g:link>

                            </g:each>
                        </span>
				</li>
				</g:if>

			</ol>
		</div>
	</body>
</html>
