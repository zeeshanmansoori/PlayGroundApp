package com.example.playgroundapp.design_pattern.architecture.mvp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playgroundapp.databinding.ActivityMvcactivityBinding
import com.example.playgroundapp.design_pattern.architecture.mvp.presenter.HomePresenter
import com.example.playgroundapp.design_pattern.architecture.mvp.view.IHomeView
import java.util.*

/**
 * Model : Holds the business logic and state, it is same for every Arch's.
 * View : It is an Interface to reduce the coupling
 * Presenter : provides the communications
 * MVP : It is more testable as it doesn't directly depends upon the Android APIs
 * **/
class MVPActivity : AppCompatActivity(),IHomeView {

    private val binding: ActivityMvcactivityBinding by lazy {
        ActivityMvcactivityBinding.inflate(layoutInflater)
    }
    private val presenter = HomePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        presenter.onCreate()

    }


    override fun updateMessage(message: String) {
        binding.textView.text = message
    }
}