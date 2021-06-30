package com.arvind.newsapp.view.news

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arvind.newsapp.R
import com.arvind.newsapp.adapter.NewsAdapter
import com.arvind.newsapp.databinding.FragmentNewsBinding
import com.arvind.newsapp.utils.Resource
import com.arvind.newsapp.view.base.BaseFragment
import com.arvind.newsapp.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding, NewsViewModel>() {
    override val viewModel: NewsViewModel by viewModels()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initsview()

    }

    private fun initsview() = with(binding) {
        newsAdapter = NewsAdapter()
        rvNews.apply {
            setHasFixedSize(true)
            adapter = newsAdapter
            setupobserver()
        }
    }

    private fun setupobserver() = with(binding) {
        viewModel.newsData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    progressBarStatus(false)
                    tryAgainStatus(false)
                    newsAdapter.differ.submitList(response.data!!.articles)
                    rvNews.adapter = newsAdapter
                }
                is Resource.Error -> {
                    tryAgainStatus(true, response.message!!)
                    progressBarStatus(false)
                }
                is Resource.Loading -> {
                    tryAgainStatus(false)
                    progressBarStatus(true)
                }
            }
        })
    }

    private fun tryAgainStatus(status: Boolean, message: String = "message") {
        if (status) {
            tryAgainMessage.text = message
            tryAgainLayout.visibility = View.VISIBLE
        } else {
            tryAgainLayout.visibility = View.GONE
        }
    }


    private fun progressBarStatus(status: Boolean) {
        progressBar_news.visibility = if (status) VISIBLE else GONE

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        lifecycleScope.launchWhenStarted {
            val isChecked = viewModel.getUIMode.first()
            val uiMode = menu.findItem(R.id.action_night_mode)
            uiMode.isChecked = isChecked
            setUIMode(uiMode, isChecked)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        return when (item.itemId) {
            R.id.action_night_mode -> {
                item.isChecked = !item.isChecked
                setUIMode(item, item.isChecked)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUIMode(item: MenuItem, isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            viewModel.saveToDataStore(true)
            item.setIcon(R.drawable.ic_night)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            viewModel.saveToDataStore(false)
            item.setIcon(R.drawable.ic_day)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNewsBinding.inflate(inflater, container, false)

}