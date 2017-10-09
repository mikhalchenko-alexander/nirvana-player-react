package com.anahoret.nirvanaplayer.components.medialibrary

import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.stores.TracksAddedAction
import com.anahoret.nirvanaplayer.stores.model.Track
import com.anahoret.nirvanaplayer.toTimeString
import kotlinx.html.div
import kotlinx.html.js.onClickFunction
import kotlinx.html.style
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent
import runtime.wrappers.jsstyle

class TrackView: ReactDOMStatelessComponent<TrackView.Props>() {
  companion object: ReactComponentSpec<TrackView, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    div("track") {
      style = jsstyle { marginLeft = "${props.treeNodeMargin}px" }
      +"${props.track.title} (${props.track.duration.toTimeString()})"

      onClickFunction = { PlayerDispatcher.dispatch(TracksAddedAction(props.track)) }
    }
  }

  class Props(var track: Track, var treeNodeMargin: Int): RProps()
}