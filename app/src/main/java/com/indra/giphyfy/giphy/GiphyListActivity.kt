package com.indra.giphyfy.giphy

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.indra.giphyfy.R
import com.indra.giphyfy.databinding.ActivityGiphyListBinding
import com.indra.giphyfy.databinding.DialogGiphyBinding
import com.indra.giphyfy.network.Giphy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GiphyListActivity : AppCompatActivity() {
    private val GRID_SIZE = 3
    private lateinit var binding: ActivityGiphyListBinding

    private val giphyListViewModel: GiphyListViewModel by viewModels()

    private val giphyAdapter by lazy {
        GiphyAdapter(giphyListViewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_giphy_list)

        with(binding) {
            this.lifecycleOwner = this@GiphyListActivity
            this.viewModel = giphyListViewModel
        }

        with(binding.giphyGridView) {
            this.adapter = giphyAdapter
            this.layoutManager = GridLayoutManager(this@GiphyListActivity, GRID_SIZE)
        }

        giphyListViewModel.viewState.observe(this) {
            giphyAdapter.submitList(it.giphyList)
        }
        giphyListViewModel.viewEvent.observe(this) {
            when(it) {
                is ViewEvent.ShowDataError -> {
                    Toast.makeText(this@GiphyListActivity, it.error, Toast.LENGTH_SHORT).show()
                }
                is ViewEvent.ShowGiphyDetail -> {
                    showGiphyDetail(it.giphy)
                }
            }
        }

        giphyListViewModel.getGiphyList()
    }

    private fun showGiphyDetail(giphy:Giphy) {
        val binding = DialogGiphyBinding.inflate(
            LayoutInflater.from(this)
        )
        binding.itemView = giphy
        val dialog = Dialog(this)
        dialog.setContentView(binding.root)
        dialog.show()
    }
}