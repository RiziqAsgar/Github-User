package com.example.githubusernavigationdanapi.api

import com.example.githubusernavigationdanapi.model.DetailUsers
import com.example.githubusernavigationdanapi.model.User
import com.example.githubusernavigationdanapi.model.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("search/users")
    @Headers("Authorization: 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")
    fun getSearchUser(
        @Query("q")query: String
    ): Call<UsersResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUsers>


    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowersUser(
        @Path("username")username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowingUser(
        @Path("username")username: String
    ): Call<List<User>>


}
