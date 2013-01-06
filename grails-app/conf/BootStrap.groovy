import de.internetallee.sven.sslmonitor.Role
import de.internetallee.sven.sslmonitor.User
import de.internetallee.sven.sslmonitor.UserRole

class BootStrap {

    def init = { servletContext ->

        String.metaClass.truncate = { maxLength ->
            delegate ? (delegate.length() > maxLength ? delegate[0..maxLength - 2] + '\u2026' : delegate) : ''
        }

        def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)

        def adminUser = User.findByUsername('admin') ?: new User(
                username: 'admin',
                password: 'secret',
                enabled: true).save(failOnError: true)

        if (!adminUser.authorities.contains(adminRole)) {
            UserRole.create adminUser, adminRole
        }
    }

    def destroy = {
    }
}
