class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
        "/apiDoc"(controller: 'apiDoc', action: 'index')
        "/apiDoc/json"(controller: 'apiDoc', action: 'swaggerJson')
        "/api/v1/user/add"(controller: 'restUser', action: 'add')
        "/api/v1/user/show/$id"(controller: 'restUser', action: 'show')
        "/api/v1/user/delete"(controller: 'restUser', action: 'delete')
    }
}
