package com.indra.giphyfy.data

import com.indra.giphyfy.giphy.Result
import com.indra.giphyfy.network.GiphyResponse

interface GiphyRepository {
    suspend fun getGiphyList(): Result<GiphyResponse>
}