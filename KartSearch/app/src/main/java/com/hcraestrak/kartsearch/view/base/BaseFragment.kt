package com.hcraestrak.kartsearch.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.BR
import com.hcraestrak.kartsearch.viewModel.ModeViewModel

abstract class BaseFragment<VB: ViewDataBinding, VM: ViewModel>(@LayoutRes val layoutRes: Int): Fragment() {

    protected abstract val viewModel: VM
    protected lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}
