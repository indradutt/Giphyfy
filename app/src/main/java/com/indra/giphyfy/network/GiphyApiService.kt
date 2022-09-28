package com.indra.giphyfy.network

import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.giphy.com/v1/"
interface GiphyApiService {
    @GET("gifs/trending")
    suspend fun getGiphyList(
        @Query("api_key") apiKey: String = "sfZNxqjaGq3jhKaiSBfPGed0rJaiF2no",
        @Query("limit") limit: String = "25",
        @Query("rating") rating: String = "g"
    ): GiphyResponse
}