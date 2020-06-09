package com.example.trainingsapp.app.main

class MainPresenterImp : MainContract.Presenter {
    private var view: MainContract.View? = null

    override fun attachView(view: MainContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}
