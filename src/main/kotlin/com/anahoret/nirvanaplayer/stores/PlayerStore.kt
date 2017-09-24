package com.anahoret.nirvanaplayer.stores

import com.anahoret.flux.FluxReduceStore
import com.anahoret.nirvanaplayer.PlayerDispatcher

data class PlayerState(val tracks: List<String>)
data class MediaLibraryLoadedAction(val tracks: List<String>)

object PlayerStore: FluxReduceStore<PlayerState>(PlayerDispatcher) {

  override fun getInitialState(): PlayerState = PlayerState(emptyList())

  override fun reduce(state: PlayerState, action: Any): PlayerState {
    return when(action) {
      is MediaLibraryLoadedAction -> PlayerState(action.tracks)
      else -> state
    }
  }
}
