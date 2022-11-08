package com.hieuwu.justart.presentation.explore

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hieuwu.justart.R
import com.hieuwu.justart.databinding.LayoutEventItemBinding
import com.hieuwu.justart.domain.models.EventDo

class EventAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<EventDo, EventAdapter.EventViewHolder>(EventAdapter) {

    class EventViewHolder(val binding: LayoutEventItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            val titleView = binding.root.findViewById<TextView>(R.id.titleText)
            titleView.truncateText(maxLine = 1)

            val contentTextView = binding.root.findViewById<TextView>(R.id.paragraphText)
            contentTextView.truncateText()
        }

        fun bind(exhibition: EventDo) {
            binding.event = exhibition
        }

        private fun TextView.truncateText(maxLine: Int = 2) {
            maxLines = maxLine
            ellipsize = TextUtils.TruncateAt.END
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<EventDo>() {
        override fun areItemsTheSame(oldItem: EventDo, newItem: EventDo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: EventDo, newItem: EventDo): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(
        val itemClickListener: (exhibition: EventDo) -> Unit
    )

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val exhibition = getItem(position)
        exhibition?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val viewHolder = EventViewHolder(
            LayoutEventItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

        viewHolder.itemView.setOnClickListener {
            val item = getItem(viewHolder.adapterPosition)
            item?.let {
                onClickListener.itemClickListener(it)
            }
        }

        return EventViewHolder(
            LayoutEventItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}