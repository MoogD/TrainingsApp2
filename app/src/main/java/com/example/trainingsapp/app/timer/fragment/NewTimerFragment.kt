package com.example.trainingsapp.app.timer.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trainingsapp.R
import com.example.trainingsapp.app.base.BaseFragment
import com.example.trainingsapp.app.main.FragmentListener
import com.example.trainingsapp.app.timer.CustomNumberPicker
import com.example.trainingsapp.app.timer.TimerContract
import com.example.trainingsapp.app.timer.TimerTabAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.new_constant_pattern.breakLayout
import kotlinx.android.synthetic.main.new_constant_pattern.stepPicker
import kotlinx.android.synthetic.main.new_constant_pattern.timerLayout
import kotlinx.android.synthetic.main.new_timer_fragment.addTimerButton
import kotlinx.android.synthetic.main.new_timer_fragment.cancelTimerButton
import kotlinx.android.synthetic.main.new_timer_fragment.newTimerTabLayout
import kotlinx.android.synthetic.main.new_timer_fragment.viewPager
import timber.log.Timber
import javax.inject.Inject

class NewTimerFragment : BaseFragment(), TimerContract.View, NewTimerListener {

    @Inject
    lateinit var presenter: TimerContract.Presenter

    private var adapter: TimerTabAdapter? = null
    private var listener: FragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.new_timer_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TimerTabAdapter(this)
        adapter?.addFragment(
            CreateConstantTimerFragment(),
            getString(R.string.constant_pattern_tab_text)
        )
        adapter?.addFragment(
            CreateIndividualTimerFragment(),
            getString(R.string.individual_pattern_tab_text)
        )
        viewPager.adapter = adapter
        TabLayoutMediator(newTimerTabLayout, viewPager) { tab, position ->
            tab.text = adapter?.getTitle(position)
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
        var currentFrag = adapter?.getFragment(viewPager.currentItem)
        when (currentFrag) {
            is CreateConstantTimerFragment -> {
                presenter.createConstantTimerPattern(
                    stepPicker.value,
                    timerLayout
                        .findViewById<CustomNumberPicker>(R.id.numpickerMinutes).value,
                    timerLayout
                        .findViewById<CustomNumberPicker>(R.id.numpickerSeconds).value,
                    breakLayout
                        .findViewById<CustomNumberPicker>(R.id.numpickerMinutes).value,
                    breakLayout
                        .findViewById<CustomNumberPicker>(R.id.numpickerSeconds).value
                )
            }
            is CreateIndividualTimerFragment -> {
                val stepList = currentFrag.getTimerList()
                presenter.createIndividualTimerPattern(stepList)
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
        adapter = null
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

    override fun updateIndividualTimer(minutes: Int, seconds: Int): Int =
        presenter.updateIndividualTimer(minutes, seconds)

    companion object {
        @JvmStatic
        fun newInstance() = NewTimerFragment()
    }
}
