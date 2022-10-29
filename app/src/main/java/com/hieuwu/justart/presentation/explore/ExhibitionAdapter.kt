package com.hieuwu.justart.presentation.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hieuwu.justart.databinding.LayoutExhibitionItemBinding
import com.hieuwu.justart.domain.models.ExhibitionsDo

class ExhibitionAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ExhibitionsDo, ExhibitionAdapter.ExhibitionViewHolder>(ExhibitionAdapter) {

    class ExhibitionViewHolder(val binding: LayoutExhibitionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exhibition: ExhibitionsDo) {
            binding.exhibition = exhibition
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ExhibitionsDo>() {
        override fun areItemsTheSame(oldItem: ExhibitionsDo, newItem: ExhibitionsDo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ExhibitionsDo, newItem: ExhibitionsDo): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(
        val itemClickListener: (exhibition: ExhibitionsDo) -> Unit
    )

    override fun onBindViewHolder(holder: ExhibitionViewHolder, position: Int) {
        val exhibition = getItem(position)
        exhibition?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExhibitionViewHolder {
        val viewHolder = ExhibitionViewHolder(
            LayoutExhibitionItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

        viewHolder.itemView.setOnClickListener {
            val item = getItem(viewHolder.adapterPosition)
            item?.let {
                onClickListener.itemClickListener(it)
            }
        }

        return ExhibitionViewHolder(
            LayoutExhibitionItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}