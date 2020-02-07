package pl.valueadd.mvi.fragment.delegate.destroyview

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import pl.valueadd.mvi.fragment.mvi.IBaseView
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A simple implementation of intent which is called when view has been detached.
 * Presenter should handle given intent to save the view state which you don't need to
 * handle each time when it changes.
 *
 * Resolves problem of endless loop with text changes and related workarounds.
 *
 * To implement this create a property (destroyViewIntent) then pass it
 * to the [IBaseView.provideViewIntents].
 *
 * @param provideDestroyIntent method which creates your [OnDestroyViewIntent][IBaseView.IBaseIntent]
 * @param lifecycleOwner delegation need to know when emit [OnDestroyViewIntent][IBaseView.IBaseIntent]
 * so it should be [fragment][androidx.fragment.app.Fragment]
 */
class DestroyViewIntentDelegate<VI : IBaseView.IBaseIntent>(
    lifecycleOwner: LifecycleOwner,
    private val provideDestroyIntent: () -> VI
) : LifecycleObserver,
    ReadOnlyProperty<LifecycleOwner, Subject<VI>> {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    private val destroyViewIntent: Subject<VI>
        by lazy { PublishSubject.create<VI>() }

    override fun getValue(thisRef: LifecycleOwner, property: KProperty<*>): Subject<VI> {
        return destroyViewIntent
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        destroyViewIntent.onNext(provideDestroyIntent())
    }
}