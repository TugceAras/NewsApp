package com.tugcearas.newsapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tugcearas.newsapp.R
import com.tugcearas.newsapp.databinding.FragmentFavoriteNewsBinding
import com.tugcearas.newsapp.ui.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteNewsFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteNewsBinding
    private val favViewModel: FavoriteNewsVM by viewModels()
    lateinit var favAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteNewsBinding.inflate(inflater,container,false)
        binding.favToolbar.appTitle.text = getString(R.string.favToolbar)
        binding.favToolbar.shareIcon.visibility = View.GONE
        binding.favToolbar.customToolbar.navigationIcon = null
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRecyclerView()
        observeData()
        deleteProcess()
    }

    private fun createRecyclerView(){
        favAdapter = NewsAdapter()
        binding.favRecyclerView.adapter = favAdapter
        favAdapter.setOnItemClickListener {
           val action =
               FavoriteNewsFragmentDirections.actionFavoriteNewsFragmentToWebViewFragment(it, true)
            findNavController().navigate(action)
        }
    }

    private fun observeData(){
        favViewModel.getFavArticles().observe(viewLifecycleOwner) {
            favAdapter.asyncListDiffer.submitList(it)
        }
    }

    private fun deleteProcess() {
        val itemTouchHelper= object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = favAdapter.asyncListDiffer.currentList[position]
                favViewModel.deleteArticle(article)
                Snackbar.make(requireView(), "Successfully deleted!", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        favViewModel.addArticle(article)
                    }.show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(binding.favRecyclerView)
        }
    }
}