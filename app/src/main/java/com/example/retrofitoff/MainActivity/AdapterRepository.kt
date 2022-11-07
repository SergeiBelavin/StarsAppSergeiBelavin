package com.example.retrofitoff

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitoff.databinding.ReposNameListBinding
import com.example.retrofitoff.mode2.RepositoriesUser
import com.example.retrofitoff.mode2.RepositoriesUserItem
import retrofit2.Call
import retrofit2.Response

class AdapterReposNameReView(val listener: Listener): RecyclerView.Adapter<AdapterReposNameReView.StartViewHolder>() {

    var listStart=emptyList<Call<RepositoriesUserItem>>()

    class StartViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = ReposNameListBinding.bind(item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.repos_name_list, parent, false)
        return StartViewHolder(view)
    }

    override fun onBindViewHolder(holder: StartViewHolder, position: Int) {

        holder.binding.name.text = listStart[position].toString()
        holder.itemView.setOnClickListener {
            listener.onClick(listStart[position])
        }

    }

    override fun getItemCount(): Int {
        return listStart.size
    }
    fun setList(list: Call<RepositoriesUserItem>) {
        listStart = listOf(list)
        notifyDataSetChanged()

    }
    interface  Listener{
        fun onClick(list: Call<RepositoriesUserItem>) {

        }
    }
}