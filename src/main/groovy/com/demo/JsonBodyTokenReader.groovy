package com.demo

import grails.plugin.springsecurity.rest.token.AccessToken
import grails.plugin.springsecurity.rest.token.reader.TokenReader

import javax.servlet.http.HttpServletRequest


class JsonBodyTokenReader implements TokenReader{

    AccessToken findToken(HttpServletRequest request) {
        String token = request.getParameter("access_token")
        if (token) {
            println "============ access_token Found in Request Parameters ==============="
            return (new AccessToken(token))
        } else {
            Map requestMap = request.JSON as Map
            if (requestMap.access_token) {
                token = requestMap.access_token
                println "======== access_token Found in Json Body ============"
                return (new AccessToken(token))
            }
            return null
        }
    }
}
