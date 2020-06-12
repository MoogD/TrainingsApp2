package com.example.trainingsapp.app.main

interface MainContract {
    interface View

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
    }
}
