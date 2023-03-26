package com.example.retrofitapp

import com.example.retrofitapp.Constants.END_POINT_GET
import com.example.retrofitapp.Constants.END_POINT_POST
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PostAPI {
    @GET(END_POINT_GET)
    suspend fun gatAllPosts():Response<List<PostItem>>

    @POST(END_POINT_POST)
    suspend fun posting(@Body postItem: PostItem):Response<PostItem>
}