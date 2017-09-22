package com.anahoret.flux

class Dispatcher: TokenGenerator<Int> by IntTokenGenerator() {

  private var isDispatching = false
  private val callbacks = mutableListOf<Callback>()
  private var pendingPayload: Any? = null

  fun dispatch(payload: Any) {
    if(isDispatching()) throw IllegalStateException("Dispatcher.dispatch(...): Cannot dispatch in the middle of a dispatch.")
    startDispatching(payload)
    val pl = pendingPayload ?: throw IllegalStateException("Dispatcher.dispatch(...): pendingPayload is undefined.")
    try {
      callbacks.forEach { cb -> if (!cb.isPending) cb(pl) }
    } finally {
      stopDispatching()
    }
  }

  fun register(callback: (Any) -> Unit): Int {
    val token = createToken()
    callbacks.add(
      Callback(
        token = token,
        func = callback
      )
    )
    return token
  }

  fun unregister(token: Int) {
    callbacks.remove(getCallback(token))
  }

  fun waitFor(tokens: Array<Int>) {
    if(!isDispatching()) throw IllegalStateException("Dispatcher.waitFor(...): Must be invoked while dispatching.")
    tokens.forEach { token ->
      val callback = getCallback(token)
      if (callback.isPending) {
        if(callback.isHandled) throw IllegalStateException("Dispatcher.waitFor(...): Circular dependency detected while waiting for `$token`.")
      } else {
        pendingPayload?.let { callback(it) }
      }
    }
  }

  fun isDispatching(): Boolean = isDispatching

  private fun startDispatching(payload: Any) {
    callbacks.forEach { callback ->
      callback.isPending = false
      callback.isHandled = false
    }
    pendingPayload = payload
    isDispatching = true
  }

  private fun stopDispatching() {
    pendingPayload = null
    isDispatching = false
  }

  private fun getCallback(token: Int): Callback {
    val idx = callbacks.binarySearchBy(key = token, selector = { it.token })
    if (idx < 0) throw IllegalArgumentException("Error: `$token` does not map to a registered callback.")
    return callbacks[idx]
  }

  private class Callback(
    val token: Int,
    private val func: (Any) -> Unit,
    var isPending: Boolean = false,
    var isHandled: Boolean = false
  ) {
    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other !is Callback) return false

      if (token != other.token) return false

      return true
    }

    override fun hashCode(): Int {
      return token.hashCode()
    }

    operator fun invoke(payload: Any) {
      isPending = true
      func(payload)
      isHandled = true
    }

  }

}
