package com.indra.giphyfy.data

import com.indra.giphyfy.data.source.DataSource
import com.indra.giphyfy.network.Giphy
import com.indra.giphyfy.network.GiphyResponse

class FakeDataSource: DataSource {
    private val dataSet = mutableListOf<Giphy>()
    override suspend fun getGiphyList(): GiphyResponse {
        return GiphyResponse(dataSet)
    }

    fun addGiphy(vararg giphy: Giphy) {
        dataSet.addAll(giphy)
    }

    fun clearAll(vararg giphy: Giphy) {
        dataSet.clear()
    }
}