package com.anahoret.nirvanaplayer.components

import kotlinx.html.div
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent

class MediaLibrary: ReactDOMStatelessComponent<MediaLibrary.Props>() {
  companion object: ReactComponentSpec<MediaLibrary, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    div("media-library") {

    }
  }

  class Props: RProps()
}
