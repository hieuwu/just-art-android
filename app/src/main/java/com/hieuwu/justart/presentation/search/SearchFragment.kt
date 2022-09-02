package com.hieuwu.justart.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hieuwu.justart.databinding.FragmentSearchBinding
import com.hieuwu.justart.domain.usecases.SearchArtWorkUseCase
import com.hieuwu.justart.presentation.artworks.ArtWorksAdapter
import com.hieuwu.justart.presentation.utils.focusAndShowKeyboard
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

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SearchViewModel
    private var recyclerviewAdapter: ArtWorksAdapter? = null

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
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.clearText.setOnClickListener {
            binding.searchView.text = null
        }

        viewModel.artWorksList.observe(viewLifecycleOwner) {
            val a = it
        }

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner) {
            it?.let {
                navigateToArtWorksDetail(it.id)
                viewModel.displayPropertyDetailsComplete()
            }
        }
        setupSearchView()
        setupRecyclerView(binding.artWorksRecyclerView)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerviewAdapter =
            ArtWorksAdapter(onReadyToTransition = { startPostponedEnterTransition() },
                onClickListener = ArtWorksAdapter.OnClickListener(
                    clickListener = { viewModel.displayPropertyDetails(it) },
                    shareListener = {
//                        shareContent(it)
                        Timber.d("Share click")
                    },
                    favouriteListener = { Timber.d("Favourite click") },
                    pinListener = { Timber.d("Pin click") }
                ))
        with(recyclerView) {
            adapter = recyclerviewAdapter
        }
    }

    private fun navigateToArtWorksDetail(id: Int) {
        val direction =
            SearchFragmentDirections.actionSearchFragmentToArtDetailsFragment(id = id)
        findNavController().navigate(direction)
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
        }
    }
}