package com.hieuwu.justart.presentation.artworks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hieuwu.justart.databinding.LayoutArtWorksItemBinding
import com.hieuwu.justart.domain.models.ArtWorkDo

class ArtWorksAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ArtWorkDo, ArtWorksAdapter.ArtWorksViewHolder>(DiffCallback) {

    class ArtWorksViewHolder(var binding: LayoutArtWorksItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(artWork: ArtWorkDo) {
            binding.artWork = artWork
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtWorksViewHolder {
        return ArtWorksViewHolder(
            LayoutArtWorksItemBinding.inflate(LayoutInflater.from(parent.context))
        ).apply {
            binding.root.setOnClickListener {
                val artWork = getItem(adapterPosition)
                onClickListener.onClick(artWork)
            }
            binding.favouriteBtn.setOnClickListener {
                val artWork = getItem(adapterPosition)
                onClickListener.favouriteListener(artWork)
            }

            binding.pinBtn.setOnClickListener {
                val artWork = getItem(adapterPosition)
                onClickListener.pinListener(artWork)
            }

            binding.shareBtn.setOnClickListener {
                val artWork = getItem(adapterPosition)
                onClickListener.shareListener(artWork)
            }

        }
    }

    override fun onBindViewHolder(holder: ArtWorksViewHolder, position: Int) {
        val artWork = getItem(position)
        holder.bind(artWork)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ArtWorkDo>() {
        override fun areItemsTheSame(oldItem: ArtWorkDo, newItem: ArtWorkDo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ArtWorkDo, newItem: ArtWorkDo): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(
        val clickListener: (artWork: ArtWorkDo) -> Unit,
        val favouriteListener: (artwork: ArtWorkDo) -> Unit,
        val pinListener: (artwork: ArtWorkDo) -> Unit,
        val shareListener: (artwork: ArtWorkDo) -> Unit,
    ) {
        fun onClick(artWork: ArtWorkDo) = clickListener(artWork)
    }
}