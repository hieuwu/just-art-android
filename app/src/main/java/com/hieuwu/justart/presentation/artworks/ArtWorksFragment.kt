package com.hieuwu.justart.presentation.artworks

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Explode
import androidx.transition.Slide
import com.google.android.material.appbar.AppBarLayout
import com.hieuwu.justart.R
import com.hieuwu.justart.databinding.FragmentArtWorksBinding
import com.hieuwu.justart.domain.usecases.RetrieveArtWorksUseCase
import com.hieuwu.justart.presentation.views.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class ArtWorksFragment : Fragment() {

    @Inject
    lateinit var retrieveArtWorksUseCase: RetrieveArtWorksUseCase

    private lateinit var binding: FragmentArtWorksBinding

    private lateinit var viewModel: ArtWorksViewModel

    private var recyclerviewAdapter: ArtWorksAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This is the transition to be used for non-shared elements when we are opening the detail
        // screen.
        exitTransition = transitionTogether {
            duration = LARGE_EXPAND_DURATION / 2
            interpolator = FAST_OUT_LINEAR_IN
            // The app bar.
            this += Slide(Gravity.TOP).apply {
                mode = Slide.MODE_OUT
                addTarget(R.id.app_bar)
            }
            // The grid items.
            this += Explode().apply {
                mode = Explode.MODE_OUT
                excludeTarget(R.id.app_bar, true)
            }
        }

        // This is the transition to be used for non-shared elements when we are return back from
        // the detail screen.
        reenterTransition = transitionTogether {
            duration = LARGE_COLLAPSE_DURATION / 2
            interpolator = LINEAR_OUT_SLOW_IN
            // The app bar.
            this += Slide(Gravity.TOP).apply {
                mode = Slide.MODE_IN
                addTarget(R.id.app_bar)
            }
            // The grid items.
            this += Explode().apply {
                // The grid items should start imploding after the app bar is in.
                startDelay = LARGE_COLLAPSE_DURATION / 2
                mode = Explode.MODE_IN
                excludeTarget(R.id.app_bar, true)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtWorksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = ArtWorksViewModelFactory(retrieveArtWorksUseCase)
        viewModel = ViewModelProvider(this, viewModelFactory)[ArtWorksViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setObservers()
        setupRecyclerView(binding.artWorksRecyclerView)
        if (savedInstanceState != null) {
            recyclerviewAdapter?.restoreInstanceState(savedInstanceState)
        }
        if (recyclerviewAdapter!!.expectsTransition) {
            // We are transitioning back from CheeseDetailFragment.
            // Postpone the transition animation until the destination item is ready.
            postponeEnterTransition(500L, TimeUnit.MILLISECONDS)
        }

//        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        val grid: RecyclerView = binding.artWorksRecyclerView

        // Adjust the edge-to-edge display.
        val gridPadding = resources.getDimensionPixelSize(R.dimen.spacing_tiny)
        ViewCompat.setOnApplyWindowInsetsListener(view.parent as View) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.toolbar.updateLayoutParams<AppBarLayout.LayoutParams> {
                topMargin = systemBars.top
            }
            grid.updatePadding(
                left = gridPadding + systemBars.left,
                right = gridPadding + systemBars.right,
                bottom = gridPadding + systemBars.bottom
            )
            insets
        }

        grid.addItemDecoration(
            SpaceDecoration(resources.getDimensionPixelSize(R.dimen.spacing_tiny))
        )
//        grid.adapter = recyclerviewAdapter
    }

    private fun setObservers() {
        viewModel.navigateToSelectedProperty.observe(this.viewLifecycleOwner) {
            it?.let {
                navigateToArtWorksDetail(it.id)
                viewModel.displayPropertyDetailsComplete()
            }
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerviewAdapter = ArtWorksAdapter(
            ArtWorksAdapter.OnClickListener(
                clickListener = { viewModel.displayPropertyDetails(it) },
            ),
            onReadyToTransition = { startPostponedEnterTransition() }
        )
        with(recyclerView) {
            adapter = recyclerviewAdapter
        }
    }

    private fun navigateToArtWorksDetail(id: Int) {
        val direction =
            ArtWorksFragmentDirections.actionArtWorksFragmentToArtDetailsFragment(id = id)
        findNavController().navigate(direction)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        recyclerviewAdapter?.saveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }
//
}