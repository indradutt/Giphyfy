package com.indra.giphyfy.data

import com.indra.giphyfy.giphy.Result
import com.indra.giphyfy.network.Giphy
import com.indra.giphyfy.network.GiphyResponse
import com.indra.giphyfy.network.Image
import com.indra.giphyfy.network.Images
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GiphyRepositoryImplTest{
    private lateinit var dataSource: FakeDataSource

    private lateinit var underTest: GiphyRepositoryImpl

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)
    private val testScope = TestScope(testDispatcher)

    private val giphy1 = Giphy(
        "id01",
        title = "title01",
        images = Images(
            Image(
                "url01",
                width = "100",
                height = "100"
            )
        )
    )

    private val giphy2 = Giphy(
        "id02",
        title = "title02",
        images = Images(
            Image(
                "url02",
                width = "100",
                height = "100"
            )
        )
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        dataSource = FakeDataSource()
        dataSource.addGiphy(giphy1, giphy2)

        underTest = GiphyRepositoryImpl(
            dataSource,
            testDispatcher
        )
    }

    @After
    fun cleanUp() = Dispatchers.resetMain()

    @Test
    fun `verify giphy api results`() = testScope.runTest {
        val result = underTest.getGiphyList()

        assertTrue(result is Result.Success)
        result as Result.Success
        assertEquals(
            GiphyResponse(listOf(giphy1, giphy2)),
            result.data
        )
    }
}