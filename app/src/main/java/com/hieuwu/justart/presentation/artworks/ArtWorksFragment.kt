package com.hieuwu.justart.presentation.artworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.hieuwu.justart.R
import com.hieuwu.justart.databinding.FragmentArtWorksBinding
import com.hieuwu.justart.domain.usecases.RetrieveArtWorksUseCase
import com.hieuwu.justart.presentation.views.animation.helper.SpaceDecoration
import com.hieuwu.justart.utils.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class ArtWorksFragment : Fragment() {

    @Inject
    lateinit var retrieveArtWorksUseCase: RetrieveArtWorksUseCase
    lateinit var artworkItemHelper: ArtWorkItemHelper

    private lateinit var binding: FragmentArtWorksBinding

    private lateinit var viewModel: ArtWorksViewModel

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
        artworkItemHelper = ArtWorkItemHelperFactory.create(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        val viewModelFactory = ArtWorksViewModelFactory(retrieveArtWorksUseCase)
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
            ArtWorksAdapter(onReadyToTransition = { startPostponedEnterTransition() },
                onClickListener = ArtWorksAdapter.OnClickListener(
                    shareListener = {
                        artworkItemHelper.shareArtWork(it)
                        Timber.d("Share click")
                    },
                    favouriteListener = { Timber.d("Favourite click") },
                    pinListener = { Timber.d("Pin click") }
                ))
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