package com.hcraestrak.kartsearch.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentSpecificBinding

class SpecificFragment : Fragment() {

    private lateinit var binding: FragmentSpecificBinding
    private val args: SpecificFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpecificBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView()
    }

    private fun bindingView() {
        if (args.isWin == 0) {
            bindingLose()
        } else {
            bindingWin()
        }
    }

    private fun bindingWin() {
        binding.specificOutcome.text = "승리"
        binding.outcomeColor.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_blue))
    }

    private fun bindingLose() {
        binding.specificOutcome.text = "패배"
        binding.outcomeColor.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))

    }

}