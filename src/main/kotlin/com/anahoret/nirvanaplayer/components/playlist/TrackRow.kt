package com.anahoret.nirvanaplayer.components.playlist

import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.stores.TracksRemovedAction
import com.anahoret.nirvanaplayer.stores.model.Track
import com.anahoret.nirvanaplayer.toTimeString
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
      td { +props.track.artist }
      td { +props.track.title }
      td { +props.track.album }
      td { +props.track.duration.toTimeString() }

      onClickFunction = { PlayerDispatcher.dispatch(TracksRemovedAction(props.track)) }
    }
  }

  class Props(var track: Track): RProps()
}