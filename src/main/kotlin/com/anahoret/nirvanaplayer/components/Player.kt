package com.anahoret.nirvanaplayer.components

import com.anahoret.flux.ChangeEvent
import com.anahoret.nirvanaplayer.stores.PlayerStore
import org.jetbrains.react.RProps
import org.jetbrains.react.RState

import kotlinx.html.*
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMComponent

class Player: ReactDOMComponent<Player.Props, Player.State>() {
  companion object: ReactComponentSpec<Player, Props, State>

  init {
    runtime.wrappers.require("Style/player.styl")
    state = State()

    PlayerStore.subscribe { event ->
      when (event) {
        is ChangeEvent -> setState {
          tracks = PlayerStore.getState().tracks
        }
      }
    }

  }

  override fun ReactDOMBuilder.render() {
    div("player-view") {
      div("left-panel") {
        PlayerControls {}
        MediaLibrary {}
      }

      div("right-panel") {
        Playlist {}
      }

    }
  }

  class State(var tracks: List<String> = emptyList()): RState
  class Props: RProps()

}
