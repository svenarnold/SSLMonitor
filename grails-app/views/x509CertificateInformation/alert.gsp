%{--
  - This file is part of SSLMonitor
  -
  - Copyright (c) 2014 Sven Arnold
  -
  - SSLMonitor is free software: you can redistribute it and/or modify
  - it under the terms of the GNU General Public License as published by
  - the Free Software Foundation, either version 3 of the License, or
  - (at your option) any later version.
  -
  - SSLMonitor is distributed in the hope that it will be useful,
  - but WITHOUT ANY WARRANTY; without even the implied warranty of
  - MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
  - GNU General Public License for more details.
  -
  - You should have received a copy of the GNU General Public License
  - along with SSLMonitor. If not, see <http://www.gnu.org/licenses/>.
  --}%
<%@ page contentType="text/plain;charset=UTF-8" %>
SSL Monitor Notification

One or more ssl certificates need your attention:
<g:each in="${certificates}" var="certificate" status="i">

=== ${certificate.server?.name} ===
Service:
${certificate.server}

Subject Principal:
${certificate.subjectPrincipal}

Valid not After:
${certificate.validNotAfter}

Issuer DN:
${certificate.issuerDN}

Services:
<g:each in="${certificate.serviceCertificateLinks}" var="link">
${link?.server}
</g:each>
</g:each>


You are receiving this email because you are registered as an administrative person in
<g:meta name="app.name"/> (${grailsApplication?.config?.grails?.serverURL})

<g:meta name="app.name"/> version <g:meta name="app.version"/>  (${g.render(template: '/git').trim()})

© 2014 Sven Arnold, source code available at: https://github.com/svenarnold/SSLMonitor
