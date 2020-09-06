package com.cermati.putu.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cermati.putu.R
import com.cermati.putu.data.models.user.DataUser
import com.cermati.putu.ui.shared.UIConstant
import com.cermati.putu.utils.ImageHelper
import kotlinx.android.synthetic.main.item_user.view.*
import timber.log.Timber

class MainAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var users: MutableList<DataUser?> = mutableListOf()

    object ViewType {
        const val ITEM = 0
        const val LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ViewType.ITEM) {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
            )
        }
        return LoadingViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.progress_loading, parent, false)
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (users[position] == null) {
            ViewType.LOADING
        } else {
            ViewType.ITEM
        }
    }

    fun showLoading() {
        users.add(null)
        notifyItemInserted(users.size - 1)
    }

    fun hideLoading() {
        if (users.size != 0 && users[users.size - 1] == null) {
            users.removeAt(users.size - 1)
            notifyItemRemoved(users.size)
        }
    }

    fun addUsers(users: List<DataUser>) {
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun removeUsers() {
        users.clear();
    }


    override fun getItemCount(): Int = users.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(user: DataUser?) {
            if (user?.avatarURL?.isNotEmpty()!!) {
                ImageHelper.getPicasso(itemView.civUser, user.avatarURL)
            } else {
                ImageHelper.getPicasso(itemView.civUser, UIConstant.DUMMY_IMAGE)
            }

            itemView.tvNameUser.text = user.login
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == ViewType.ITEM) {
            val viewHolder = holder as ViewHolder
            viewHolder.bind(users[position])
        }
    }

}