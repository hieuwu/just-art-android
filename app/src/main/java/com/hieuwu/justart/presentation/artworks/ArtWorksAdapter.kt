package com.hieuwu.justart.presentation.artworks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hieuwu.justart.databinding.LayoutArtWorksItemBinding
import com.hieuwu.justart.domain.models.ArtWork

class ArtWorksAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ArtWork, ArtWorksAdapter.ArtWorksViewHolder>(DiffCallback) {

    class ArtWorksViewHolder(private var binding: LayoutArtWorksItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(artWork: ArtWork) {
            binding.artWork = artWork
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtWorksViewHolder {
        return ArtWorksViewHolder(
            LayoutArtWorksItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ArtWorksViewHolder, position: Int) {
        val artWork = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(artWork)
        }
        holder.bind(artWork)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ArtWork>() {
        override fun areItemsTheSame(oldItem: ArtWork, newItem: ArtWork): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ArtWork, newItem: ArtWork): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(
        val clickListener: (artWork: ArtWork) -> Unit
    ) {
        fun onClick(artWork: ArtWork) = clickListener(artWork)
    }
}