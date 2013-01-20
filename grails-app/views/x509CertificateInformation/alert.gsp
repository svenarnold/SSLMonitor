%{--
  - This file is part of SSLMonitor
  -
  - Copyright (c) 2013 Sven Arnold
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

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>SSL Monitor Notification</title>
</head>

<body>
<h1>SSL Monitor</h1>

<p>One or more ssl certificates need your attention:</p>

<h1>Certificates</h1>

<table>
  <thead>
  <tr>
    <th>Service</th>
    <th>Subject Principal</th>
    <th>Valid Not After</th>
    <th>Issuer DN</th>
  </tr>
  </thead>
  <tbody>
  <g:each in="${certificates}" var="certificate" status="i">
    <tr>
      <td>${certificate.server}</td>
      <td>${certificate.subjectPrincipal}.truncate(20)</td>
      <td>${certificate.validNotAfter}</td>
      <td>${certificate.issuerDN}.truncate(20)</td>
    </tr>
  </g:each>
  </tbody>
</table>

<p>
  You are receiving this email because you are registered as an administrative person in
  <a href="${grailsApplication.config.grails.serverURL}"><g:meta name="app.name"/></a>
</p>

<p><g:meta name="app.name"/> version <g:meta name="app.version"/></p>

<p>&copy; 2013 Sven Arnold, get source code from <a href="https://github.com/svenarnold/SSLMonitor">github</a></p>

</body>
</html>