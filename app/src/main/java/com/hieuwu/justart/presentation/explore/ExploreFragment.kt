package com.hieuwu.justart.presentation.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hieuwu.justart.databinding.FragmentExploreBinding
import com.hieuwu.justart.domain.usecases.RetrieveExhibitionsUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment : Fragment() {

    @Inject
    lateinit var retrieveExhibitionsUseCase: RetrieveExhibitionsUseCase

    private lateinit var binding: FragmentExploreBinding
    private var adapter: ExhibitionAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        val viewModelFactory = ExploreViewModelFactory(
            retrieveExhibitionsUseCase = retrieveExhibitionsUseCase,
        )

        val viewModel = ViewModelProvider(this, viewModelFactory)[ExploreViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        adapter = ExhibitionAdapter(ExhibitionAdapter.OnClickListener {})
        binding.exhibitionsList.adapter = adapter
        viewModel.exhibitions.observe(viewLifecycleOwner) {}
        return binding.root
    }

}