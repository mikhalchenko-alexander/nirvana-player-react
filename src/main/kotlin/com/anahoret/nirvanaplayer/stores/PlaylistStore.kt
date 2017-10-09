package com.anahoret.nirvanaplayer.stores

import com.anahoret.flux.FluxReduceStore
import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.stores.model.Track

data class PlayListState(val tracks: List<Track>)
data class TracksAddedAction(val tracks: List<Track>)
data class TracksRemovedAction(val tracks: List<Track>)

object PlaylistStore: FluxReduceStore<PlayListState>(PlayerDispatcher) {

  override fun getInitialState(): PlayListState = PlayListState(emptyList())

  override fun reduce(state: PlayListState, action: Any): PlayListState = when(action) {
    is TracksAddedAction -> state.copy(tracks = state.tracks + action.tracks)
    is TracksRemovedAction -> state.copy(tracks = state.tracks - action.tracks)
    else -> state
  }

}
