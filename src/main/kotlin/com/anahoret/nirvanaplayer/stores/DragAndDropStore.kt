package com.anahoret.nirvanaplayer.stores

import com.anahoret.flux.FluxReduceStore
import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.stores.model.Folder
import com.anahoret.nirvanaplayer.stores.model.Track

class DragAndDropState(var currentDraggable: Draggable? = null)

interface Draggable
data class DraggableTrack(val track: Track): Draggable
data class DraggableFolder(val folder: Folder): Draggable

data class DragStarted(val draggable: Draggable)
class DragEnded

object DragAndDropStore: FluxReduceStore<DragAndDropState>(PlayerDispatcher) {

  override fun getInitialState(): DragAndDropState = DragAndDropState()

  override fun reduce(state: DragAndDropState, action: Any): DragAndDropState {
    return when (action) {
      is DragStarted -> DragAndDropState(action.draggable)
      is DragEnded -> DragAndDropState()
      else -> state
    }
  }

}
