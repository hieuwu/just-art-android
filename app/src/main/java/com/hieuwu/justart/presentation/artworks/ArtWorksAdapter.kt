package com.hieuwu.justart.presentation.artworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.hieuwu.justart.R
import com.hieuwu.justart.databinding.LayoutArtWorksItemBinding
import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.presentation.artworkdetails.ArtWorkDetailsFragment
import com.hieuwu.justart.presentation.artworkdetails.ArtWorkDetailsFragmentArgs
import com.hieuwu.justart.presentation.views.doOnEnd

private const val STATE_LAST_SELECTED_ID = "last_selected_id"

class ArtWorksAdapter(
    private val onClickListener: OnClickListener? = null,
    private val onReadyToTransition: () -> Unit
) :
    ListAdapter<ArtWorkDo, ArtWorksAdapter.ArtWorksViewHolder>(DiffCallback) {
    private var lastSelectedId: Int? = null

    val expectsTransition: Boolean
        get() = lastSelectedId != null

    class ArtWorksViewHolder(private var binding: LayoutArtWorksItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.artWorksImage
        val title = binding.artWorksTitle
        val card = binding.cardView
        fun bind(artWork: ArtWorkDo) {
            binding.artWork = artWork
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtWorksViewHolder {
        return ArtWorksViewHolder(
            LayoutArtWorksItemBinding.inflate(LayoutInflater.from(parent.context))
        ).apply {
            itemView.setOnClickListener {
                val artWork = getItem(adapterPosition)
                it?.findNavController()?.navigate(
                    R.id.action_artWorksFragment_to_artDetailsFragment,
                    ArtWorkDetailsFragmentArgs(artWork.id).toBundle(),
                    null,
                    FragmentNavigatorExtras(
                        // We expand the card into the background.
                        // The fragment will use this first element as the epicenter of all the
                        // fragment transitions, including Explode for non-shared elements.
                        title to ArtWorkDetailsFragment.TRANSITION_NAME_NAME,
                        // The image is the focal element in this shared element transition.
                        image to ArtWorkDetailsFragment.TRANSITION_NAME_IMAGE,
                        card to ArtWorkDetailsFragment.TRANSITION_NAME_BACKGROUND
//                        card to ArtWorkDetailsFragment.TRANSITION_NAME_BACKGROUND
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ArtWorksViewHolder, position: Int) {
        val artWork = getItem(position)
//        holder.itemView.setOnClickListener {
//            onClickListener?.onClick(artWork)
//        }

        // Each of the shared elements has to have a unique transition name, not just in this grid
        // item, but in the entire fragment.
        ViewCompat.setTransitionName(holder.image, "image-${artWork.id}")
        ViewCompat.setTransitionName(holder.title, "name-${artWork.id}")
        ViewCompat.setTransitionName(holder.card, "card-${artWork.id}")

//        ViewCompat.setTransitionName(holder.card, "card-${artWork.id}")

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
        val clickListener: ((artWork: ArtWorkDo) -> Unit)? = null
    ) {
        fun onClick(artWork: ArtWorkDo) = clickListener?.invoke(artWork)
    }
}