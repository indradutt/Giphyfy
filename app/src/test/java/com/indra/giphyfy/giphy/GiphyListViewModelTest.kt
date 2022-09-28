package com.indra.giphyfy.giphy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.indra.giphyfy.data.FakeDataSource
import com.indra.giphyfy.data.FakeGiphyRepository
import com.indra.giphyfy.network.Giphy
import com.indra.giphyfy.network.Image
import com.indra.giphyfy.network.Images
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GiphyListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)
    private val testScope = TestScope(testDispatcher)

    private lateinit var fakeDataSource: FakeDataSource
    private lateinit var fakeGiphyRepository: FakeGiphyRepository
    private lateinit var underTest: GiphyListViewModel

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
        fakeDataSource = FakeDataSource()
        fakeDataSource.addGiphy(giphy1, giphy2)

        fakeGiphyRepository = FakeGiphyRepository(fakeDataSource, testDispatcher)

        underTest = GiphyListViewModel(fakeGiphyRepository)
    }

    @After
    fun cleanUp() = Dispatchers.resetMain()

    @Test
    fun `when getter is called verify view state loading`() = testScope.runTest {
        underTest.getGiphyList()

        assertEquals(true, underTest.viewState.value?.loading)

        runCurrent()

        assertEquals(false, underTest.viewState.value?.loading)
    }

    @Test
    fun `when getter is called verify view state list`() = testScope.runTest{
        underTest.getGiphyList()
        advanceUntilIdle()

        assertEquals(listOf(giphy1, giphy2), underTest.viewState.value?.giphyList)
    }

    @Test
    fun `when getter is called with error verify view state list`() = testScope.runTest{
        fakeGiphyRepository.setReturnError(true)
        underTest.getGiphyList()

        advanceUntilIdle()

        with(underTest.viewState.value){
            assertEquals(0, this?.giphyList?.size)
            assertEquals(false, this?.loading)
        }
        assertEquals(ViewEvent.ShowDataError("No Data"), underTest.viewEvent.value)
    }
}