import de.internetallee.sven.sslmonitor.SecRole
import de.internetallee.sven.sslmonitor.SecUser
import de.internetallee.sven.sslmonitor.SecUserRole

class BootStrap {

    def grailsApplication

    def init = { servletContext ->

        log.info("Initializing ${grailsApplication.metadata['app.ame']}")
        log.info("Application Version: " + grailsApplication.metadata['app.version'])
        log.info("Grails Version:      " + grailsApplication.metadata['app.grails.version'])

        def timeoutInMillis = grailsApplication.config.sslMonitor.timeoutInMillis
        if (timeoutInMillis) log.info("Configured Socket Timeout: " + timeoutInMillis  + "ms")

        String.metaClass.truncate = { maxLength ->
            delegate ? (delegate.length() > maxLength ? delegate[0..maxLength - 2] + '\u2026' : delegate) : ''
        }

        def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)

        def adminUser = SecUser.findByUsername('admin') ?: new SecUser(
                username: 'admin',
                password: 'secret',
                enabled: true).save(failOnError: true)

        if (!adminUser.authorities.contains(adminRole)) {
            SecUserRole.create adminUser, adminRole
        }
    }

    def destroy = {
    }
}
