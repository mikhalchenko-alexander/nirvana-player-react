package com.anahoret.nirvanaplayer.components

import kotlinx.html.div
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent

private class ControlButton: ReactDOMStatelessComponent<ControlButton.Props>() {
  companion object: ReactComponentSpec<ControlButton, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    div("btn-player-control ${props.classes}") {
      children()
    }
  }

  open class Props(var classes: String): RProps()

}

class PlayPauseButton: ReactDOMStatelessComponent<PlayPauseButton.Props>() {
  companion object: ReactComponentSpec<PlayPauseButton, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    ControlButton {
      if (props.play) {
        classes = "btn-play"
        +">"
      } else {
        classes = "btn-pause"
        +"||"
      }
    }
  }

  class Props(var play: Boolean): RProps()
}

class StopButton: ReactDOMStatelessComponent<StopButton.Props>() {
  companion object: ReactComponentSpec<StopButton, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    ControlButton {
      classes = "btn-stop"
      +"|=|"
    }
  }

  class Props: RProps()
}

class PreviousButton: ReactDOMStatelessComponent<PreviousButton.Props>() {
  companion object: ReactComponentSpec<PreviousButton, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    ControlButton {
      classes = "btn-previous"
      +"|<"
    }
  }

  class Props: RProps()
}

class NextButton: ReactDOMStatelessComponent<NextButton.Props>() {
  companion object: ReactComponentSpec<NextButton, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    ControlButton {
      classes = "btn-next"
      +">|"
    }
  }

  class Props: RProps()
}
