package com.indra.giphyfy.data

import com.indra.giphyfy.data.source.DataSource
import com.indra.giphyfy.giphy.Result
import com.indra.giphyfy.network.GiphyResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class GiphyRepositoryImpl @Inject constructor(
    private val remoteDataSource: DataSource,
    private val ioDispatcher: CoroutineDispatcher
): GiphyRepository {
    override suspend fun getGiphyList(): Result<GiphyResponse> {
        return withContext(ioDispatcher) {
            try {
                val result = remoteDataSource.getGiphyList()
                Result.Success(result)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}