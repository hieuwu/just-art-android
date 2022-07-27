package com.hieuwu.justart.presentation.artworkdetails

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.hieuwu.justart.databinding.LayoutArtWorksItemBinding
import com.hieuwu.justart.databinding.LayoutArtworkDetailsSectionViewBinding
import com.hieuwu.justart.databinding.LayoutArworkDetailsCollapseSectionViewBinding
import com.hieuwu.justart.presentation.artworks.ArtWorksAdapter
import kotlinx.coroutines.NonDisposableHandle.parent

open class ArtWorkDetailsViewHolder(private var binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(artWorkDetailDisplay: ArtWorkDetailDisplay) {
        when (binding) {
            is LayoutArworkDetailsCollapseSectionViewBinding -> {
                val bi = binding as LayoutArworkDetailsCollapseSectionViewBinding
                bi.displayItem = artWorkDetailDisplay as ArtWorkDetailDisplay.CollapseSection
                bi.executePendingBindings()
            }
        }
//        when (artWorkDetailDisplay) {
//            is ArtWorkDetailDisplay.Thumbnail -> {
//
//            }
//            is ArtWorkDetailDisplay.Text -> {
//
//            }
//            is ArtWorkDetailDisplay.Section -> {
//
//            }
//            is ArtWorkDetailDisplay.CollapseSection -> {
//
//            }
//        }
    }

    class ThumbnailViewHolder(private var binding: ViewBinding) : ArtWorkDetailsViewHolder(binding)
    class TextViewHolder(private var binding: ViewBinding) : ArtWorkDetailsViewHolder(binding)
    class SectionViewHolder(private var binding: ViewBinding) : ArtWorkDetailsViewHolder(binding)
    class CollapseSectionViewHolder(private var binding: ViewBinding) :
        ArtWorkDetailsViewHolder(binding)

}