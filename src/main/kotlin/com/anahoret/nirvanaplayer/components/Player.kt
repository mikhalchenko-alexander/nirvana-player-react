package com.anahoret.nirvanaplayer.components

import com.anahoret.flux.ChangeEvent
import com.anahoret.nirvanaplayer.components.medialibrary.MediaLibrary
import com.anahoret.nirvanaplayer.components.playlist.Playlist
import com.anahoret.nirvanaplayer.stores.model.Folder
import com.anahoret.nirvanaplayer.stores.MediaLibraryStore
import com.anahoret.nirvanaplayer.stores.PlaylistStore
import com.anahoret.nirvanaplayer.stores.model.Track
import org.jetbrains.react.RProps
import org.jetbrains.react.RState

import kotlinx.html.*
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMComponent

class Player: ReactDOMComponent<Player.Props, Player.State>() {
  companion object: ReactComponentSpec<Player, Props, State>

  init {
    state = State()

    MediaLibraryStore.subscribe {
      when (it) {
        is ChangeEvent -> setState {
          folder = MediaLibraryStore.getState().rootFolder
        }
      }
    }

    PlaylistStore.subscribe {
      when(it) {
        is ChangeEvent -> setState {
          playlistTracks = PlaylistStore.getState().tracks
        }
      }
    }

  }

  override fun ReactDOMBuilder.render() {
    div("player-view") {
      div("left-panel") {
        PlayerControls {}
        MediaLibrary {
          folder = state.folder
        }
      }

      div("right-panel") {
        Playlist {
          tracks = state.playlistTracks
        }
      }

    }
  }

  class State(var folder: Folder? = null, var playlistTracks: List<Track> = emptyList()): RState
  class Props: RProps()

}
