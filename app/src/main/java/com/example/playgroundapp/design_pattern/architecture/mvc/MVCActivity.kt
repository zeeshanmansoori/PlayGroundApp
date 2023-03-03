package com.example.playgroundapp.design_pattern.architecture.mvc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playgroundapp.databinding.ActivityMvcactivityBinding
import java.util.*

/**
 * Model : Holds the business logic and state, it is same for every Arch's.
 * It is optional but we can make it observables so to provide two way data binding.
 *
 * View : Android Views
 * Controller (Activity/Fragment) : Manipulates views and Model
 * MVC : It is less testable as it does directly depends upon the Android APIs
 * **/
class MVCActivity : AppCompatActivity(), Observer {

    private val binding: ActivityMvcactivityBinding by lazy {
        ActivityMvcactivityBinding.inflate(layoutInflater)
    }
    private val model by lazy {
        MVCModel().apply {
            addObserver(this@MVCActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            model.setValueAtIndex(10)
        }

    }

    override fun update(o: Observable?, arg: Any?) {
        binding.textView.text = model.getValueAtIndex(0).toString()
    }
}