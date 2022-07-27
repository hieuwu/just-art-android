package com.hieuwu.justart.presentation

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hieuwu.justart.R
import com.hieuwu.justart.domain.models.ArtWork
import com.hieuwu.justart.presentation.artworkdetails.ArtWorkDetailDisplay
import com.hieuwu.justart.presentation.artworkdetails.ArtWorkDetailsAdapter
import com.hieuwu.justart.presentation.artworks.ArtWorksAdapter
import com.hieuwu.justart.presentation.views.CollapseParagraphView

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions().placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ArtWork>?) {
    val adapter = recyclerView.adapter as ArtWorksAdapter
    adapter.submitList(data)
}

@BindingAdapter("detailData")
fun bindDetailRecyclerView(recyclerView: RecyclerView, data: List<ArtWorkDetailDisplay>?) {
    data?.let {
        val adapter = recyclerView.adapter as ArtWorkDetailsAdapter
        adapter.submitList(it)
    }
}

@BindingAdapter("contentValue")
fun bindContentValue(view: CollapseParagraphView, text: String?) {
    text?.let {
        view.setContent(text)
    }
}

@BindingAdapter("titleValue")
fun bindingTitleValue(view: CollapseParagraphView, text: String?) {
    text?.let {
        view.setTitle(text)
    }
}
