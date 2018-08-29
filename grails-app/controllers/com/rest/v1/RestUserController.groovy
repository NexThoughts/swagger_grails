package com.rest.v1

import com.demo.Role
import com.demo.User
import com.demo.UserRole
import com.demo.UserVO
import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

@Secured("permitAll")
@Api(value = '/user', description = 'Login related apis')
class RestUserController {

    SpringSecurityService springSecurityService

    def index() { }

    @Secured(['ROLE_ADMIN'])
    @ApiOperation(value = 'api to add an user', nickname = '/add', tags = 'admin', notes = "this api will create a user", httpMethod = 'POST', consumes = 'application/x-www-form-urlencoded', produces = 'application/Json')
    @ApiImplicitParams([@ApiImplicitParam(name = 'access_token', paramType = 'form', required = true, value = 'token of admin', dataType = "String"),
            @ApiImplicitParam(name = 'emailId', paramType = 'form', required = true, value = 'email id of new user', dataType = "String"),
            @ApiImplicitParam(name = 'role', paramType = 'form', required = true, value = 'authority of of new user', allowableValues = "client,admin", dataType = "String")])
    @ApiResponses([@ApiResponse(code = 200, message = "user created"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 400, message = "attribute missing"),
            @ApiResponse(code = 500, message = "Internal server error")])
    def add() {
        String username = params.emailId
        String authority = params.role;
        Map result = [:]
        try {
            String password = authority.equalsIgnoreCase("admin") ? "admin1234" : "client1234"
            Role role = Role.findByAuthority(authority)
            if (username) {
                if (role) {
                    User newUser = new User(username: username, password: password)
                    newUser.save(flush: true)
                    UserRole.create(newUser, role)
                    result.status = "OK"
                    result.code = "200"
                    result.message = "User created with password : ${password}"
                } else {
                    result.status = "ERROR"
                    result.code = "404"
                    result.message = message(code: 'default.not.found.message', args: ["role", "authority"])
                }
            } else {
                result.status = "ERROR"
                result.code = "400"
                result.message = "Please provide emailId"
            }
        } catch (Exception e) {
            e.printStackTrace()
            result.status = "ERROR"
            result.code = "500"
            result.message = "Internal server error"
        }
        render result as JSON
    }
    @Secured(['ROLE_CLIENT', 'ROLE_ADMIN'])
    @ApiOperation(value = 'api to show an users info', nickname = '/show', tags = 'user', notes = "this api will give info of an user", httpMethod = 'POST', consumes = 'application/x-www-form-urlencoded', produces = 'application/Json')
    @ApiImplicitParams([@ApiImplicitParam(name = 'access_token', paramType = 'form', required = true, value = 'token of admin or user', dataType = "String"),
            @ApiImplicitParam(name = 'id', paramType = 'path', required = true, value = 'id of user', dataType = "int")])
    @ApiResponses([@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 400, message = "attribute missing"),
            @ApiResponse(code = 500, message = "Internal server error")])
    def show() {
        Map result = [:]
        try {
            int id = Integer.parseInt(params.id)
            User user = User.get(id)
            if (user) {
                UserVO userVO = new UserVO(user)
                result.userInfo = userVO
            } else {
                result.status = "ERROR"
                result.code = "404"
                result.message = message(code: 'default.not.found.message', args: ["user", id])
            }
        } catch (Exception e) {
            e.printStackTrace()
            result.status = "ERROR"
            result.code = "500"
            result.message = "Internal server error"
        }
        render result as JSON
    }

    @Secured(['ROLE_CLIENT', 'ROLE_ADMIN'])
    @ApiOperation(value = 'api to delete an users', nickname = '/delete', tags = 'admin', notes = "this api will delete an user", httpMethod = 'DELETE', consumes = 'application/x-www-form-urlencoded', produces = 'application/Json')
    @ApiImplicitParams([@ApiImplicitParam(name = 'access_token', paramType = 'form', required = true, value = 'token of admin or user', dataType = "String"),
            @ApiImplicitParam(name = 'id', paramType = 'form', required = true, value = 'id of user', dataType = "int")])
    @ApiResponses([@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 400, message = "attribute missing"),
            @ApiResponse(code = 500, message = "Internal server error")])
    def delete() {
        User currentUser = springSecurityService.currentUser as User
        int id = Integer.parseInt(params.id)
        Map result = [:]
        try {
            User user = User.get(id)
            if (user) {
                if (user.isAdmin() || user.id == currentUser.id) {
                    List<Role> roles = user?.authorities as List
                    roles.each {
                        role ->
                            UserRole.remove(user, role, true)
                    }
                    user.delete(flush: true)
                    result.status = "OK"
                    result.code = "200"
                    result.message = "User deleted with emailId : ${user.username}"
                } else {
                    result.status = "ERROR"
                    result.code = "400"
                    result.message = "User is not admin"
                }
            } else {
                result.status = "ERROR"
                result.code = "404"
                result.message = message(code: 'default.not.found.message', args: ["user", id])
            }
        } catch (Exception e) {
            e.printStackTrace()
            result.status = "ERROR"
            result.code = "500"
            result.message = "Internal server error"
        }
        render result as JSON
    }

}
