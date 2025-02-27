package com.hieuwu.justart.presentation.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.hieuwu.justart.R
import com.hieuwu.justart.databinding.LayoutArtWorksItemBinding
import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.presentation.views.doOnEnd
import com.hieuwu.justart.utils.ArtWorkItemHelper

private const val STATE_LAST_SELECTED_ID = "last_selected_id"

class SearchResultAdapter(
    private val onClickListener: OnClickListener,
    private val onReadyToTransition: () -> Unit,
    private val artWorkItemHelper: ArtWorkItemHelper
) :
    ListAdapter<ArtWorkDo, SearchResultAdapter.ArtWorksViewHolder>(DiffCallback) {
    private var lastSelectedId: Int? = null

    val expectsTransition: Boolean
        get() = lastSelectedId != null

    class ArtWorksViewHolder(var binding: LayoutArtWorksItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.artWorksImage
        val title = binding.artWorksTitle
        val card = binding.cardView
        fun bind(artWork: ArtWorkDo) {
            binding.artWork = artWork
            binding.executePendingBindings()
            updateFavouriteIcon(artWork.isFavorite)
        }

        fun updateFavouriteIcon(isFavorite: Boolean) {
            val favoriteImage = when (isFavorite) {
                true -> R.drawable.ic_baseline_favorite_24
                false -> R.drawable.ic_outline_favorite_border_24
            }
            binding.favouriteBtn.setImageResource(favoriteImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtWorksViewHolder {
        return ArtWorksViewHolder(
            LayoutArtWorksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply {
            itemView.setOnClickListener {
                val artWork = getItem(adapterPosition)
                artWorkItemHelper.clickArtWork(it, title, image, card, artWork.id)
            }
            binding.favouriteBtn.setOnClickListener {
                val artWork = getItem(adapterPosition)
                with(artWork) {
                    onClickListener.favouriteListener(this)
                    updateFavouriteIcon(isFavorite)
                }
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

    class OnClickListener(
        val favouriteListener: (artwork: ArtWorkDo) -> Unit,
        val pinListener: (artwork: ArtWorkDo) -> Unit,
        val shareListener: (artwork: ArtWorkDo) -> Unit,
    )
}