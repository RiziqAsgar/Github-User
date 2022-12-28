package com.example.githubusernavigationdanapi.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubusernavigationdanapi.model.User
import com.example.githubusernavigationdanapi.databinding.ItemUserBinding

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {

    private val listUser = ArrayList<User>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }



    @SuppressLint("NotifyDataSetChanged")
    fun setData(user: List<User>){
        listUser.clear()
        listUser.addAll(user)
        notifyDataSetChanged()
        Log.d("TAG", "cek $listUser")

    }
    inner class ListViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        internal fun bind(user: User){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user) }
            binding.tvItemUsername.text = user.login
            Glide.with(itemView.context)
                .load(user.avatar_url)
                .centerCrop()
                .apply(RequestOptions().override(60,60))
                .into(binding.imgItemPhoto)


        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListAdapter.ListViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListAdapter.ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
    override fun getItemCount(): Int = listUser.size

}

