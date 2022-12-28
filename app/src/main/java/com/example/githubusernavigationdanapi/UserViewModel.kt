package com.example.githubusernavigationdanapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusernavigationdanapi.api.RetrofitClient
import com.example.githubusernavigationdanapi.model.DetailUsers
import com.example.githubusernavigationdanapi.model.User
import com.example.githubusernavigationdanapi.model.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    companion object{
        const val RESPONSE = "response code"
        const val FAIL = "Failure"
    }

    val listUsers = MutableLiveData<List<User>>()
    val followingUsers = MutableLiveData<List<User>>()
    val followersUser = MutableLiveData<List<User>>()
    val detailUsers = MutableLiveData<DetailUsers>()


    fun setSearchUser(query: String){
        RetrofitClient.apiInstance
            .getSearchUser(query)
            .enqueue(object : Callback<UsersResponse> {
                override fun onResponse(
                    call: Call<UsersResponse>,
                    response: Response<UsersResponse>
                ) {
                    if (response.isSuccessful){
                        listUsers.postValue(response.body()?.items)
                        Log.d(RESPONSE, response.code().toString())
                    }
                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    t.message?.let { Log.d(FAIL, it) }
                }
            })
    }

    fun getSearchUser(): LiveData<List<User>> {
        return listUsers
    }

    fun setDetailUser(username: String){
        RetrofitClient.apiInstance
            .getDetailUser(username)
            .enqueue(object : Callback<DetailUsers> {
                override fun onResponse(call: Call<DetailUsers>, response: Response<DetailUsers>) {
                    if (response.isSuccessful){
                        detailUsers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUsers>, t: Throwable) {
                    t.message?.let { Log.d(FAIL, it) }
                }
            })
    }

    fun getUserDetail(): LiveData<DetailUsers> {
        return detailUsers

    }



    fun setDetailFollowing(username: String){
        RetrofitClient.apiInstance
            .getFollowingUser(username)
            .enqueue(object : Callback<List<User>>{
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    followingUsers.postValue(response.body())
                    Log.d(RESPONSE, response.code().toString())
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    t.message?.let { Log.d(FAIL, it) }
                }
            })
    }

    fun getDetailFollowing() : LiveData<List<User>>{
        return followingUsers
    }

    fun setDetailFollower(username: String){
        RetrofitClient.apiInstance
            .getFollowersUser(username)
            .enqueue(object : Callback<List<User>>{
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    followersUser.postValue(response.body())
                    Log.d(RESPONSE, response.code().toString())
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    t.message?.let { Log.d(FAIL, it) }
                }
            })
    }

    fun getDetailFollower() : LiveData<List<User>>{
        return followersUser
    }

}