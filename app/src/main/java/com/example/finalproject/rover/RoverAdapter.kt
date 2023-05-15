package com.example.finalproject.rover

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ListItemRoverBinding

const val TAG = "RoverListAdapter"
class RoverListAdapter(
    private val rovers: List<Rover>,
    private val activity: Activity
): RecyclerView.Adapter<RoverHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoverHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemRoverBinding.inflate(inflater, parent, false)
        return RoverHolder(binding, activity)
    }

    override fun getItemCount() = rovers.size

    override fun onBindViewHolder(holder: RoverHolder, position: Int) {
        val response = rovers[position]
        holder.bind(response)
    }
}