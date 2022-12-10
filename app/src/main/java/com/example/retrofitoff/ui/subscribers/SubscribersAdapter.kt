package com.example.retrofitoff.ui.subscribers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitoff.R
import com.example.retrofitoff.model.StarGroup
import com.example.retrofitoff.databinding.SubscribersAdapterBinding
import com.squareup.picasso.Picasso

class SubscribersAdapter(): RecyclerView.Adapter<SubscribersAdapter.UserAvatar>() {
    private var userList= emptyList<StarGroup>()
    class UserAvatar(item: View): RecyclerView.ViewHolder(item) {
        val binding = SubscribersAdapterBinding.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAvatar {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.subscribers_adapter,parent,false)
        return UserAvatar(view)
    }

    override fun onBindViewHolder(holder: UserAvatar, position: Int) {
        holder.binding.nameUser.text = userList[position].user.name
        Picasso.get()
            .load(userList[position].user.avatar)
            .error(R.drawable.shrek)
            .into(holder.binding.avatarUser)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    fun setList(list: List<StarGroup>) {
        userList = list
        notifyDataSetChanged()
    }
}