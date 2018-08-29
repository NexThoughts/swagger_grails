import com.demo.JsonBodyTokenReader
import io.swagger.jaxrs.config.BeanConfig

// Place your Spring DSL code here
beans = {

    tokenReader(JsonBodyTokenReader)

    swaggerBeanConfig(BeanConfig) {
//        def serverUrl = grailsApplication.config.grails.serverURL.toString()
//        def hostName = serverUrl.substring(serverUrl.indexOf("://")+3)
        resourcePackage = 'com.demo.rest.v1'
//        host = hostName
        basePath = "/api/v1"
        //version = 'v0' // Default "1".
        title = 'Core Registration API, Version V1' // Default: App Name.
        description = 'API for Accessing secured resources'
        contact = 'arif@fintechlabs.in'
        license = ''
        licenseUrl = ''
    }
}
