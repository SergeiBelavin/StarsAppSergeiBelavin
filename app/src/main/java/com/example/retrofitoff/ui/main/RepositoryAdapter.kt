package com.example.retrofitoff.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitoff.R
import com.example.retrofitoff.model.RepoUser
import com.example.retrofitoff.databinding.ReposNameListBinding

class RepositoryAdapter(private val listener: Listener): RecyclerView.Adapter<RepositoryAdapter.StartViewHolder>() {

    private var repoList=emptyList<RepoUser>()

    class StartViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = ReposNameListBinding.bind(item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.repos_name_list, parent, false)
        return StartViewHolder(view)
    }

    override fun onBindViewHolder(holder: StartViewHolder, position: Int) {

        holder.binding.name.text = repoList[position].name
        holder.itemView.setOnClickListener {
            listener.onClick(repoList[position])
        }

    }

    override fun getItemCount(): Int {
        return repoList.size
    }
    fun setList(list: List<RepoUser>) {
        repoList = list
        notifyDataSetChanged()

    }
    interface  Listener{
        fun onClick(list: RepoUser)
    }
}