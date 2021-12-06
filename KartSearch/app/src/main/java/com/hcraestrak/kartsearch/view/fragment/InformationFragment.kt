package com.hcraestrak.kartsearch.view.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentInformationBinding
import com.hcraestrak.kartsearch.model.viewModel.MatchViewModel
import java.io.File
import java.io.IOException

class InformationFragment : Fragment() {

    private lateinit var binding: FragmentInformationBinding
    private val viewModel: MatchViewModel by viewModels()
    private val args: InformationFragmentArgs by navArgs()
    private lateinit var storageReference: StorageReference

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
        viewModel.getMatchResponseObserver().observe(viewLifecycleOwner, { match ->
            binding.userNickName.text = match.nickName
            getProfileImage("character", match.matches[0].matches[0].character) // 대표 캐릭터 이미지
            when(match.matches[0].matches[0].player.rankinggrade2) { // 라이센스 이미지
                "1" -> getLicenseImage("License", "chobo")
                "2" -> getLicenseImage("License", "Rookie")
                "3" -> getLicenseImage("License", "L3")
                "4" -> getLicenseImage("License", "L2")
                "5" -> getLicenseImage("License", "L1")
                "6" -> getLicenseImage("License", "PRO")
            }
//            getLicenseImage("License", "PRO")

//            storageReference = FirebaseStorage.getInstance().getReference("UnknownCharacter.png")
        })
    }

    private fun getProfileImage(type: String, id: String) {
        storageReference = FirebaseStorage.getInstance().getReference("/$type/$id.png")
        try {
            val localFile: File = File.createTempFile("tempfile", ".png")
            storageReference.getFile(localFile)
                .addOnSuccessListener {
                    val bitmap: Bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    Glide.with(this).load(bitmap).into(binding.userProfileImg)
                }.addOnFailureListener{
                    Toast.makeText(requireContext(), "사진 가져오기에 실패했습니다.", Toast.LENGTH_LONG).show()
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getLicenseImage(type: String, id: String) {
        storageReference = FirebaseStorage.getInstance().getReference("/$type/$id.png")
        try {
            val localFile: File = File.createTempFile("tempfile", ".png")
            storageReference.getFile(localFile)
                .addOnSuccessListener {
                    val bitmap: Bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    Glide.with(this).load(bitmap).into(binding.userLicense)
                }.addOnFailureListener{
                    Toast.makeText(requireContext(), "사진 가져오기에 실패했습니다.", Toast.LENGTH_LONG).show()
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}