package com.example.trainingsapp.app.timer

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CreateTimerTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragmentList: MutableList<Fragment> = mutableListOf()
    private val titleList: MutableList<String> = mutableListOf()

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun addFragment(frag: Fragment, title: String) {
        fragmentList.add(frag)
        titleList.add(title)
    }

    fun getTitle(position: Int): String = titleList[position]
}