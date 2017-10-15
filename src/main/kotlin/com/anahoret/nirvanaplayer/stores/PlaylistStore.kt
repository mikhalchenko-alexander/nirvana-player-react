package com.anahoret.nirvanaplayer.stores

import com.anahoret.flux.FluxReduceStore
import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.stores.model.Track

data class PlayListState(val tracks: List<Track>, val selectedTrack: Track?, val playingTrack: Track?)

data class TracksAddedAction(val tracks: List<Track>) {
  constructor(track: Track): this(listOf(track))
}
data class TracksRemovedAction(val tracks: List<Track>) {
  constructor(track: Track): this(listOf(track))
}
data class TrackSelectedAction(val track: Track)
data class TrackPlayAction(val track: Track)

object PlaylistStore: FluxReduceStore<PlayListState>(PlayerDispatcher) {

  override fun getInitialState(): PlayListState = PlayListState(emptyList(), null, null)

  override fun reduce(state: PlayListState, action: Any): PlayListState = when(action) {
    is TracksAddedAction -> state.copy(tracks = state.tracks + action.tracks.filterNot(state.tracks::contains))
    is TracksRemovedAction -> state.copy(tracks = state.tracks - action.tracks)
    is TrackSelectedAction -> state.copy(selectedTrack = action.track)
    is TrackPlayAction -> state.copy(playingTrack = action.track)
    else -> state
  }

}
