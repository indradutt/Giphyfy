package com.indra.giphyfy.data.source

import com.indra.giphyfy.network.GiphyResponse

interface DataSource {
    suspend fun getGiphyList(): GiphyResponse
}