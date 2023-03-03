package com.example.playgroundapp.recyclervew

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.playgroundapp.databinding.ActivityNestedRecyclerviewBinding

/**
 * will test the recyclerview performance inside NestedScrollView
 * */
class NestedRecyclerviewActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }


    private val binding:ActivityNestedRecyclerviewBinding by lazy {
        ActivityNestedRecyclerviewBinding.inflate(layoutInflater)
    }

    private val adapter = RecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerview.adapter = adapter
        adapter.submitList(List(50) { it })

    }

}