package com.anahoret.nirvanaplayer.components

import kotlinx.html.div
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent

class Playlist: ReactDOMStatelessComponent<Playlist.Props>() {
  companion object: ReactComponentSpec<Playlist, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    div("playlist") {

    }
  }

  class Props: RProps()
}
