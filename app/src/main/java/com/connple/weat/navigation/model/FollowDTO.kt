package com.connple.weat.navigation.model

data class FollowDTO(
    var followerCount : Int = 0,
    var followers : MutableMap<String,Boolean> = HashMap(),

    var followingCount: Int = 0,
    var following : MutableMap<String,Boolean> = HashMap()
)