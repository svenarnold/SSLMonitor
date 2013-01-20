SSLMonitor
==========

SSLMonitor is Simple GRAILS application to check SSL certificates of running services. The goal is to have a simple way
to determine any certificates that are short before running out of date.

## Provided Functionalities

- Manage a list of servers that provide SSL sockets
- Fetch certificates from this servers and display their period of validity

## Installation and Usage Instructions

To built the application a working Grails environment is needed. The application itself should be self explaining. It
was developed using Grails 2.2 and Java 7 but should work with earlier versions of both Grails and JDK also.

Make sure to setup credentials in BootStrap.groovy.

## Acknowledgments

SSLMonitor is built upon and uses the GRAILS framework (http://www.grails.org).

Additionally installed plugins are:
- spring-security-core (http://grails.org/plugin/spring-security-core)
- quartz (http://grails.org/plugin/quartz)
- mail (http://grails.org/plugin/mail)

## Disclaimer

SSLMonitor is built for the author's own purpose and learning fun. It is not meant to be used in a production
environment. Therefore it is shared in case somebody else finds it useful as an example. No kind of warranty is given to
anyone.

## License

SSLMonitor is licensed under the GNU Public License Version 3 or (at your option) later. See file LICENSE.txt for details.

## Author

Sven Arnold < sven at internetallee dot de >
