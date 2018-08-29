package com.demo

class AuthenticationToken {

    String username
    String tokenValue

    static mapping = {
        version false
    }

    static constraints = {
        tokenValue(blank: true, nullable: true)
    }
}
