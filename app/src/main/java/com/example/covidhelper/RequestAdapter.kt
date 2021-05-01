package com.example.covidhelper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.covidhelper.Models.Request
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RequestAdapter(options: FirestoreRecyclerOptions<Request>) : FirestoreRecyclerAdapter<Request, RequestAdapter.RequestViewHolder>(
    options) {

    class RequestViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val desc: TextView = itemView.findViewById(R.id.description)
        val userName: TextView = itemView.findViewById(R.id.userName)
        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
        val userImage: ImageView = itemView.findViewById(R.id.userImage)
        val phone: TextView = itemView.findViewById(R.id.phoneNo)
        val city: TextView = itemView.findViewById(R.id.city)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val viewHolder =  RequestViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.request_post, parent, false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int, model: Request) {
        holder.userName.text = model.name
        holder.desc.text = model.description
        holder.phone.text = "+91 " + model.phone
        holder.city.text = model.address
        holder.userName.text = model.name
        Glide.with(holder.userImage.context).load(model.createdBy.imageId).circleCrop().into(holder.userImage)
        holder.createdAt.text = Utils.getTimeAgo(model.createdAt)
    }
}