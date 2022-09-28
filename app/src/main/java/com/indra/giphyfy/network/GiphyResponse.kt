package com.indra.giphyfy.network

import com.google.gson.annotations.SerializedName

data class GiphyResponse(
    @SerializedName("data")
    val giphyList: List<Giphy>
)
data class Giphy(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("images")
    val images: Images
)

data class Images(
    @SerializedName("original")
    val image: Image
)

data class Image(
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: String,
    @SerializedName("height")
    val height: String
)
