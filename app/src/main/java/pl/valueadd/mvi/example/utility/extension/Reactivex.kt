package pl.valueadd.mvi.example.utility.extension

import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

/**
 * Throttle clicks on view with given duration.
 */
fun View.throttleClicks(interval: Long = 500L): Observable<Unit> =
    this.clicks()
        .throttleFirst(interval, TimeUnit.MILLISECONDS)

/**
 * Throttle text changes on view with given duration.
 */
fun View.throttleTextChanges(interval: Long = 50L): Observable<Unit> =
    this.clicks()
        .throttleFirst(interval, TimeUnit.MILLISECONDS)

/**
 * Calls a given lambda on thread from which caller method has been invoked.
 */
fun immediate(action: () -> Unit): Completable =
    Completable
        .fromAction { action() }

/**
 * Calls a given lambda on thread from which caller method has been invoked.
 */
fun <T> immediateSingle(action: () -> T): Single<T> =
    Single.fromCallable<T> { action() }

/**
 * Immediately subscribes stream and adds to disposables.
 *
 * Errors are silenced by default.
 */
fun <T> Observable<T>.onSuccess(
    composite: CompositeDisposable,
    action: (T) -> Unit,
    onError: (Throwable) -> Unit = { /* Muted */ }
): Disposable =
    this.subscribe(action, onError)
        .addTo(composite)

/**
 * Immediately subscribes stream and adds to disposables.
 *
 * Errors are silenced by default.
 */
fun <T> Flowable<T>.onSuccess(
    composite: CompositeDisposable,
    action: (T) -> Unit,
    onError: (Throwable) -> Unit = { /* Muted */ }
): Disposable =
    this.subscribe(action, onError)
        .addTo(composite)

/**
 * Immediately subscribes stream and adds to disposables.
 *
 * Errors are silenced by default.
 */
fun Completable.onSuccess(
    composite: CompositeDisposable,
    action: () -> Unit,
    onError: (Throwable) -> Unit = { /* Muted */ }
): Disposable =
    this.subscribe(action, onError)
        .addTo(composite)

/**
 * Immediately subscribes stream and adds to disposables.
 *
 * Errors are silenced by default.
 */
fun <T> Maybe<T>.onSuccess(
    composite: CompositeDisposable,
    action: (T) -> Unit,
    onError: (Throwable) -> Unit = { /* Muted */ }
): Disposable =
    this.subscribe(action, onError)
        .addTo(composite)