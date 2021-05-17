package pl.valueadd.mvi.example.presentation.main.second

import android.os.Bundle
import android.view.View
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import io.reactivex.Observable
import pl.valueadd.mvi.example.databinding.FragmentSecondBinding
import pl.valueadd.mvi.example.presentation.base.AbstractBaseMviFragment
import pl.valueadd.mvi.fragment.base.FragmentBindingInflater
import pl.valueadd.mvi.presenter.IBaseView
import javax.inject.Inject

class SecondFragment : AbstractBaseMviFragment<SecondView, SecondViewState, IBaseView.IBaseIntent, IBaseView.IBaseEffect, SecondPresenter, FragmentSecondBinding>(),
    SecondView {

    @Inject
    override lateinit var presenter: SecondPresenter

    override val bindingInflater: FragmentBindingInflater<FragmentSecondBinding>
        get() = FragmentSecondBinding::inflate

    private val listAdapter: FastItemAdapter<IItem<*>>
        by lazy { FastItemAdapter<IItem<*>>() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    override fun onDestroyView(): Unit = with(requireBinding) {
        super.onDestroyView()
        recyclerView.adapter = null
    }

    override fun render(state: SecondViewState) {
        bindDataToList(state.list)
    }

    override fun provideViewIntents(): List<Observable<IBaseView.IBaseIntent>> = listOf()

    override fun provideInitialViewState(): SecondViewState {
        return restoredViewState ?: SecondViewState()
    }

    private fun bindDataToList(list: List<IItem<*>>) {
        listAdapter.setNewList(list)
    }

    private fun setupView(): Unit = with(requireBinding) {
        recyclerView.adapter = listAdapter
    }
}
