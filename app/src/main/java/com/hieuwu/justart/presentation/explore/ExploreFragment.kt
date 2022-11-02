package com.hieuwu.justart.presentation.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hieuwu.justart.databinding.FragmentExploreBinding
import com.hieuwu.justart.domain.models.EventDo
import com.hieuwu.justart.domain.usecases.GetEventsUseCase
import com.hieuwu.justart.domain.usecases.RetrieveExhibitionsUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment : Fragment() {

    @Inject
    lateinit var retrieveExhibitionsUseCase: RetrieveExhibitionsUseCase

    @Inject
    lateinit var getEventsUseCase: GetEventsUseCase

    private lateinit var binding: FragmentExploreBinding
    private val exhibitionAdapter = ExhibitionAdapter(ExhibitionAdapter.OnClickListener {})
    private val eventAdapter = EventAdapter(EventAdapter.OnClickListener {})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        val viewModelFactory = ExploreViewModelFactory(
            retrieveExhibitionsUseCase = retrieveExhibitionsUseCase,
            getEventsUseCase = getEventsUseCase
        )

        val viewModel = ViewModelProvider(this, viewModelFactory)[ExploreViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.exhibitionsList.adapter = exhibitionAdapter
        binding.eventsList.adapter = eventAdapter
        viewModel.exhibitions.observe(viewLifecycleOwner) {}
        viewModel.events.observe(viewLifecycleOwner) {}
        return binding.root
    }

}