package com.tugcearas.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tugcearas.newsapp.R
import com.tugcearas.newsapp.data.model.Article

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val callback = object :DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val asyncListDiffer = AsyncListDiffer(this,callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false))
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val list = asyncListDiffer.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(list.urlToImage).into(findViewById(R.id.itemImageView))
            findViewById<TextView>(R.id.itemTitleTextView).text = list.title
            findViewById<TextView>(R.id.itemSourceTextView).text = list.source?.name
            findViewById<TextView>(R.id.itemDescriptionTextView).text = list.description
            findViewById<TextView>(R.id.itemPublishedAt).text = list.publishedAt
            setOnClickListener {
                onItemClickListener?.let {listener->
                    listener(list)
                }
            }
        }
    }
    private var onItemClickListener:((Article) -> Unit)?=null
    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}