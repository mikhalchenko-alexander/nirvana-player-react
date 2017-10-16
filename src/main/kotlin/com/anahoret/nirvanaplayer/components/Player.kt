package com.anahoret.nirvanaplayer.components

import com.anahoret.flux.ChangeEvent
import com.anahoret.nirvanaplayer.components.medialibrary.MediaLibrary
import com.anahoret.nirvanaplayer.components.playlist.Playlist
import com.anahoret.nirvanaplayer.stores.model.Folder
import com.anahoret.nirvanaplayer.stores.MediaLibraryStore
import com.anahoret.nirvanaplayer.stores.PlaylistStore
import com.anahoret.nirvanaplayer.stores.ProgressSliderStore
import com.anahoret.nirvanaplayer.stores.VolumeSliderStore
import com.anahoret.nirvanaplayer.stores.model.Track
import org.jetbrains.react.RProps
import org.jetbrains.react.RState

import kotlinx.html.*
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMComponent
import org.w3c.dom.HTMLAudioElement
import kotlin.browser.document

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
          val playListState = PlaylistStore.getState()
          playlistTracks = playListState.tracks
          playlistSelectedTrack = playListState.selectedTrack
          playlistPlayingTrack = playListState.playingTrack
          isPlaying = playListState.isPlaying

          audioPlayer()?.also { audio ->
            if (audio.paused && playListState.isPlaying) {
              audio.play()
            } else if (!audio.paused && !playListState.isPlaying) {
              audio.pause()
            }
          }
        }
      }
    }

    VolumeSliderStore.subscribe {
      when(it) {
        is ChangeEvent -> setState {
          volumeValue = VolumeSliderStore.getState().value
          audioPlayer()?.also { audio ->
            audio.volume = volumeValue.toDouble() / 100
          }
        }
      }
    }

    ProgressSliderStore.subscribe {
      when(it) {
        is ChangeEvent -> setState {
          progressValue = ProgressSliderStore.getState().value
        }
      }
    }

  }

  override fun ReactDOMBuilder.render() {
    div("player-view") {
      div("left-panel") {
        PlayerControls {
          volumeValue = state.volumeValue
          progressValue = state.progressValue
          playingTrack = state.playlistPlayingTrack
          isPlaying = state.isPlaying
          trackUrl = props.trackUrl
        }
        MediaLibrary {
          folder = state.folder
          url = props.mediaLibraryUrl
        }
      }

      div("right-panel") {
        Playlist {
          tracks = state.playlistTracks
          selectedTrack = state.playlistSelectedTrack
        }
      }

    }
  }

  private fun audioPlayer(): HTMLAudioElement? =
    document.getElementById("audio-player") as HTMLAudioElement

  class State(var folder: Folder? = null,
              var playlistTracks: List<Track> = emptyList(),
              var playlistSelectedTrack: Track? = null,
              var playlistPlayingTrack: Track? = null,
              var isPlaying: Boolean = false,
              var volumeValue: Int = 100,
              var progressValue: Int = 0): RState

  class Props(
    var mediaLibraryUrl: String,
    var trackUrl: String): RProps()

}
