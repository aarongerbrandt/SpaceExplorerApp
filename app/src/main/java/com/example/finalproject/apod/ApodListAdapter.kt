@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused")

package com.example.finalproject.apod

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ListItemApodBinding

const val TAG = "ApodListAdapter"
class ApodListAdapter(
    private val apods:List<Apod>,
    private val activity: Activity,
    private val viewModel: ApodListViewModel
): RecyclerView.Adapter<ApodHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemApodBinding.inflate(inflater, parent, false)
        return ApodHolder(binding, activity, viewModel)
    }

    override fun onBindViewHolder(holder: ApodHolder, position: Int) {
        val response = apods[position]
        holder.bind(response)
    }

    override fun getItemCount() = apods.size

}