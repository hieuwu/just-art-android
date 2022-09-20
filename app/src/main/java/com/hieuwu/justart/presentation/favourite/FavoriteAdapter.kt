package com.hieuwu.justart.presentation.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.hieuwu.justart.databinding.LayoutFavoriteItemBinding
import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.presentation.views.doOnEnd
import com.hieuwu.justart.utils.ArtWorkItemHelper

private const val STATE_LAST_SELECTED_ID = "last_selected_id"

class FavoriteAdapter(
    private val onReadyToTransition: () -> Unit,
    private val artWorkItemHelper: ArtWorkItemHelper
) : ListAdapter<ArtWorkDo, FavoriteAdapter.FavoriteViewHolder>(DiffCallback) {

    private var lastSelectedId: Int? = null

    val expectsTransition: Boolean
        get() = lastSelectedId != null

    class FavoriteViewHolder(var binding: LayoutFavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.favoriteImage
        val title = binding.favoriteTitle
        val card = binding.cardView
        fun bind(artWork: ArtWorkDo) {
            binding.favoriteTitle.text = artWork.title
            binding.artWork = artWork
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutFavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply {
            itemView.setOnClickListener {
                val artWork = getItem(adapterPosition)
                artWorkItemHelper.clickArtWork(it, title, image, card, artWork.id)
            }
        }
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val artWork = getItem(position)

        // Each of the shared elements has to have a unique transition name, not just in this grid
        // item, but in the entire fragment.
        ViewCompat.setTransitionName(holder.image, "image-${artWork.id}")
        ViewCompat.setTransitionName(holder.title, "name-${artWork.id}")
        ViewCompat.setTransitionName(holder.card, "card-${artWork.id}")

        // Load the image asynchronously. See CheeseDetailFragment.kt about "dontTransform()"
        var requestBuilder = Glide.with(holder.image).load(artWork.imageUrl).dontTransform()
        if (artWork.id == lastSelectedId) {
            requestBuilder = requestBuilder
                .priority(Priority.IMMEDIATE)
                .doOnEnd {
                    // We have loaded the image for the transition destination. It is ready to start
                    // the transition postponed in the fragment.
                    onReadyToTransition()
                    lastSelectedId = null
                }
        }
        requestBuilder.into(holder.image)

        holder.bind(artWork)
    }

    fun saveInstanceState(outState: Bundle) {
        lastSelectedId?.let { id ->
            outState.putInt(STATE_LAST_SELECTED_ID, id)
        }
    }

    fun restoreInstanceState(state: Bundle) {
        if (lastSelectedId == null && state.containsKey(STATE_LAST_SELECTED_ID)) {
            lastSelectedId = state.getInt(STATE_LAST_SELECTED_ID)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ArtWorkDo>() {
        override fun areItemsTheSame(oldItem: ArtWorkDo, newItem: ArtWorkDo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ArtWorkDo, newItem: ArtWorkDo): Boolean {
            return oldItem.id == newItem.id
        }
    }
}