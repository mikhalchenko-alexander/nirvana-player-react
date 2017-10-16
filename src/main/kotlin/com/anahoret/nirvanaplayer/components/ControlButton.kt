package com.anahoret.nirvanaplayer.components

import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.stores.NextTrackAction
import com.anahoret.nirvanaplayer.stores.PlayTrackToggleAction
import com.anahoret.nirvanaplayer.stores.PreviousTrackAction
import kotlinx.html.div
import kotlinx.html.js.onClickFunction
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent
import org.w3c.dom.events.Event

private class ControlButton: ReactDOMStatelessComponent<ControlButton.Props>() {
  companion object: ReactComponentSpec<ControlButton, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    div("btn-player-control ${props.classes}") {
      children()
      onClickFunction = props.onClickFunction
    }
  }

  open class Props(var classes: String, var onClickFunction: (Event) -> Unit): RProps()

}

class PlayPauseButton: ReactDOMStatelessComponent<PlayPauseButton.Props>() {
  companion object: ReactComponentSpec<PlayPauseButton, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    ControlButton {
      if (props.isPlaying) {
        classes = "btn-pause"
        +"||"
      } else {
        classes = "btn-play"
        +">"
      }

      onClickFunction = { PlayerDispatcher.dispatch(PlayTrackToggleAction()) }
    }
  }

  class Props(var isPlaying: Boolean): RProps()
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

      onClickFunction = { PlayerDispatcher.dispatch(PreviousTrackAction()) }
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

      onClickFunction = { PlayerDispatcher.dispatch(NextTrackAction()) }
    }
  }

  class Props: RProps()
}
