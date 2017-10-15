package com.anahoret.nirvanaplayer.components

import com.anahoret.nirvanaplayer.components.slider.Slider
import com.anahoret.nirvanaplayer.stores.model.Track
import kotlinx.html.audio
import kotlinx.html.div
import kotlinx.html.id
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
        audio {
          id = "audio-player"
          props.playingTrack?.let { curTrack ->
            src = "${props.trackUrl}/${curTrack.id}"
          }
        }
        div("buttons") {
          PreviousButton {}
          StopButton {}
          PlayPauseButton {
            isPlaying = props.isPlaying
          }
          NextButton {}
        }
        div("volume-slider") {
          Slider {
            tag = "volume"
            value = props.volumeValue
          }
          div { +"${props.volumeValue}%" }
        }
      }
      div("progress-slider") {
        Slider {
          tag = "progress"
          value = props.progressValue
        }
        props.playingTrack?.let { t ->
          div { +"${t.artist} - ${t.title}" }
        }
      }
    }
  }

  class Props(
    var volumeValue: Int,
    var progressValue: Int,
    var playingTrack: Track?,
    var isPlaying: Boolean,
    var trackUrl: String
  ): RProps()

}
