package com.hieuwu.justart.presentation.onboarding

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import com.hieuwu.justart.MainActivity
import com.hieuwu.justart.databinding.FragmentOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

const val DEFAULT_ADVANCE_TIME = 5000L
const val INITIAL_ADVANCE_TIME = 3000L

@AndroidEntryPoint
class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding

    private val onboardingViewModel: OnboardingViewModel by viewModels()

    private lateinit var onboardAdapter : OnboardingAdapter

    private val handler = Handler(Looper.getMainLooper())

    private val advancePager: Runnable = object : Runnable {
        override fun run() {
            val currentPosition = binding.pager.currentItem
            val size = binding.pager.adapter?.count ?: 0
            if (size != 0) {
                binding.pager.setCurrentItem((currentPosition + 1) % size, true)
            }

            handler.postDelayed(this, DEFAULT_ADVANCE_TIME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        onboardAdapter = OnboardingAdapter(childFragmentManager)
        binding.pager.adapter = onboardAdapter
        binding.viewpagerIndicator.attachTo(binding.pager)

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // If user touches pager then stop auto advance
        binding.pager.setOnTouchListener { v, event ->
            handler.removeCallbacks(advancePager)
            false
        }
        binding.getStarted.setOnClickListener {
            onboardingViewModel.getStartedClicked()
        }

        onboardingViewModel.navigationAction.observe(viewLifecycleOwner) { result ->
            if (result) {
                startActivity(Intent(context, MainActivity::class.java))
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        handler.postDelayed(advancePager, INITIAL_ADVANCE_TIME)
    }

    override fun onDetach() {
        handler.removeCallbacks(advancePager)
        super.onDetach()
    }
}

class OnboardingAdapter(fragment: FragmentManager) : FragmentPagerAdapter(fragment) {

    // Don't show then countdown fragment if the conference has already started
    private val fragments =
        arrayOf(
            OnboardIntroduceFragment(),
            OnboardExploreFragment(),
            OnboardFavoriteFragment()
        )

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]

}
