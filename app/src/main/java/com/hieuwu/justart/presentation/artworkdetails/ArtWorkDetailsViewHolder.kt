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
            with(binding as LayoutArtworkDetailsThumbnailViewBinding) {
                displayItem = artWorkDetailDisplay as ArtWorkDetailDisplay.Thumbnail
                executePendingBindings()
            }
        }

    }

    class TextViewHolder(private var binding: ViewBinding) : ArtWorkDetailsViewHolder(binding) {
        override fun bind(artWorkDetailDisplay: ArtWorkDetailDisplay) {
            with(binding as LayoutArtworkDetailsTitleViewBinding) {
                displayItem = artWorkDetailDisplay as ArtWorkDetailDisplay.Text
                executePendingBindings()
            }
        }
    }

    class SectionViewHolder(private var binding: ViewBinding) : ArtWorkDetailsViewHolder(binding) {
        override fun bind(artWorkDetailDisplay: ArtWorkDetailDisplay) {
            with(binding as LayoutArtworkDetailsSectionViewBinding) {
                displayItem = artWorkDetailDisplay as ArtWorkDetailDisplay.Section
                executePendingBindings()
            }
        }
    }

    class CollapseSectionViewHolder(private var binding: ViewBinding) :
        ArtWorkDetailsViewHolder(binding) {
        override fun bind(artWorkDetailDisplay: ArtWorkDetailDisplay) {
            with(binding as LayoutArworkDetailsCollapseSectionViewBinding) {
                displayItem = artWorkDetailDisplay as ArtWorkDetailDisplay.CollapseSection
                executePendingBindings()
            }
        }
    }

}