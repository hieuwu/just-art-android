package com.hieuwu.justart.presentation.artworkdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.hieuwu.justart.databinding.FragmentArtworkDetailsBinding
import com.hieuwu.justart.domain.usecases.RetrieveArtWorkDetailsUseCase
import com.hieuwu.justart.presentation.artworks.ArtWorksAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtDetailsFragment : Fragment() {

    @Inject
    lateinit var retrieveArtWorkDetailsUseCase: RetrieveArtWorkDetailsUseCase


    private lateinit var binding: FragmentArtworkDetailsBinding

    private var artWorkId: Int = -1

    private lateinit var viewModel: ArtWorkDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artWorkId = ArtDetailsFragmentArgs.fromBundle(arguments as Bundle).id
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
        viewModel.artWorksDetails.observe(viewLifecycleOwner) {
            binding.artworkDetailLayout.visibility = VISIBLE
        }

        with(binding.detailsRecyclerView) {
            adapter = ArtWorkDetailsAdapter()
        }

        viewModel.displayList.observe(viewLifecycleOwner) {
            var a = it
        }
        return binding.root
    }
}