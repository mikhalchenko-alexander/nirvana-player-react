package com.anahoret.nirvanaplayer

import react.RProps
import react.RState

import kotlinx.html.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.ReactComponentSpec
import react.dom.ReactDOMBuilder
import react.dom.ReactDOMComponent

class PlayerView: ReactDOMComponent<PlayerView.Props, PlayerView.State>() {
  companion object: ReactComponentSpec<PlayerView, Props, State>

  init {
    state = State()
  }

  override fun ReactDOMBuilder.render() {
    div {
      +"It's working!"
      br{}
      +"Clicked ${state.clicks} times"
      br{}
      button {
        +"Click me"
        onClickFunction = this@PlayerView::incrementClicks
      }
    }
  }

  private fun incrementClicks(event: Event) {
    setState {
      clicks = state.clicks + 1
    }
  }

  class State(var clicks: Int = 0): RState
  class Props: RProps()

}
