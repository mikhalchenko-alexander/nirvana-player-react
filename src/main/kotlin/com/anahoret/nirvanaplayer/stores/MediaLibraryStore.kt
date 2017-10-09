package com.anahoret.nirvanaplayer.stores

import com.anahoret.flux.FluxReduceStore
import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.dto.FolderDto
import com.anahoret.nirvanaplayer.stores.model.Folder

class MediaLibraryLoaded(val rootFolder: FolderDto)
class MediaLibraryFolderOpenToggle(val folderId: Long)

object MediaLibraryStore : FluxReduceStore<MediaLibraryState>(PlayerDispatcher) {

  override fun getInitialState(): MediaLibraryState = MediaLibraryState(null)

  override fun reduce(state: MediaLibraryState, action: Any): MediaLibraryState =
    when (action) {
      is MediaLibraryLoaded -> MediaLibraryState(Folder(action.rootFolder))

      is MediaLibraryFolderOpenToggle -> MediaLibraryState(state.rootFolder?.toggleOpen(action.folderId))
      
      else -> state
    }

}

class MediaLibraryState(val rootFolder: Folder?)
