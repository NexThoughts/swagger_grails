import com.demo.Role
import com.demo.User
import com.demo.UserRole

class BootStrap {

    def init = { servletContext ->
        if (!User.count) {
            5.times {
                User u1 = new User(username: "clientUser${it}@gmail.com", password: "client1234").save(flush: true)
                Role client_role = Role.findByAuthority("ROLE_CLIENT") ? Role.findByAuthority("ROLE_CLIENT") : new Role(authority: "ROLE_CLIENT").save(flush: true)
                new UserRole(user: u1, role: client_role).save(flush: true)
                println it
            }
            5.times {
                User u1 = new User(username: "adminUser${it}@gmail.com", password: "admin1234").save(flush: true)
                Role client_role = Role.findByAuthority("ROLE_ADMIN") ? Role.findByAuthority("ROLE_ADMIN") : new Role(authority: "ROLE_ADMIN").save(flush: true)
                new UserRole(user: u1, role: client_role).save(flush: true)
                println it
            }
        }
    }

    def destroy = {

    }
}
