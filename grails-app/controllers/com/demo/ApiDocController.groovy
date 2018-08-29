package com.demo

import grails.plugin.springsecurity.annotation.Secured
import grails.web.mapping.LinkGenerator

@Secured("permitAll")
class ApiDocController {
    LinkGenerator grailsLinkGenerator
    def apiDocService


    def index() {
        String basePath = request.getRequestURL().toString().replace(request.getRequestURI().toString(), "")
        render(view: 'index', model: [apiDocsPath: "${basePath}/apiDoc/json"])
    }


    def swaggerJson() {
        render apiDocService.generateJSON()
    }
}
