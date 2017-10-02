package com.anahoret.nirvanaplayer.stores

import com.anahoret.flux.FluxReduceStore
import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.dto.FolderDto
import com.anahoret.nirvanaplayer.dto.TrackDto

class MediaLibraryRootLoaded(val folder: FolderDto)
class MediaLibraryFolderLoaded(val folder: FolderDto)

object MediaLibraryStore: FluxReduceStore<MediaLibraryState>(PlayerDispatcher) {

  override fun getInitialState(): MediaLibraryState {
    return MediaLibraryState(null)
  }

  override fun reduce(state: MediaLibraryState, action: Any): MediaLibraryState =
    when(action) {
      is MediaLibraryRootLoaded -> MediaLibraryState(Folder(action.folder))
      is MediaLibraryFolderLoaded -> if (state.rootFolder != null) {
        MediaLibraryState(state.rootFolder.updated(action.folder))
      } else MediaLibraryState(Folder(action.folder))
      else -> state
    }

}

class MediaLibraryState(val rootFolder: Folder?)

data class Folder(
        val id: Long,
        val name: String,
        val isOpened: Boolean,
        val isLoaded: Boolean,
        val folders: List<Folder> = emptyList(),
        val tracks: List<Track> = emptyList()
) {
  constructor(folderDto: FolderDto): this(
    id = folderDto.id,
    name = folderDto.name,
    isOpened = false,
    isLoaded = false,
    folders = folderDto.folders.map(::Folder),
    tracks = folderDto.tracks.map(::Track))

  fun isEmpty(): Boolean = isLoaded && folders.isEmpty() && tracks.isEmpty()

  fun updated(folderDto: FolderDto): Folder {
    val match = this.id == folderDto.id
    val updatedLoaded = if (match) true else isLoaded
    val updatedFolders = if (match) folderDto.folders.map(::Folder) else folders.map { it.updated(folderDto) }
    return copy(
      isLoaded = updatedLoaded,
      folders = updatedFolders
    )
  }
}

class Track(
        val id: Long,
        val title: String,
        val artist: String,
        val duration: Int
) {
  constructor(trackDto: TrackDto): this(
    id = trackDto.id,
    title = trackDto.title,
    artist = trackDto.artist,
    duration = trackDto.duration
  )
}
