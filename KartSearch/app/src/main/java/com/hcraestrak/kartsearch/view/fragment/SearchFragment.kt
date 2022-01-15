package com.hcraestrak.kartsearch.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hcraestrak.kartsearch.databinding.FragmentSearchBinding
import com.hcraestrak.kartsearch.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.getAccessId(binding.searchEditText.text.toString())
                viewModel.getObserver().observe(viewLifecycleOwner, {
                    if (it == null) {
                        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToErrorFragment())
                    } else {
                        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToInformationFragment(it.accessId))
                    }
                })
            }
            false
        }
    }
}