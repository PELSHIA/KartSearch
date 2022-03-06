package com.hcraestrak.kartsearch.view.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentInformationBinding
import com.hcraestrak.kartsearch.model.db.entity.Bookmark
import com.hcraestrak.kartsearch.viewModel.MatchViewModel
import com.hcraestrak.kartsearch.view.adapter.InformationVIewPagerAdapter
import com.hcraestrak.kartsearch.viewModel.BookmarkViewModel
import com.hcraestrak.kartsearch.viewModel.InformationViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class InformationFragment : Fragment() {

    private lateinit var binding: FragmentInformationBinding
    private val viewModel: MatchViewModel by activityViewModels()
    private val bookMark: BookmarkViewModel by viewModels()
    private val scroll: InformationViewModel by activityViewModels()
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
        setTabLayout()
        scroll()
        bookmark()
    }

    private fun bindingToolbar() {
        val activity = activity as AppCompatActivity
        activity.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun searchData() {
        viewModel.accessIdMatchInquiry(args.accessId, "")
        viewModel.matchResponse.observe(viewLifecycleOwner, { match ->
            binding.toolbarTitle.text = match.nickName + " 님의 전적"
            binding.userNickName.text = match.nickName
            getImage("character", match.matches[0].matches[0].character, binding.userProfileImg) // 대표 캐릭터 이미지
            getLicenseImage(match.matches[0].matches[0].player.rankinggrade2) // 라이센스 이미지
        })
    }

    private fun getImage(type: String, id: String, view: ImageView) {
        storageReference = FirebaseStorage.getInstance().getReference("/$type/$id.png")
        try {
            val localFile: File = File.createTempFile("tempfile", ".png")
            storageReference.getFile(localFile)
                .addOnSuccessListener {
                    val bitmap: Bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    Glide.with(view.context).load(bitmap).into(view)
                }.addOnFailureListener{
                    if (type == "character") {
                        Glide.with(view.context).load(R.drawable.unknowncharacter).into(view)
                    }
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getLicenseImage(license: String) { // 라이센스 판별
        when(license) {
            "1" -> getImage("License", "chobo", binding.userLicense)
            "2" -> getImage("License", "Rookie", binding.userLicense)
            "3" -> getImage("License", "L3", binding.userLicense)
            "4" -> getImage("License", "L2", binding.userLicense)
            "5" -> getImage("License", "L1", binding.userLicense)
            "6" -> getImage("License", "PRO", binding.userLicense)
        }
    }

    private fun setTabLayout() {
        val tabLayoutTextList = listOf<String>("전적", "통계", "정보")
        binding.viewPager.adapter = InformationVIewPagerAdapter(requireActivity(), args.accessId)
        TabLayoutMediator(binding.tabLayout, binding.viewPager){tab, position ->
            tab.text = tabLayoutTextList[position]
        }.attach()
    }

    private fun scroll() {
        binding.scrollView.setOnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == binding.scrollView.getChildAt(0).measuredHeight - v.measuredHeight) {
                scroll.isScroll.value = true
            }
        }
    }

    private fun bookmark() {
        binding.userBookmark.setOnClickListener {
            viewModel.matchResponse.observe(viewLifecycleOwner, { match ->
                bookMark.isExists(match.nickName)
                bookMark.isExists.observe(viewLifecycleOwner, {
                    if (it) {
                        bookMark.deleteNickName(Bookmark(match.nickName))
                        Glide.with(binding.userBookmark.context).load(R.drawable.ic_star_border).into(binding.userBookmark)
                    } else {
                        bookMark.insertNickName(Bookmark(match.nickName))
                        Glide.with(binding.userBookmark.context).load(R.drawable.ic_star).into(binding.userBookmark)
                    }
                })
            })
        }
    }

}