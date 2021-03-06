<%@ page import="de.internetallee.sven.sslmonitor.MonitoredServer" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'monitoredServer.label', default: 'MonitoredServer')}"/>
  <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-monitoredServer" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                      default="Skip to content&hellip;"/></a>

<div id="show-monitoredServer" class="content scaffold-show" role="main">
  <h1><g:message code="default.show.label" args="[entityName]"/></h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <ol class="property-list monitoredServer">

    <g:if test="${monitoredServerInstance?.name}">
      <li class="fieldcontain">
        <span id="name-label" class="property-label"><g:message code="monitoredServer.name.label"
                                                                default="Name"/></span>
        <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${monitoredServerInstance}"
                                                                                field="name"/></span>
      </li>
    </g:if>

    <g:if test="${monitoredServerInstance?.hostname}">
      <li class="fieldcontain">
        <span id="hostname-label" class="property-label"><g:message code="monitoredServer.hostname.label"
                                                                    default="Hostname"/></span>
        <span class="property-value" aria-labelledby="hostname-label"><g:fieldValue bean="${monitoredServerInstance}"
                                                                                    field="hostname"/></span>
      </li>
    </g:if>

    <g:if test="${monitoredServerInstance?.port}">
      <li class="fieldcontain">
        <span id="port-label" class="property-label"><g:message code="monitoredServer.port.label"
                                                                default="Port"/></span>
        <span class="property-value" aria-labelledby="port-label"><g:fieldValue bean="${monitoredServerInstance}"
                                                                                field="port"/></span>
      </li>
    </g:if>

    <g:if test="${monitoredServerInstance?.connectionSuccess == false}">
      <li class="fieldcontain">
        <span id="connectionSuccess-label" class="property-label"><g:message
                code="monitoredServer.connectionSuccess.label" default="Connection Success"/></span>
        <span class="property-value" aria-labelledby="connectionSuccess-label"><g:fieldValue
                bean="${monitoredServerInstance}" field="connectionSuccess"/></span>
      </li>
        <li class="fieldcontain">
          <span id="lastError-label" class="property-label"><g:message
                  code="monitoredServer.connectionSuccess.label" default="Last Error"/></span>
          <span class="property-value" aria-labelledby="lastError-label"><g:fieldValue
                  bean="${monitoredServerInstance}" field="lastError"/></span>
        </li>
    </g:if>

    <g:if test="${monitoredServerInstance?.certificateInformationChain}">
      <li class="fieldcontain">
        <span id="certificateInformationChain-label" class="property-label"><g:message
                code="monitoredServer.certificateInformationChain.label"
                default="Certificate Information Chain"/></span>

        <g:each in="${monitoredServerInstance.certificateInformationChain}" var="c">
          <span class="property-value" aria-labelledby="certificateInformationChain-label"><g:link
                  controller="x509CertificateInformation" action="show"
                  id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
        </g:each>

      </li>
    </g:if>

  </ol>
  <g:form>
    <fieldset class="buttons">
      <g:hiddenField name="id" value="${monitoredServerInstance?.id}"/>
      <g:link class="edit" action="edit" id="${monitoredServerInstance?.id}"><g:message code="default.button.edit.label"
                                                                                        default="Edit"/></g:link>
      <g:actionSubmit class="delete" action="delete"
                      value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                      onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
    </fieldset>
  </g:form>
</div>
</body>
</html>
