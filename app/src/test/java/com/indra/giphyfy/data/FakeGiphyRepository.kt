package com.indra.giphyfy.data

import com.indra.giphyfy.data.source.DataSource
import com.indra.giphyfy.giphy.Result
import com.indra.giphyfy.network.GiphyResponse
import kotlinx.coroutines.CoroutineDispatcher

class FakeGiphyRepository(
    private val dataSource: DataSource,
    private val dispatcher: CoroutineDispatcher
    ): GiphyRepository {

    private var shouldReturnError: Boolean = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }
    override suspend fun getGiphyList(): Result<GiphyResponse> {
        return if (shouldReturnError) {
            Result.Error(Exception("No Data"))
        } else {
            Result.Success(dataSource.getGiphyList())
        }
    }
}