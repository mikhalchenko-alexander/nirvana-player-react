package com.anahoret.nirvanaplayer.stores

import com.anahoret.flux.FluxReduceStore
import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.stores.model.Track

data class PlayListState(
  val tracks: List<Track>,
  val selectedTrack: Track?,
  val playingTrack: Track?,
  val isPlaying: Boolean)

data class TracksAddedAction(val tracks: List<Track>) {
  constructor(track: Track): this(listOf(track))
}
data class TracksRemovedAction(val tracks: List<Track>) {
  constructor(track: Track): this(listOf(track))
}
data class TrackSelectedAction(val track: Track)
data class TrackPlayAction(val track: Track)
class TrackStopAction
class PlayTrackToggleAction
class PreviousTrackAction
class NextTrackAction

object PlaylistStore: FluxReduceStore<PlayListState>(PlayerDispatcher) {

  override fun getInitialState(): PlayListState =
    PlayListState(
      tracks = emptyList(),
      selectedTrack = null,
      playingTrack = null,
      isPlaying = false
    )

  override fun reduce(state: PlayListState, action: Any): PlayListState = when(action) {
    is TracksAddedAction -> state.copy(tracks = state.tracks + action.tracks.filterNot(state.tracks::contains))
    is TracksRemovedAction -> state.copy(tracks = state.tracks - action.tracks)
    is TrackSelectedAction -> state.copy(selectedTrack = action.track)
    is TrackPlayAction -> state.copy(playingTrack = action.track)
    is TrackStopAction -> state.copy(isPlaying = false)
    is PlayTrackToggleAction -> state.copy(isPlaying = !state.isPlaying)
    is PreviousTrackAction -> {
      state.playingTrack?.let { track ->
        val idx = state.tracks.indexOf(track)
        val newTrack =
          if (idx == 0) state.tracks.last()
          else state.tracks[idx - 1]
        state.copy(playingTrack = newTrack, selectedTrack = newTrack)
      } ?: state
    }
    is NextTrackAction -> {
      state.playingTrack?.let { track ->
        val idx = state.tracks.indexOf(track)
        val newTrack =
          if (idx < state.tracks.size - 1) state.tracks[idx + 1]
          else state.tracks.first()
        state.copy(playingTrack = newTrack, selectedTrack = newTrack)
      } ?: state
    }
    else -> state
  }

}
