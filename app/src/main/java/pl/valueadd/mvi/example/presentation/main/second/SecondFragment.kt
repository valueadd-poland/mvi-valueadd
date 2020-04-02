package pl.valueadd.mvi.example.presentation.main.second

import android.os.Bundle
import android.view.View
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_second.recyclerView
import pl.valueadd.mvi.example.R
import pl.valueadd.mvi.example.presentation.base.AbstractBaseMviFragment
import pl.valueadd.mvi.presenter.IBaseView
import javax.inject.Inject

class SecondFragment : AbstractBaseMviFragment<SecondView, SecondViewState, IBaseView.IBaseIntent, SecondPresenter>(
    R.layout.fragment_second),
    SecondView {

    companion object {
        fun createInstance(): SecondFragment =
            SecondFragment()
    }

    @Inject
    override lateinit var presenter: SecondPresenter

    private val listAdapter: FastItemAdapter<IItem<*>>
        by lazy { FastItemAdapter<IItem<*>>() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    override fun onDestroyView() {
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

    private fun setupView() {

        recyclerView.adapter = listAdapter
    }
}