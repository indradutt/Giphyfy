package com.indra.giphyfy.giphy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indra.giphyfy.data.GiphyRepository
import com.indra.giphyfy.network.Giphy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class GiphyListViewModel @Inject constructor(
    private val repository: GiphyRepository
): ViewModel() {
    private var _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    private var _viewEvent = MutableLiveData<ViewEvent>()
    val viewEvent: LiveData<ViewEvent>
        get() = _viewEvent

    fun getGiphyList() {
        _viewState.value = ViewState(loading = true)
        viewModelScope.launch {
            val result = repository.getGiphyList()
            _viewState.value = when(result) {
                is Result.Success -> {
                    viewState.value?.copy(loading = false, giphyList = result.data.giphyList)
                }
                is Result.Error -> {
                    _viewEvent.value = ViewEvent.ShowDataError(result.exception.message)
                    viewState.value?.copy(loading = false)
                }
            }
        }
    }

    fun onClickGiphy(giphy: Giphy) {
        _viewEvent.value = ViewEvent.ShowGiphyDetail(giphy)
    }
}

data class ViewState(
    val loading: Boolean,
    val giphyList: List<Giphy> = emptyList()
)

sealed class ViewEvent{
    data class ShowDataError(val error: String?): ViewEvent()
    data class ShowGiphyDetail(val giphy: Giphy): ViewEvent()
}

sealed class Result<out R> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()
}
