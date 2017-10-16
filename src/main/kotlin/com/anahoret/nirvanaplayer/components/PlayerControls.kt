package com.anahoret.nirvanaplayer.components

import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.components.slider.Slider
import com.anahoret.nirvanaplayer.stores.NextTrackAction
import com.anahoret.nirvanaplayer.stores.ProgressSliderStore
import com.anahoret.nirvanaplayer.stores.SliderValueChangedAction
import com.anahoret.nirvanaplayer.stores.VolumeSliderStore
import com.anahoret.nirvanaplayer.stores.model.Track
import com.anahoret.nirvanaplayer.toTimeString
import kotlinx.html.audio
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.js.onEndedFunction
import kotlinx.html.js.onTimeUpdateFunction
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent
import org.w3c.dom.HTMLAudioElement
import kotlin.browser.document

class PlayerControls: ReactDOMStatelessComponent<PlayerControls.Props>() {
  companion object: ReactComponentSpec<PlayerControls, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    div("player-controls") {
      div("player-controls-wrapper") {
        val audioId = "audio-player"
        audio {
          id = audioId
          props.playingTrack?.let { curTrack ->
            src = "${props.trackUrl}/${curTrack.id}"
          }

          onTimeUpdateFunction = { _ ->
            val audio = document.getElementById(audioId) as HTMLAudioElement
            PlayerDispatcher.dispatch(SliderValueChangedAction(ProgressSliderStore.tag, audio.currentTime.toInt()))
          }

          onEndedFunction = {
            PlayerDispatcher.dispatch(NextTrackAction())
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
            tag = VolumeSliderStore.tag
            value = props.volumeValue
          }
          div { +"${props.volumeValue}%" }
        }
      }
      div("progress-slider") {
        Slider {
          tag = ProgressSliderStore.tag
          value = props.progressValue
          min = props.progressMinValue
          max = props.progressMaxValue
        }
        props.playingTrack?.let { t ->
          div("playing-track") {
            div("title") {
              +"${t.artist} - ${t.title}"
            }
            div("time") {
              +props.progressValue.toTimeString()
            }
          }
        }
      }
    }
  }

  class Props(
    var volumeValue: Int,
    var progressValue: Int,
    var progressMinValue: Int = 0,
    var progressMaxValue: Int = 100,
    var playingTrack: Track?,
    var isPlaying: Boolean,
    var trackUrl: String
  ): RProps()

}
