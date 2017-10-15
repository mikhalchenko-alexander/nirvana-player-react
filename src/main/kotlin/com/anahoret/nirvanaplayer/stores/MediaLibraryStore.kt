package com.anahoret.nirvanaplayer.stores

import com.anahoret.flux.FluxReduceStore
import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.dto.FolderDto
import com.anahoret.nirvanaplayer.stores.model.Folder

data class MediaLibraryState(val rootFolder: Folder?)

data class MediaLibraryLoadedAction(val rootFolder: FolderDto)
data class MediaLibraryFolderOpenToggleAction(val folderId: Long)

object MediaLibraryStore : FluxReduceStore<MediaLibraryState>(PlayerDispatcher) {

  override fun getInitialState(): MediaLibraryState = MediaLibraryState(null)

  override fun reduce(state: MediaLibraryState, action: Any): MediaLibraryState =
    when (action) {
      is MediaLibraryLoadedAction -> MediaLibraryState(Folder(action.rootFolder))

      is MediaLibraryFolderOpenToggleAction -> MediaLibraryState(state.rootFolder?.toggleOpen(action.folderId))

      else -> state
    }

}

