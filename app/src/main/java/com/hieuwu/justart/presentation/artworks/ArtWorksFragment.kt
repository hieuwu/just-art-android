package com.hieuwu.justart.presentation.artworks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hieuwu.justart.R
import com.hieuwu.justart.databinding.FragmentArtWorksBinding

class ArtWorksFragment : Fragment() {

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

        viewModel = ViewModelProvider(this)[ArtWorksViewModel::class.java]

        binding.viewModel = viewModel

        setObserver()

        binding.artWorksRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.artWorksRecyclerView.adapter = ArtWorksAdapter(
            ArtWorksAdapter.OnClickListener(
                clickListener = {viewModel.displayPropertyDetails(it)}
            )
        )
    }

    private fun setObserver() {
        viewModel.navigateToSelectedProperty.observe(this.viewLifecycleOwner) {
            it?.let {
                navigateToArtWorksDetail()
                viewModel.displayPropertyDetailsComplete()
            }
        }
    }

    private fun navigateToArtWorksDetail() {
        val direction = ArtWorksFragmentDirections.actionArtWorksFragmentToArtDetailsFragment()
        findNavController().navigate(direction)
    }
}