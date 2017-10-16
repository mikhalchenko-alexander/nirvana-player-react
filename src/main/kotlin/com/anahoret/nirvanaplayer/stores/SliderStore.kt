package com.anahoret.nirvanaplayer.stores

import com.anahoret.flux.FluxReduceStore
import com.anahoret.nirvanaplayer.PlayerDispatcher

data class SliderState(val value: Int, val minValue: Int, val maxValue: Int)

abstract class SliderAction { abstract val tag: String }
data class SliderValueChangedAction(override val tag: String, val value: Int): SliderAction()

abstract class SliderStore(val tag: String): FluxReduceStore<SliderState>(PlayerDispatcher) {
  override fun getInitialState(): SliderState =
    SliderState(
      value = 0,
      minValue = 0,
      maxValue = 100
    )
  override fun reduce(state: SliderState, action: Any): SliderState {
    if (action !is SliderAction) return state
    if (action.tag != tag) return state
    return when (action) {
      is SliderValueChangedAction -> state.copy(value = action.value)
      else -> state
    }
  }
}

object VolumeSliderStore: SliderStore("volume")

object ProgressSliderStore: SliderStore("progress") {
  override fun reduce(state: SliderState, action: Any): SliderState {
    val st = super.reduce(state, action)
    return when (action) {
      is TrackPlayAction -> st.copy(maxValue = action.track.duration)
      else -> st
    }
  }
}
