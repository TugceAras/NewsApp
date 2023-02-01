package com.tugcearas.newsapp.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tugcearas.newsapp.R
import com.tugcearas.newsapp.databinding.FragmentSearchNewsBinding
import com.tugcearas.newsapp.ui.adapter.NewsAdapter
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
    lateinit var searchAdapter:NewsAdapter
    private val tagString="Search Fragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(inflater,container,false)
        binding.searchToolbar.appTitle.text = getString(R.string.searchToolbar)
        binding.searchToolbar.customToolbar.navigationIcon = null
        binding.searchToolbar.shareIcon.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        searchViewModel.searchNews.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Success->{
                    response.data?.let {
                        searchAdapter.asyncListDiffer.submitList(it.articles)
                    }
                }
                else->{
                    response.message?.let{message->
                        Log.e(tagString,"Error an occurred! $message")
                    }
                }
            }
        })
    }
}