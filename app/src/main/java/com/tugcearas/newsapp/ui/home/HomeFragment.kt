package com.tugcearas.newsapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tugcearas.newsapp.R
import com.tugcearas.newsapp.databinding.FragmentHomeBinding
import com.tugcearas.newsapp.ui.adapter.NewsAdapter
import com.tugcearas.newsapp.util.extensions.gone
import com.tugcearas.newsapp.util.resource.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeVM by viewModels()
    private lateinit var homeAdapter: NewsAdapter
    private val tagString = "Home Fragment"

      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeToolbar.apply {
            appTitle.text = getString(R.string.homeToolbar)
            shareIcon.gone()
            customToolbar.navigationIcon = null
        }
        homeViewModel.getBreakingNews()
        createRecyclerView()
        observeData()
    }

    private fun createRecyclerView(){
        homeAdapter = NewsAdapter()
        binding.homeRecyclerView.adapter = homeAdapter
        homeAdapter.setOnItemClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToWebViewFragment(it, fromHome = true)
            findNavController().navigate(action)
        }
    }

    private fun observeData(){
        homeViewModel.getNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        homeAdapter.asyncListDiffer.submitList(it)
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