package com.hieuwu.justart.presentation.artworkdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.ChangeTransform
import androidx.transition.Transition
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.hieuwu.justart.databinding.FragmentArtworkDetailsBinding
import com.hieuwu.justart.domain.usecases.RetrieveArtWorkDetailsUseCase
import com.hieuwu.justart.presentation.views.*
import com.hieuwu.justart.presentation.views.animation.helper.SharedFade
import com.hieuwu.justart.presentation.views.animation.helper.plusAssign
import com.hieuwu.justart.presentation.views.animation.helper.transitionTogether
import com.hieuwu.justart.utils.hideLoading
import com.hieuwu.justart.utils.showLoading
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class ArtWorkDetailsFragment : Fragment() {

    @Inject
    lateinit var retrieveArtWorkDetailsUseCase: RetrieveArtWorkDetailsUseCase
    private var artWorkId: Int = -1

    private lateinit var viewModel: ArtWorkDetailsViewModel
    private lateinit var binding: FragmentArtworkDetailsBinding

    companion object {
        const val TRANSITION_NAME_IMAGE = "image"
        const val TRANSITION_NAME_NAME = "name"
        const val TRANSITION_NAME_TOOLBAR = "toolbar"
        const val TRANSITION_NAME_BACKGROUND = "background"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artWorkId = ArtWorkDetailsFragmentArgs.fromBundle(arguments as Bundle).id
        sharedElementEnterTransition = createSharedElementTransition(LARGE_EXPAND_DURATION)
        sharedElementReturnTransition = createSharedElementTransition(LARGE_COLLAPSE_DURATION)
    }

    private fun createSharedElementTransition(duration: Long): Transition {
        return transitionTogether {
            this.duration = duration
            interpolator = FAST_OUT_SLOW_IN
            this += SharedFade()
            this += ChangeImageTransform()
            this += ChangeBounds()
            this += ChangeTransform()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtworkDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        val viewModelFactory =
            ArtWorkDetailsViewModelFactory(artWorkId, retrieveArtWorkDetailsUseCase)
        viewModel = ViewModelProvider(this, viewModelFactory)[ArtWorkDetailsViewModel::class.java]
        binding.viewModel = viewModel

        with(binding.detailsRecyclerView) {
            adapter = ArtWorkDetailsAdapter()
        }
        setupObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        postponeEnterTransition(500L, TimeUnit.MILLISECONDS)
        setupTransition(view)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            when (it) {
                true -> showLoading()
                false -> hideLoading()
            }
        }
    }

    private fun setupObservers() {
        viewModel.displayList.observe(viewLifecycleOwner) {}
        viewModel.title.observe(viewLifecycleOwner) {
            binding.toolbar.title = it
        }
        binding.errorView.setRefresh {
            viewModel.onRefresh()
        }
        viewModel.showErrorView.observe(viewLifecycleOwner) { shouldShowErrorView ->
            with(binding) {
                when (shouldShowErrorView) {
                    true -> {
                        detailsRecyclerView.visibility = View.GONE
                        errorView.visibility = View.VISIBLE
                    }
                    else -> {
                        detailsRecyclerView.visibility = View.VISIBLE
                        errorView.visibility = View.GONE
                    }
                }
            }
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupTransition(view: View) {
        ViewCompat.setTransitionName(binding.toolbar, TRANSITION_NAME_TOOLBAR)
        ViewCompat.setTransitionName(binding.detailsRecyclerView, TRANSITION_NAME_BACKGROUND)
        ViewGroupCompat.setTransitionGroup(binding.detailsRecyclerView, true)

        // Adjust the edge-to-edge display.
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.toolbar.updateLayoutParams<CollapsingToolbarLayout.LayoutParams> {
                topMargin = systemBars.top
            }
            binding.detailsRecyclerView.updatePadding(
                left = systemBars.left,
                right = systemBars.right,
                bottom = systemBars.bottom
            )
            insets
        }
    }
}