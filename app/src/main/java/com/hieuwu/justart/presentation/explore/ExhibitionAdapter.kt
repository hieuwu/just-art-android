package com.hieuwu.justart.presentation.explore

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hieuwu.justart.R
import com.hieuwu.justart.databinding.LayoutExhibitionItemBinding
import com.hieuwu.justart.domain.models.ExhibitionsDo

class ExhibitionAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ExhibitionsDo, ExhibitionAdapter.ExhibitionViewHolder>(ExhibitionAdapter) {

    class ExhibitionViewHolder(val binding: LayoutExhibitionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            val titleView = binding.root.findViewById<TextView>(R.id.titleText)
            titleView.truncateText(maxLine = 1)

            val contentTextView = binding.root.findViewById<TextView>(R.id.paragraphText)
            contentTextView.truncateText()
        }

        fun bind(exhibition: ExhibitionsDo) {
            binding.exhibition = exhibition
        }

        private fun TextView.truncateText(maxLine: Int = 2) {
            maxLines = maxLine
            ellipsize = TextUtils.TruncateAt.END
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