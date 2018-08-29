package com.demo


import grails.web.mapping.LinkGenerator
import io.swagger.annotations.Api
import io.swagger.jaxrs.config.BeanConfig
import io.swagger.models.Swagger
import io.swagger.servlet.Reader
import io.swagger.util.Json
import org.apache.commons.lang3.StringUtils
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

class ApiDocService implements ApplicationContextAware {

    BeanConfig swaggerBeanConfig
    Swagger swagger

    ApplicationContext applicationContext

    def generateJSON() {
        String[] schemes = ["http"] as String[]
        swaggerBeanConfig.setSchemes(schemes)
        swaggerBeanConfig.setScan(true)

        swagger = scanSwaggerResource()
        Json.mapper().writeValueAsString(swagger)
//        getJsonDocument(scanSwaggerResource())
    }

    Swagger scanSwaggerResource() {
        swagger = swaggerBeanConfig.swagger
        LinkGenerator linkGenerator = applicationContext.getBean(LinkGenerator.class)
        String host = linkGenerator.getServerBaseURL()
        host = host.replace($/http:///$, StringUtils.EMPTY)
        host = host.replace($/https:///$, StringUtils.EMPTY)
        swagger.setHost(host)
        Map<String, Object> swaggerResourcesAsMap = applicationContext.getBeansWithAnnotation(Api.class)
        List<Class> swaggerResources = swaggerResourcesAsMap.collect {
            it.value?.class
        }
        if (swaggerResources) {
            Reader.read(swagger, new HashSet<Class<?>>(swaggerResources))
        }

        return swagger
    }

    static String getJsonDocument(Swagger swagger) {
        String swaggerJson = null
        if (swagger) {
            try {
                swaggerJson = Json.mapper().writeValueAsString(swagger)
            } catch (Exception ex) {

            }
        }
        return swaggerJson
    }
}

