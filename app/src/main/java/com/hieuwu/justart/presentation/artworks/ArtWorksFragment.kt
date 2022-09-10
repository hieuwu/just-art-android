package com.hieuwu.justart.presentation.artworks

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hieuwu.justart.R
import com.hieuwu.justart.data.FavouriteDataStore
import com.hieuwu.justart.data.repository.ArtworkRepository
import com.hieuwu.justart.databinding.FragmentArtWorksBinding
import com.hieuwu.justart.domain.usecases.GetFavoriteUseCase
import com.hieuwu.justart.domain.usecases.RetrieveArtWorksUseCase
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
    lateinit var getFavoriteUseCase: GetFavoriteUseCase

    @Inject
    lateinit var artworkItemHelper: ArtWorkItemHelper

    @Inject
    lateinit var artworkRepository: ArtworkRepository

    private lateinit var binding: FragmentArtWorksBinding

    private lateinit var viewModel: ArtWorksViewModel

    private var recyclerviewAdapter: ArtWorksAdapter? = null

    private lateinit var favouriteDataStore: FavouriteDataStore
    private var setOfFavourite: MutableSet<String> = mutableSetOf()

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

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        val viewModelFactory = ArtWorksViewModelFactory(retrieveArtWorksUseCase, getFavoriteUseCase,
            artworkRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[ArtWorksViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupRecyclerView(binding.artWorksRecyclerView)
        if (savedInstanceState != null) {
            recyclerviewAdapter?.restoreInstanceState(savedInstanceState)
        }
        if (recyclerviewAdapter!!.expectsTransition) {
            postponeEnterTransition(500L, TimeUnit.MILLISECONDS)
        }

        val items = viewModel.items
        lifecycleScope.launch {
            // We repeat on the STARTED lifecycle because an Activity may be PAUSED
            // but still visible on the screen, for example in a multi window app
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collectLatest {
                    recyclerviewAdapter?.submitData(it)
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
            viewModel.onRefresh()
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