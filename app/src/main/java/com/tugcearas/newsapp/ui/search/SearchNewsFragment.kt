package com.tugcearas.newsapp.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tugcearas.newsapp.R
import com.tugcearas.newsapp.databinding.FragmentSearchNewsBinding
import com.tugcearas.newsapp.ui.adapter.NewsAdapter
import com.tugcearas.newsapp.util.extensions.gone
import com.tugcearas.newsapp.util.resource.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNewsFragment : Fragment() {

    private lateinit var binding: FragmentSearchNewsBinding
    private val searchViewModel: SearchNewsVM by viewModels()
    private lateinit var searchAdapter:NewsAdapter
    private val tagString = "Search Fragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchToolbar.apply {
            appTitle.text = getString(R.string.searchToolbar)
            shareIcon.gone()
            customToolbar.navigationIcon = null
        }
        createRecyclerView()
        searchQuery()
        observeData()
    }

    private fun createRecyclerView(){
        searchAdapter = NewsAdapter()
        binding.searchRecyclerView.adapter = searchAdapter
        searchAdapter.setOnItemClickListener {
            val action = SearchNewsFragmentDirections.actionSearchNewsFragmentToWebViewFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun searchQuery(){
        var job:Job?=null
        binding.searchEditText.addTextChangedListener {editable->
            // cancel the previous background task before starting a new one
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable.let {
                    if (editable.toString().isNotEmpty()){
                        searchViewModel.getNewsEverything(editable.toString())
                    }
                }
            }
        }
    }

    private fun observeData(){
        searchViewModel.searchNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        searchAdapter.asyncListDiffer.submitList(it.articles)
                    }
                }
                else -> {
                    response.message?.let { message ->
                        Log.e(tagString, getString(R.string.error_occured)+message)
                    }
                }
            }
        }
    }
}