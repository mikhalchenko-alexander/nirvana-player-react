package com.anahoret.nirvanaplayer.components.playlist

import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.stores.*
import com.anahoret.nirvanaplayer.stores.model.Folder
import com.anahoret.nirvanaplayer.stores.model.Track
import kotlinx.html.*
import kotlinx.html.js.onMouseUpFunction
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent

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

      onMouseUpFunction = {
        val draggable = DragAndDropStore.getState().currentDraggable
        PlayerDispatcher.dispatch(DragEnded())
        draggable.let {
          when (it) {
            is DraggableTrack -> PlayerDispatcher.dispatch(TracksAddedAction(it.track))
            is DraggableFolder -> PlayerDispatcher.dispatch(TracksAddedAction(flattenTracks(it.folder)))
          }
        }
      }
    }
  }

  private fun flattenTracks(folder: Folder): List<Track> = folder.tracks + folder.folders.flatMap(::flattenTracks)

  class Props(var tracks: List<Track>): RProps()
}
