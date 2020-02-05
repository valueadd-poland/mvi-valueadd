package pl.valueadd.mvi.fragment.mvi.delegation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import pl.valueadd.mvi.fragment.base.BaseFragment
import pl.valueadd.mvi.fragment.mvi.BaseMviPresenter
import pl.valueadd.mvi.fragment.mvi.IBaseMviFragment
import pl.valueadd.mvi.fragment.mvi.IBaseView
import java.util.UUID
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class MviFragmentDelegateImpl<V : IBaseView<*, *>, P : BaseMviPresenter<*, *, *, V>>(
    fragment: Fragment
) : MviFragmentDelegate, ReadOnlyProperty<Fragment, P> {

    lateinit var presenter: P

    init {
        fragment.lifecycle.addObserver(this)
    }

    override val viewStateKey: String =
        "ARG_STATE_VIEW_${UUID.randomUUID()}"

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(viewStateKey, presenter.currentState)
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): P {
        return presenter
    }

    @Suppress("UNCHECKED_CAST")
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onStart() {
        presenter.attachView(this as V)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onStop() {
        presenter.detachView()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        presenter.destroy()
    }
}