package com.anahoret.nirvanaplayer.components.playlist

import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.stores.TrackPlayAction
import com.anahoret.nirvanaplayer.stores.TrackSelectedAction
import com.anahoret.nirvanaplayer.stores.model.Track
import com.anahoret.nirvanaplayer.toTimeString
import kotlinx.html.classes
import kotlinx.html.js.onClickFunction
import kotlinx.html.td
import kotlinx.html.tr
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent

class TrackRow: ReactDOMStatelessComponent<TrackRow.Props>() {
  companion object: ReactComponentSpec<TrackRow, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    tr("track-row") {
      if (props.selected) classes += "selected"

      td { +props.track.artist }
      td { +props.track.title }
      td { +props.track.album }
      td { +props.track.duration.toTimeString() }

      onClickFunction = {
        if (props.selected) {
          PlayerDispatcher.dispatch(TrackPlayAction(props.track))
        } else {
          PlayerDispatcher.dispatch(TrackSelectedAction(props.track))
        }
      }
    }
  }

  override fun shouldComponentUpdate(nextProps: Props, nextState: ReactComponentNoState): Boolean =
    nextProps.selected != props.selected

  class Props(var track: Track, var selected: Boolean): RProps()
}
