package com.hieuwu.justart.presentation.favourite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hieuwu.justart.R
import com.hieuwu.justart.databinding.FragmentFavouriteBinding
import com.hieuwu.justart.domain.usecases.GetFavoriteArtWorkUseCase
import com.hieuwu.justart.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    @Inject
    lateinit var getFavoriteArtWorkUseCase: GetFavoriteArtWorkUseCase

    @Inject
    lateinit var artworkItemHelper: ArtWorkItemHelper

    private lateinit var binding: FragmentFavouriteBinding

    private lateinit var viewModel: FavoriteViewModel

    private var recyclerviewAdapter: FavoriteAdapter? = null

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
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = FavoriteViewModelFactory(
            getFavoriteArtWorkUseCase = getFavoriteArtWorkUseCase
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]

        recyclerviewAdapter = FavoriteAdapter(
            onReadyToTransition = { startPostponedEnterTransition() },
            artWorkItemHelper = artworkItemHelper
        )

        binding.favoriteRecyclerView.adapter = recyclerviewAdapter

        viewModel.isLoading.observe(viewLifecycleOwner) {
            when (it) {
                true -> showLoading()
                false -> hideLoading()
            }
        }

        viewModel.updateFavoriteArtWork()

        viewModel.favoriteArtWork.observe(viewLifecycleOwner) {
            recyclerviewAdapter?.submitList(it)
        }

        if (savedInstanceState != null) {
            recyclerviewAdapter?.restoreInstanceState(savedInstanceState)
        }
        if (recyclerviewAdapter!!.expectsTransition) {
            postponeEnterTransition(500L, TimeUnit.MILLISECONDS)
        }

    }
}