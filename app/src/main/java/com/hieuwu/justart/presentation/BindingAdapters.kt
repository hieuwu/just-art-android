package com.hieuwu.justart.presentation

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hieuwu.justart.R
import com.hieuwu.justart.presentation.artworkdetails.ArtWorkDetailDisplay
import com.hieuwu.justart.presentation.artworkdetails.ArtWorkDetailsAdapter
import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.domain.models.ExhibitionsDo
import com.hieuwu.justart.presentation.explore.ExhibitionAdapter
import com.hieuwu.justart.presentation.search.SearchResultAdapter
import com.hieuwu.justart.presentation.views.custom.CollapseParagraphView
import com.hieuwu.justartsdk.exhibitions.v1.domain.Exhibition

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
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ArtWorkDo>?) {
    val adapter = recyclerView.adapter as SearchResultAdapter
    adapter.submitList(data)
}

@BindingAdapter("detailData")
fun bindDetailRecyclerView(recyclerView: RecyclerView, data: List<ArtWorkDetailDisplay>?) {
    data?.let {
        val adapter = recyclerView.adapter as ArtWorkDetailsAdapter
        adapter.submitList(it)
    }
}


@BindingAdapter("exhibitionData")
fun bindExhibitionRecyclerView(recyclerView: RecyclerView, data: List<ExhibitionsDo>?) {
    data?.let {
        val adapter = recyclerView.adapter as ExhibitionAdapter?
        adapter?.submitList(data)
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
