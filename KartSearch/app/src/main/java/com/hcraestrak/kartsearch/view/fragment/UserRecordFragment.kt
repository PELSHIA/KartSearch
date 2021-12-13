package com.hcraestrak.kartsearch.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentUserRecordBinding

class UserRecordFragment(val id: String) : Fragment() {

    private lateinit var binding: FragmentUserRecordBinding
    private var isClicked: Boolean = true // true 개인전 false 팀전

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingStateButton()
        bindingSpinner()
        bindingTitle()
    }

    private fun bindingStateButton() {
        binding.userInfoStateLayout.setOnClickListener {
            isClicked = if (isClicked) {
                stateSingle(resources.getColor(R.color.light_blue), R.drawable.background_on)
                stateTeam(resources.getColor(R.color.gray), 0)
                bindingSpinner()
                bindingTitle()
                false
            } else {
                stateSingle(resources.getColor(R.color.gray), 0)
                stateTeam(resources.getColor(R.color.light_blue), R.drawable.background_on)
                bindingSpinner()
                bindingTitle()
                true
            }
        }
    }

    private fun stateTeam(color: Int, backGround: Int) {
        binding.userInfoStateTeam.setTextColor(color)
        binding.userInfoStateTeam.setBackgroundResource(backGround)
    }

    private fun stateSingle(color: Int, backGround: Int) {
        binding.userInfoStateSingle.setTextColor(color)
        binding.userInfoStateSingle.setBackgroundResource(backGround)
    }

    private fun bindingSpinner() {
        if (isClicked) {
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.single_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                binding.userRecordSpinner.adapter = adapter
            }
        } else {
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.team_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                binding.userRecordSpinner.adapter = adapter
            }
        }

        binding.userRecordSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                bindingTitle()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun bindingTitle() {
        if (isClicked) {
            binding.userRecordTitle.text = binding.userRecordSpinner.selectedItem.toString() + " 팀전 전적"
        } else {
            binding.userRecordTitle.text = binding.userRecordSpinner.selectedItem.toString() + " 개인전 전적"
        }
    }

}