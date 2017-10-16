package com.anahoret.nirvanaplayer.components.medialibrary

import com.anahoret.nirvanaplayer.stores.model.Track
import com.anahoret.nirvanaplayer.toTimeString
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onDragStartFunction
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent
import org.w3c.dom.DragEventInit
import runtime.wrappers.jsstyle
import kotlin.browser.document

class TrackView: ReactDOMStatelessComponent<TrackView.Props>() {
  companion object: ReactComponentSpec<TrackView, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    div("track") {
      style = jsstyle { marginLeft = "${props.treeNodeMargin}px" }
      draggable = Draggable.true_
      span("file-icon") {
        style = jsstyle { backgroundImage = "url('${props.iconsUrl}/note.png')" }
      }
      +"${props.track.title} (${props.track.duration.toTimeString()})"

      onDragStartFunction = { e ->
        e.stopPropagation()
        @Suppress("UNCHECKED_CAST_TO_NATIVE_INTERFACE")
        val dragEvent = e as DragEventInit
        dragEvent.dataTransfer?.let { dataTransfer ->
          dataTransfer.setData("type", "track")
          dataTransfer.setData("id", props.track.id.toString())
          dataTransfer.setDragImage(document.create.img { src = "${props.iconsUrl}/note.png" }, 13, 13)
        }
      }
    }
  }

  class Props(var track: Track,
              var treeNodeMargin: Int,
              var iconsUrl: String): RProps()
}
