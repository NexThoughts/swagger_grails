package com.demo

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class MyLoginController {

    SpringSecurityService springSecurityService

    def index() {
        render "LOGIN FAILED"
    }
    @Secured(['ROLE_CLIENT','ROLE_ADMIN'])
    def success() {
        String show = "welcome ${(springSecurityService.currentUser as User)?.username}"
        render(show)
    }

    @Secured("ROLE_ADMIN")
    def admin() {
        String show = "Hello ${(springSecurityService.currentUser as User)?.username}"
        render(show)
    }

    def myLogout() {
        redirect(controller: 'logout', action : 'index')
    }
}
