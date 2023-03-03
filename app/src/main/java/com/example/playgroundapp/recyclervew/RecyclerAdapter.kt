package com.example.playgroundapp.recyclervew

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.playgroundapp.databinding.RcvItemBinding

class RecyclerAdapter : ListAdapter<Int, RecyclerAdapter.RecyclerViewHolder>(diffUtil) {
    private var count = 0
    var startTime = 0L
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        if(count ==0){
            startTime = System.currentTimeMillis()
        }
        count++

        if(count == itemCount){
            Log.d("zeeshan", "onCreate:count $itemCount ms ${System.currentTimeMillis() - startTime}")
        }
        val binding = RcvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

    }
}