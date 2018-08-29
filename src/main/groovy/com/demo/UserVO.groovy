package com.demo


class UserVO {

    Long id
    String username
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    String authorities

    UserVO() {

    }

    UserVO(User user) {
        id = user?.id
        username = user?.username
        enabled = user?.enabled
        accountExpired = user?.accountExpired
        accountLocked = user?.accountLocked
        passwordExpired = user?.passwordExpired
        List<Role> roleList = user.authorities as List<Role>
        authorities = roleList ? (roleList*.authority).toString().replaceAll("\\[|\\]", "") : null
    }
}
