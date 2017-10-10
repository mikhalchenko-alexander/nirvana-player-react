package com.anahoret.nirvanaplayer.components.medialibrary

import com.anahoret.nirvanaplayer.stores.model.Track
import com.anahoret.nirvanaplayer.toTimeString
import kotlinx.html.Draggable
import kotlinx.html.div
import kotlinx.html.draggable
import kotlinx.html.js.onDragStartFunction
import kotlinx.html.style
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent
import org.w3c.dom.DragEventInit
import runtime.wrappers.jsstyle

class TrackView: ReactDOMStatelessComponent<TrackView.Props>() {
  companion object: ReactComponentSpec<TrackView, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    div("track") {
      style = jsstyle { marginLeft = "${props.treeNodeMargin}px" }
      draggable = Draggable.true_

      +"${props.track.title} (${props.track.duration.toTimeString()})"

      onDragStartFunction = { e ->
        e.stopPropagation()
        @Suppress("UNCHECKED_CAST_TO_NATIVE_INTERFACE")
        val dragEvent = e as DragEventInit
        dragEvent.dataTransfer?.let { dataTransfer ->
          dataTransfer.setData("type", "track")
          dataTransfer.setData("id", props.track.id.toString())
        }
      }
    }
  }

  class Props(var track: Track, var treeNodeMargin: Int): RProps()
}
