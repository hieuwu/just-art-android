package com.hieuwu.justart.presentation.artworkdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hieuwu.justart.R
import com.hieuwu.justart.databinding.*


class ArtWorkDetailsAdapter :
    ListAdapter<ArtWorkDetailDisplay, ArtWorkDetailsViewHolder>(DiffCallback) {
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ArtWorkDetailDisplay.Thumbnail -> {
                R.layout.layout_artwork_details_thumbnail_view
            }
            is ArtWorkDetailDisplay.Text -> {
                R.layout.layout_artwork_details_title_view
            }
            is ArtWorkDetailDisplay.Section -> {
                R.layout.layout_artwork_details_section_view
            }
            is ArtWorkDetailDisplay.CollapseSection -> {
                R.layout.custom_collapse_paragraph_view
            }
            else -> {
                -1
//                super.getItemViewType(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtWorkDetailsViewHolder {
        return when (viewType) {
            R.layout.layout_artwork_details_title_view -> {
                ArtWorkDetailsViewHolder.TextViewHolder(
                    LayoutArtworkDetailsTitleViewBinding
                        .inflate(LayoutInflater.from(parent.context))
                )
            }

            R.layout.layout_artwork_details_thumbnail_view -> {
                ArtWorkDetailsViewHolder.ThumbnailViewHolder(
                    LayoutArtworkDetailsThumbnailViewBinding
                        .inflate(LayoutInflater.from(parent.context))
                )
            }

            R.layout.layout_artwork_details_section_view -> {
                ArtWorkDetailsViewHolder.SectionViewHolder(
                    LayoutArtworkDetailsSectionViewBinding
                        .inflate(LayoutInflater.from(parent.context))
                )
            }

            R.layout.custom_collapse_paragraph_view -> {
                ArtWorkDetailsViewHolder.CollapseSectionViewHolder(
                    LayoutArworkDetailsCollapseSectionViewBinding
                        .inflate(LayoutInflater.from(parent.context))
                )
            }
            else -> {
                throw(Exception("view not found"))
            }
        }
    }

    override fun onBindViewHolder(holder: ArtWorkDetailsViewHolder, position: Int) {
        val artWork = getItem(position)
        when (holder) {
            is ArtWorkDetailsViewHolder.ThumbnailViewHolder -> {
                (holder as ArtWorkDetailsViewHolder.ThumbnailViewHolder).bind(artWork)
            }
            is ArtWorkDetailsViewHolder.TextViewHolder -> {
                (holder as ArtWorkDetailsViewHolder.TextViewHolder).bind(artWork)
            }
            is ArtWorkDetailsViewHolder.SectionViewHolder -> {
                (holder as ArtWorkDetailsViewHolder.SectionViewHolder).bind(artWork)
            }

            is ArtWorkDetailsViewHolder.CollapseSectionViewHolder -> {
                (holder as ArtWorkDetailsViewHolder.CollapseSectionViewHolder).bind(artWork)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ArtWorkDetailDisplay>() {
        override fun areItemsTheSame(
            oldItem: ArtWorkDetailDisplay,
            newItem: ArtWorkDetailDisplay
        ): Boolean {
            return false
        }

        override fun areContentsTheSame(
            oldItem: ArtWorkDetailDisplay,
            newItem: ArtWorkDetailDisplay
        ): Boolean {
            return false
        }
    }
}