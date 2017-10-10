package com.anahoret.nirvanaplayer.components.playlist

import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.stores.*
import com.anahoret.nirvanaplayer.stores.model.Track
import kotlinx.html.*
import kotlinx.html.js.onDragOverFunction
import kotlinx.html.js.onDropFunction
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent
import org.w3c.dom.DragEventInit

class Playlist: ReactDOMStatelessComponent<Playlist.Props>() {
  companion object: ReactComponentSpec<Playlist, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    div("playlist") {
      table("tracks") {
        thead {
          tr {
            th { +"Artist" }
            th { +"Title" }
            th { +"Album" }
            th { +"Duration" }
          }
        }
        tbody {
          props.tracks.forEach {
            TrackRow {
              track = it
            }
          }
        }
      }

      onDragOverFunction = { e -> e.preventDefault() }

      onDropFunction = { e ->
        e.preventDefault()
        @Suppress("UNCHECKED_CAST_TO_NATIVE_INTERFACE")
        val dragEvent = e as DragEventInit
        dragEvent.dataTransfer?.apply {
          val draggableType = getData("type")
          when (draggableType) {
            "track" -> {
              val trackId = getData("id").toLong()
              MediaLibraryStore.getState().rootFolder?.findTrackById(trackId)?.let {
                PlayerDispatcher.dispatch(TracksAddedAction(it))
              }
            }

            "folder" -> {
              val folderId = getData("id").toLong()
              MediaLibraryStore.getState().rootFolder?.findFolderById(folderId)?.let {
                PlayerDispatcher.dispatch(TracksAddedAction(it.flattenTracks()))
              }
            }
          }
        }
      }
    }
  }

  class Props(var tracks: List<Track>): RProps()
}
