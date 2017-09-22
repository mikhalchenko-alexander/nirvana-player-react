package com.anahoret.flux

sealed class FluxStore(private val dispatcher: Dispatcher): TokenGenerator<Int> by IntTokenGenerator() {

  private val dispatchToken: Int
  
  private val callbacks = mutableListOf<Callback>()
  protected var changed: Boolean = false

  init {
    dispatchToken = dispatcher.register { payload ->
      invokeOnDispatch(payload)
    }
  }

  fun subscribe(callback: (StoreEvent?) -> Unit): Int {
    val token = createToken()
    callbacks.add(Callback(token, callback))
    return token
  }

  fun unsubscribe(token: Int) {
    callbacks.remove(getCallback(token))
  }

  fun hasChanged(): Boolean {
    if (!dispatcher.isDispatching()) throw IllegalStateException("${this::class.js}.hasChanged(): Must be invoked while dispatching.")
    return changed
  }

  protected abstract fun onDispatch(payload: Any?)

  protected fun emitChange() {
    if (!dispatcher.isDispatching()) throw IllegalStateException("${this::class.js}.emitChange(): Must be invoked while dispatching.")
    this.changed = true
  }

  protected open fun invokeOnDispatch(payload: Any) {
    this.changed = false
    onDispatch(payload)
    if (changed) emitChangeEvent()
  }

  protected fun emitChangeEvent() {
    callbacks.forEach { it(ChangeEvent) }
  }

  private fun getCallback(token: Int): Callback {
    val idx = callbacks.binarySearchBy(key = token, selector = { it.token })
    if (idx < 0) throw IllegalArgumentException("Error: `$token` does not map to a registered callback.")
    return callbacks[idx]
  }

  private class Callback(val token: Int, val func: (StoreEvent) -> Unit) {
    operator fun invoke(storeEvent: StoreEvent) = func(storeEvent)
  }

}

interface StoreEvent
object ChangeEvent: StoreEvent

abstract class FluxReduceStore<T>(dispatcher: Dispatcher): FluxStore(dispatcher) {

  private var state: T? = null

  fun getState(): T {
    val result = state ?: getInitialState()
    if (state == null) {
      state = result
    }
    return result
  }

  protected abstract fun getInitialState(): T

  protected abstract fun reduce(state: T, action: Any): T

  override fun invokeOnDispatch(payload: Any) {
    changed = false
    val startingState = getState()
    val endingState = reduce(startingState, payload)

    if (startingState != endingState) {
      state = endingState
      emitChange()
    }
    if (changed) emitChangeEvent()
  }

  // Method is unused in FluxReduceStore
  override fun onDispatch(payload: Any?) {}

}
