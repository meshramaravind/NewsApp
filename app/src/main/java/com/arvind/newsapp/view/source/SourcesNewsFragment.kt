package com.arvind.newsapp.view.source

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.arvind.newsapp.adapter.NewsAdapter
import com.arvind.newsapp.adapter.SourceNewsAdapter
import com.arvind.newsapp.databinding.FragmentSourcesNewsBinding
import com.arvind.newsapp.utils.Resource
import com.arvind.newsapp.view.base.BaseFragment
import com.arvind.newsapp.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_sources_news.*

@AndroidEntryPoint
class SourcesNewsFragment : BaseFragment<FragmentSourcesNewsBinding, NewsViewModel>() {

    override val viewModel: NewsViewModel by viewModels()
    lateinit var sourceNewsAdapter: SourceNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initsview()
    }

    private fun initsview() = with(binding) {
        viewModel.getSourcesNews()

        sourceNewsAdapter = SourceNewsAdapter()
        rvSourcenews.apply {
            setHasFixedSize(true)
            adapter = sourceNewsAdapter
            setupobserver()
        }
    }

    private fun setupobserver() {
        viewModel.newsSourcesData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    progressBarStatus(false)
                    sourceNewsAdapter.differ.submitList(response.data!!.sources)
                    rv_sourcenews.adapter = sourceNewsAdapter
                }
                is Resource.Error -> {
                    progressBarStatus(false)
                }
                is Resource.Loading -> {
                    progressBarStatus(true)
                }
            }
        })
    }

    private fun progressBarStatus(status: Boolean) {
        progressbar_sources.visibility = if (status) View.VISIBLE else View.GONE

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSourcesNewsBinding.inflate(inflater, container, false)
}