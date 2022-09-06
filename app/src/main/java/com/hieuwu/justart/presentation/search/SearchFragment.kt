package com.hieuwu.justart.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.hieuwu.justart.R
import com.hieuwu.justart.databinding.FragmentSearchBinding
import com.hieuwu.justart.domain.usecases.SearchArtWorkUseCase
import com.hieuwu.justart.presentation.artworks.ArtWorksAdapter
import com.hieuwu.justart.presentation.utils.focusAndShowKeyboard
import com.hieuwu.justart.presentation.views.animation.helper.SpaceDecoration
import com.hieuwu.justart.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

const val SEARCH_TIME_DELAY = 1500L

@AndroidEntryPoint
class SearchFragment : Fragment() {

    @Inject
    lateinit var searchArtWorkUseCase: SearchArtWorkUseCase
    lateinit var artWorkItemHelper: ArtWorkItemHelper

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SearchViewModel
    private var recyclerviewAdapter: ArtWorksAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artWorkItemHelper = ArtWorkItemHelperFactory.create(requireContext())
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
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = SearchViewModelFactory(searchArtWorkUseCase)
        viewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.clearText.setOnClickListener {
            binding.searchView.text = null
        }

        viewModel.artWorksList.observe(viewLifecycleOwner) {
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            when (it) {
                true -> showLoading()
                false -> hideLoading()
            }
        }
        setupWindowListener(view, binding.toolbar, binding.artWorksRecyclerView)

        setupWindowListener(view)
        setupSearchView()
        setupRecyclerView(binding.artWorksRecyclerView)
    }


    private fun setupWindowListener(view: View) {
        val gridPadding = resources.getDimensionPixelSize(R.dimen.spacing_tiny)
        ViewCompat.setOnApplyWindowInsetsListener(view.parent as View) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.toolbar.updateLayoutParams<AppBarLayout.LayoutParams> {
                topMargin = systemBars.top
            }
            binding.artWorksRecyclerView.updatePadding(
                left = gridPadding + systemBars.left,
                right = gridPadding + systemBars.right,
                bottom = gridPadding + systemBars.bottom
            )
            insets
        }

        binding.artWorksRecyclerView.addItemDecoration(
            SpaceDecoration(resources.getDimensionPixelSize(R.dimen.spacing_tiny))
        )
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerviewAdapter =
            ArtWorksAdapter(onReadyToTransition = { startPostponedEnterTransition() },
                onClickListener = ArtWorksAdapter.OnClickListener(
                    shareListener = {
                        artWorkItemHelper.shareArtWork(it)
                        Timber.d("Share click")
                    },
                    favouriteListener = { Timber.d("Favourite click") },
                    pinListener = { Timber.d("Pin click") }
                ))
        with(recyclerView) {
            adapter = recyclerviewAdapter
        }
    }

    private fun setupSearchView() {
        binding.searchView.apply {
            focusAndShowKeyboard()
        }

        var job: Job? = null
        binding.searchView.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_TIME_DELAY)
            }
            viewModel.searchArtWorks(it.toString())
        }
    }
}