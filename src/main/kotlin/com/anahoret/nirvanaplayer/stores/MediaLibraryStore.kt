package com.anahoret.nirvanaplayer.stores

import com.anahoret.flux.FluxReduceStore
import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.dto.FolderDto
import com.anahoret.nirvanaplayer.stores.model.Folder

class MediaLibraryRootLoaded(val folder: FolderDto)
class MediaLibraryFolderLoaded(val folder: FolderDto)
class MediaLibraryFolderOpenToggle(val folderId: Long)

object MediaLibraryStore : FluxReduceStore<MediaLibraryState>(PlayerDispatcher) {

  override fun getInitialState(): MediaLibraryState = MediaLibraryState(null)

  override fun reduce(state: MediaLibraryState, action: Any): MediaLibraryState =
    when (action) {
      is MediaLibraryRootLoaded -> MediaLibraryState(Folder(action.folder))

      is MediaLibraryFolderLoaded -> if (state.rootFolder != null) {
        MediaLibraryState(state.rootFolder.updated(action.folder))
      } else MediaLibraryState(Folder(action.folder))

      is MediaLibraryFolderOpenToggle -> MediaLibraryState(state.rootFolder?.toggleOpen(action.folderId))
      
      else -> state
    }

}

class MediaLibraryState(val rootFolder: Folder?)
