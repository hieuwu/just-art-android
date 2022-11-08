package com.hieuwu.justart.presentation.explore

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hieuwu.justart.R
import com.hieuwu.justart.databinding.LayoutArticleItemBinding
import com.hieuwu.justart.domain.models.ArticleDo

class ArticleAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ArticleDo, ArticleAdapter.ArticleViewHolder>(ArticleAdapter) {

    class ArticleViewHolder(val binding: LayoutArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            val titleView = binding.root.findViewById<TextView>(R.id.titleText)
            titleView.truncateText(maxLine = 1)

            val contentTextView = binding.root.findViewById<TextView>(R.id.paragraphText)
            contentTextView.truncateText()
        }

        fun bind(article: ArticleDo) {
            binding.article = article
        }

        private fun TextView.truncateText(maxLine: Int = 2) {
            maxLines = maxLine
            ellipsize = TextUtils.TruncateAt.END
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ArticleDo>() {
        override fun areItemsTheSame(oldItem: ArticleDo, newItem: ArticleDo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ArticleDo, newItem: ArticleDo): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(
        val itemClickListener: (exhibition: ArticleDo) -> Unit
    )

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val exhibition = getItem(position)
        exhibition?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val viewHolder = ArticleViewHolder(
            LayoutArticleItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

        viewHolder.itemView.setOnClickListener {
            val item = getItem(viewHolder.adapterPosition)
            item?.let {
                onClickListener.itemClickListener(it)
            }
        }

        return ArticleViewHolder(
            LayoutArticleItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}