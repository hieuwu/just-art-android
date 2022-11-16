package com.hieuwu.justart.presentation.artworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.hieuwu.justart.R
import com.hieuwu.justart.databinding.FragmentArtWorksBinding
import com.hieuwu.justart.domain.usecases.*
import com.hieuwu.justart.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class ArtWorksFragment : Fragment() {

    @Inject
    lateinit var retrieveArtWorksUseCase: RetrieveArtWorksUseCase

    @Inject
    lateinit var checkFavoriteArtWorkExistedUseCase: CheckFavoriteArtWorkExistedUseCase

    @Inject
    lateinit var deleteFavoriteArtWorkUseCase: DeleteFavoriteArtWorkUseCase

    @Inject
    lateinit var saveFavoriteArtWorkUseCase: SaveFavoriteArtWorkUseCase

    @Inject
    lateinit var artworkItemHelper: ArtWorkItemHelper

    private lateinit var binding: FragmentArtWorksBinding

    val viewModel: ArtWorksViewModel by viewModels()

    private var recyclerviewAdapter: ArtWorksAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupExitTransition()
        setupReEnterTransition()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        recyclerviewAdapter?.saveInstanceState(outState)
        super.onSaveInstanceState(outState)
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
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding.lifecycleOwner = this

        setupRecyclerView(binding.artWorksRecyclerView)
        if (savedInstanceState != null) {
            recyclerviewAdapter?.restoreInstanceState(savedInstanceState)
        }
        if (recyclerviewAdapter!!.expectsTransition) {
            postponeEnterTransition(500L, TimeUnit.MILLISECONDS)
        }

        val items = viewModel.artWorkList
        lifecycleScope.launch {
            // We repeat on the STARTED lifecycle because an Activity may be PAUSED
            // but still visible on the screen, for example in a multi window app
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collectLatest {
                    recyclerviewAdapter?.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                recyclerviewAdapter?.loadStateFlow?.collect {
                    binding.prependProgress.isVisible = it.source.prepend is LoadState.Loading
                    binding.appendProgress.isVisible = it.source.append is LoadState.Loading
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            when (it) {
                true -> showLoading()
                false -> hideLoading()
            }
        }
        binding.errorView.setRefresh {
            recyclerviewAdapter?.refresh()
        }

        viewModel.showError.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.errorView.visibility = View.VISIBLE
                    binding.artWorksRecyclerView.visibility = View.GONE
                }
                false -> {
                    binding.errorView.visibility = View.GONE
                    binding.artWorksRecyclerView.visibility = View.VISIBLE
                }
            }
        }

        setupWindowListener(view, binding.toolbar, binding.artWorksRecyclerView)
        setupTitle()
    }


    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerviewAdapter =
            ArtWorksAdapter(
                onReadyToTransition = { startPostponedEnterTransition() },
                artWorkItemHelper = artworkItemHelper,
                onClickListener = ArtWorksAdapter.OnClickListener(
                    shareListener = {
                        artworkItemHelper.shareArtWork(it)
                        Timber.d("Share click")
                    },
                    favouriteListener = {
                        viewModel.updateFavoriteStatus(it)
                        Timber.d("Favourite click")
                    },
                    pinListener = { Timber.d("Pin click") }
                ),
            )
        with(recyclerView) {
            adapter = recyclerviewAdapter
        }
    }

    private fun setupTitle() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
    }
}