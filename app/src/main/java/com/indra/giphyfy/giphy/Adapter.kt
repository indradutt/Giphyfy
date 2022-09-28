package com.indra.giphyfy.giphy

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.indra.giphyfy.BR
import com.indra.giphyfy.databinding.ItemGiphyBinding
import com.indra.giphyfy.network.Giphy

class GiphyAdapter(
    private val giphyListViewModel: GiphyListViewModel
) : ListAdapter<Giphy, GiphyItemHolder>(GiphyComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyItemHolder {
        val binding = ItemGiphyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GiphyItemHolder(binding, giphyListViewModel)
    }

    override fun onBindViewHolder(holder: GiphyItemHolder, position: Int) {
        holder.bindData(getItem(position))
    }

}


class GiphyItemHolder(
    private val binding: ItemGiphyBinding,
    private val giphyListViewModel: GiphyListViewModel): RecyclerView.ViewHolder(binding.root) {

    fun bindData(giphyData: Giphy) {
        with(binding) {
            setVariable(BR.itemView, giphyData)
            setVariable(BR.viewModel, giphyListViewModel)
        }
    }
}

class GiphyComparator:  DiffUtil.ItemCallback<Giphy>() {
    override fun areItemsTheSame(oldItem: Giphy, newItem: Giphy): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Giphy, newItem: Giphy): Boolean {
        return oldItem.id == newItem.id
    }

}

@BindingAdapter("loadBitmapUrl")
fun setBitmapUrl(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
        .asBitmap()
        .load(url)
        .into(object: CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                imageView.setImageBitmap(resource)
            }
            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })
}

@BindingAdapter("loadImageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
        .load(url)
        .into(imageView)
}