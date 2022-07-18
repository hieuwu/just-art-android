package com.hieuwu.justart.presentation.artworks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hieuwu.justart.databinding.FragmentArtWorksBinding
import com.hieuwu.justart.domain.usecases.RetrieveArtWorksUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtWorksFragment : Fragment() {

    @Inject
    lateinit var retrieveArtWorksUseCase: RetrieveArtWorksUseCase

    private lateinit var binding: FragmentArtWorksBinding

    private lateinit var viewModel: ArtWorksViewModel

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
        binding.testDataButton.setOnClickListener {
            viewModel.retrieveData()
        }
        setObservers()
        setupRecyclerView(binding.artWorksRecyclerView)
    }

    private fun setObservers() {
        viewModel.navigateToSelectedProperty.observe(this.viewLifecycleOwner) {
            it?.let {
                navigateToArtWorksDetail()
                viewModel.displayPropertyDetailsComplete()
            }
        }

        viewModel.artworksNetwork.observe(viewLifecycleOwner) {
            var a = it
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        with(recyclerView) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = ArtWorksAdapter(
                ArtWorksAdapter.OnClickListener(
                    clickListener = { viewModel.displayPropertyDetails(it) }
                )
            )
        }
    }

    private fun navigateToArtWorksDetail() {
        val direction = ArtWorksFragmentDirections.actionArtWorksFragmentToArtDetailsFragment()
        findNavController().navigate(direction)
    }
}