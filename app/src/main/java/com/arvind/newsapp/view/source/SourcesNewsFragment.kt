package com.arvind.newsapp.view.source

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.arvind.newsapp.databinding.FragmentSourcesNewsBinding
import com.arvind.newsapp.view.base.BaseFragment
import com.arvind.newsapp.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SourcesNewsFragment : BaseFragment<FragmentSourcesNewsBinding, NewsViewModel>() {

    override val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSourcesNewsBinding.inflate(inflater, container, false)
}