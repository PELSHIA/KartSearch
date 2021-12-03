package com.hcraestrak.kartsearch.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentInformationBinding
import com.hcraestrak.kartsearch.model.viewModel.MatchViewModel
import com.hcraestrak.kartsearch.model.viewModel.UserViewModel

class InformationFragment : Fragment() {

    private lateinit var binding: FragmentInformationBinding
    private val viewModel: MatchViewModel by viewModels()
    private val args: InformationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingToolbar()
        searchData()
    }

    private fun bindingToolbar() {
        val activity = activity as AppCompatActivity
        activity.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_informationFragment_to_searchFragment)
        }
    }

    private fun searchData() {
        viewModel.accessIdMatchInquiry(args.accessId)
        viewModel.getMatchResponseObserver().observe(viewLifecycleOwner, {
            binding.userNickName.text = it.nickName
            getUserCharacter("character", it.matches[0].matches[0].character)
        })
    }

    private fun getUserCharacter(type: String, id: String) {
        val storage: FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val uri = storageRef.child("$type/$id.png")
        uri.downloadUrl.addOnSuccessListener {
            Glide.with(this).load(it).into(binding.userProfileImg)
        }
    }

}