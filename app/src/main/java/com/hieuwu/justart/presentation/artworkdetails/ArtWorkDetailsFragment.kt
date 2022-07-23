package com.hieuwu.justart.presentation.artworkdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hieuwu.justart.R
import com.hieuwu.justart.databinding.FragmentArtWorksBinding
import com.hieuwu.justart.databinding.FragmentArtworkDetailsBinding
import com.hieuwu.justart.domain.usecases.RetrieveArtWorkDetailsUseCase
import com.hieuwu.justart.presentation.artworks.ArtWorksViewModel
import com.hieuwu.justart.presentation.artworks.ArtWorksViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtDetailsFragment : Fragment() {

    @Inject
    lateinit var retrieveArtWorkDetailsUseCase: RetrieveArtWorkDetailsUseCase


    private lateinit var binding: FragmentArtworkDetailsBinding

    private lateinit var viewModel: ArtWorkDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtworkDetailsBinding.inflate(inflater, container, false)
        val viewModelFactory = ArtWorkDetailsViewModelFactory(retrieveArtWorkDetailsUseCase)
        viewModel = ViewModelProvider(this, viewModelFactory)[ArtWorkDetailsViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.artWorksDetails?.observe(viewLifecycleOwner){
            val a = it
        }

        return binding.root
    }
}