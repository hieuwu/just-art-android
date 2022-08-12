package com.hieuwu.justart.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hieuwu.justart.MainActivity
import com.hieuwu.justart.R
import com.hieuwu.justart.databinding.FragmentSearchBinding
import com.hieuwu.justart.presentation.focusAndShowKeyboard

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

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

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.searchView.apply {
            focusAndShowKeyboard()
        }

        binding.clearText.setOnClickListener {
            binding.searchView.text = null
        }
    }
}