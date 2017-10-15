package com.anahoret.nirvanaplayer.stores

import com.anahoret.flux.FluxReduceStore
import com.anahoret.nirvanaplayer.PlayerDispatcher

data class SliderState(val value: Int)

data class SliderValueChangedAction(val tag: String, val value: Int)

abstract class SliderStore(val tag: String): FluxReduceStore<SliderState>(PlayerDispatcher) {
  override fun getInitialState(): SliderState = SliderState(0)
  override fun reduce(state: SliderState, action: Any): SliderState = when (action) {
    is SliderValueChangedAction -> if (action.tag == tag) SliderState(action.value) else state
    else -> state
  }
}

object VolumeSliderStore: SliderStore("volume")
object ProgressSliderStore: SliderStore("progress")
