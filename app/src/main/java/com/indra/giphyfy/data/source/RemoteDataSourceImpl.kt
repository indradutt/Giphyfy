package com.indra.giphyfy.data.source

import com.indra.giphyfy.network.GiphyResponse
import com.indra.giphyfy.network.GiphyApiService
import javax.inject.Inject


class RemoteDataSourceImpl @Inject constructor(
    private val apiService: GiphyApiService
): DataSource {
    override suspend fun getGiphyList(): GiphyResponse {
        return apiService.getGiphyList()
    }
}