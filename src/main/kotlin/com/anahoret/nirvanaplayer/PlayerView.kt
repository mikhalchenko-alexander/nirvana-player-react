package com.anahoret.nirvanaplayer

import react.RProps
import react.RState

import kotlinx.html.*
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
    }
  }


  class State: RState
  class Props: RProps()

}
