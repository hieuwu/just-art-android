package com.hieuwu.justart.presentation.artworkdetails

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.hieuwu.justart.databinding.LayoutArtworkDetailsSectionViewBinding
import com.hieuwu.justart.databinding.LayoutArtworkDetailsThumbnailViewBinding
import com.hieuwu.justart.databinding.LayoutArtworkDetailsTitleViewBinding
import com.hieuwu.justart.databinding.LayoutArworkDetailsCollapseSectionViewBinding

abstract class ArtWorkDetailsViewHolder(private var binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(artWorkDetailDisplay: ArtWorkDetailDisplay)

    class ThumbnailViewHolder(private var binding: ViewBinding) :
        ArtWorkDetailsViewHolder(binding) {
        override fun bind(artWorkDetailDisplay: ArtWorkDetailDisplay) {
            val bi = binding as LayoutArtworkDetailsThumbnailViewBinding
            bi.displayItem = artWorkDetailDisplay as ArtWorkDetailDisplay.Thumbnail
            bi.executePendingBindings()
        }

    }

    class TextViewHolder(private var binding: ViewBinding) : ArtWorkDetailsViewHolder(binding) {
        override fun bind(artWorkDetailDisplay: ArtWorkDetailDisplay) {
            val bi = binding as LayoutArtworkDetailsTitleViewBinding
            bi.displayItem = artWorkDetailDisplay as ArtWorkDetailDisplay.Text
            bi.executePendingBindings()
        }
    }

    class SectionViewHolder(private var binding: ViewBinding) : ArtWorkDetailsViewHolder(binding) {
        override fun bind(artWorkDetailDisplay: ArtWorkDetailDisplay) {
            val bi = binding as LayoutArtworkDetailsSectionViewBinding
            bi.displayItem = artWorkDetailDisplay as ArtWorkDetailDisplay.Section
            bi.executePendingBindings()
        }
    }

    class CollapseSectionViewHolder(private var binding: ViewBinding) :
        ArtWorkDetailsViewHolder(binding) {
        override fun bind(artWorkDetailDisplay: ArtWorkDetailDisplay) {
            val bi = binding as LayoutArworkDetailsCollapseSectionViewBinding
            bi.displayItem = artWorkDetailDisplay as ArtWorkDetailDisplay.CollapseSection
            bi.executePendingBindings()
        }
    }

}