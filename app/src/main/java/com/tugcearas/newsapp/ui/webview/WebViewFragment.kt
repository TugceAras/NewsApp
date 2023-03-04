package com.tugcearas.newsapp.ui.webview

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tugcearas.newsapp.R
import com.tugcearas.newsapp.databinding.FragmentWebViewBinding
import com.tugcearas.newsapp.util.extensions.toastMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : Fragment() {

    private lateinit var binding: FragmentWebViewBinding
    private val webViewModel: WebViewVM by viewModels()
    private val args by navArgs<WebViewFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentWebViewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webViewToolbar.appTitle.text = getText(R.string.webViewToolbar)
        webView()
        clickFloatingButton()
        checkFavoriteIcon()
        clickBackButton()
        shareLink()
        initObservers()
    }

    private fun webView(){
        try {
            binding.webView.apply {
                webViewClient = WebViewClient()
                args.article.url?.let {
                    loadUrl(it)
                }
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun checkFavoriteIcon(){
        if (!args.fromFavorites && !args.article.isFav)
            binding.floatingButton.setImageResource(R.drawable.fav_icon_outline)
        else if (args.fromHome)
            binding.floatingButton.setImageResource(R.drawable.fav_icon_fill)
        else
            binding.floatingButton.visibility = View.GONE
    }

    private fun clickFloatingButton(){
        binding.floatingButton.setOnClickListener {
            webViewModel.addFavoriteArticle(args.article)
        }
    }

    private fun initObservers(){
        webViewModel.isAddedFavorite.observe(viewLifecycleOwner){
            if (it){
                binding.floatingButton.setImageResource(R.drawable.fav_icon_fill)
                requireContext().toastMessage(getString(R.string.article_added_message))
            }
            else{
                requireContext().toastMessage(getString(R.string.article_already_saved))
            }
        }
    }

    private fun clickBackButton(){
        binding.webViewToolbar.customToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun shareLink(){
        binding.webViewToolbar.shareIcon.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, binding.webView.url)
                startActivity(Intent.createChooser(this,getString(R.string.share_news_link)))
            }
        }
    }
}