package com.example.playgroundapp.design_pattern.architecture.mvp.presenter

import com.example.playgroundapp.design_pattern.architecture.mvp.model.HomeModel
import com.example.playgroundapp.design_pattern.architecture.mvp.view.IHomeView

class HomePresenter(private val view: IHomeView) : IHomePresenter {
    private val model: HomeModel = HomeModel("some-message")

    override fun onCreate() {
    view.updateMessage(model.message)
    }
}