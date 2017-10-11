package com.anahoret.nirvanaplayer.components

import com.anahoret.nirvanaplayer.components.slider.Slider
import kotlinx.html.div
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent

class PlayerControls: ReactDOMStatelessComponent<PlayerControls.Props>() {
  companion object: ReactComponentSpec<PlayerControls, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    div("player-controls") {
      div("player-controls-wrapper") {
        div("buttons") {
          PreviousButton {}
          StopButton {}
          PlayPauseButton {
            play = true
          }
          NextButton {}
        }
        div("volume-slider") {
          Slider {
            horizontal = true
          }
        }
      }
      div("progress-slider") {
        Slider {
          horizontal = true
        }
      }
    }
  }

  class Props: RProps()

}
