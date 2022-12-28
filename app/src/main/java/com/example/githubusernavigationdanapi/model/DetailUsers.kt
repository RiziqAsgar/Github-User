package com.example.githubusernavigationdanapi.model

data class DetailUsers(
    val id : Int ,
    val login: String = "",
    val followers_url:String,
    val following_url: String,
    val name: String = "",
    val company: String = "",
    val location: String = "",
    val html_url: String = "",
    val avatar_url: String = "",
    val public_repos: Int,
    val followers: Int,
    val following: Int
)
