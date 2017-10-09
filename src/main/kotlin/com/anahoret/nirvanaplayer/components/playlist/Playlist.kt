package com.anahoret.nirvanaplayer.components.playlist

import com.anahoret.nirvanaplayer.stores.Track
import kotlinx.html.*
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
    }
  }

  class Props(var tracks: List<Track>): RProps()
}
