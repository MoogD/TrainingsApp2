package com.example.trainingsapp.app.timer.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trainingsapp.R
import com.example.trainingsapp.app.base.BaseFragment
import com.example.trainingsapp.app.main.FragmentListener
import com.example.trainingsapp.app.timer.*
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.new_constant_pattern.*
import kotlinx.android.synthetic.main.new_timer_fragment.*
import timber.log.Timber
import java.lang.ClassCastException
import javax.inject.Inject

class NewTimerFragment : BaseFragment(), TimerContract.View {

    private lateinit var adapter: CreateTimerTabAdapter

    @Inject
    lateinit var presenter: TimerContract.Presenter

    private var listener: FragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.new_timer_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CreateTimerTabAdapter(this)
        adapter.addFragment(
            CreateConstantTimerFragment(),
            getString(R.string.constant_pattern_tab_text)
        )
        adapter.addFragment(
            CreateIndividualTimerFragment(),
            getString(R.string.individual_pattern_tab_text)
        )
        viewPager.adapter = adapter
        TabLayoutMediator(newTimerTabLayout, viewPager) { tab, position ->
            tab.text = adapter.getTitle(position)
        }.attach()
        cancelTimerButton.setOnClickListener(::cancelClicked)
        addTimerButton.setOnClickListener(::addTimerClicked)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter.attachView(this)
        if (context is FragmentListener) {
            listener = context
        } else throw ClassCastException("$context must implement FragmentListener!")
    }

    private fun addTimerClicked(view: View) {
        Timber.i("$view clicked")
        when (adapter.getTitle(viewPager.currentItem)) {
            getString(R.string.constant_pattern_tab_text) -> {
                presenter.createConstantTimerPattern(
                    stepsCountEditText.text.toString(),
                    stepsMinutes.editText?.text.toString(),
                    stepsSeconds.editText?.text.toString(),
                    breaksMinutes.editText?.text.toString(),
                    breaksSeconds.editText?.text.toString()
                )
            }
            getString(R.string.individual_pattern_tab_text) -> {
            }
            else -> {
            }
        }
    }
    private fun cancelClicked(view: View) {
        Timber.i("$view clicked")
        listener?.onTimerCreated()
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onTimerCreated() {
        Timber.d("Timer created!")
        listener?.onTimerCreated()
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    override fun showError(msg: String) {
        Timber.d("Error creating a new Timer: $msg")
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewTimerFragment()
    }
}