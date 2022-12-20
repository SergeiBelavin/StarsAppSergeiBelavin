package com.example.retrofitoff.data.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitoff.R
import com.example.retrofitoff.model.RepoUser
import com.example.retrofitoff.databinding.ReposNameListBinding

class RepoAdapter(private val listener: Listener): RecyclerView.Adapter<RepoAdapter.StartViewHolder>() {

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
            listener.onClickAdapter(repoList[position])
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
        fun onClickAdapter(list: RepoUser)
    }
}