package com.anahoret.nirvanaplayer

import com.anahoret.flux.ChangeEvent
import com.anahoret.flux.Dispatcher
import com.anahoret.flux.FluxReduceStore
import org.jetbrains.react.RProps
import org.jetbrains.react.RState

import kotlinx.html.*
import kotlinx.html.js.onClickFunction
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMComponent

object PlayerDispatcher: Dispatcher()

data class PlayerState(val i: Int)
object IncAction
object DecAction
object PlayerStore: FluxReduceStore<PlayerState>(PlayerDispatcher) {

  override fun getInitialState(): PlayerState = PlayerState(0)

  override fun reduce(state: PlayerState, action: Any): PlayerState {
    return when(action) {
      is IncAction -> state.copy(i = state.i + 1)
      is DecAction -> state.copy(i = state.i - 1)
      else -> state
    }
  }
}

class PlayerView: ReactDOMComponent<PlayerView.Props, PlayerView.State>() {
  companion object: ReactComponentSpec<PlayerView, Props, State>

  init {
    runtime.wrappers.require("Style/player.styl")
    state = State()

    PlayerStore.subscribe { event ->
      when (event) {
        is ChangeEvent -> setState {
          clicks = PlayerStore.getState().i
        }
      }
    }

  }

  override fun ReactDOMBuilder.render() {
    div {
      +"It's working!"
      br{}
      +"Clicked ${state.clicks} times"
      br{}
      button {
        +"Inc"
        onClickFunction = { PlayerDispatcher.dispatch(IncAction) }
      }
      button {
        +"Dec"
        onClickFunction = { PlayerDispatcher.dispatch(DecAction) }
      }
    }
  }

  class State(var clicks: Int = 0): RState
  class Props: RProps()

}
