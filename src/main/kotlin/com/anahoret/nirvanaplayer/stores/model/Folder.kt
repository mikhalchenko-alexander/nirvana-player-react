package com.anahoret.nirvanaplayer.stores.model

import com.anahoret.nirvanaplayer.dto.FolderDto

data class Folder(
  val id: Long,
  val name: String,
  val isOpened: Boolean,
  val isLoaded: Boolean,
  val folders: List<Folder> = emptyList(),
  val tracks: List<Track> = emptyList()
) {
  constructor(folderDto: FolderDto) : this(
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
    val updatedTracks = if (match) folderDto.tracks.map(::Track) else tracks
    return copy(
      isLoaded = updatedLoaded,
      folders = updatedFolders,
      tracks = updatedTracks
    )
  }

  fun toggleOpen(folderId: Long): Folder {
    return if (id == folderId) copy(isOpened = !isOpened)
    else {
      val updatedFolders = folders.map { it.toggleOpen(folderId) }
      copy(folders = updatedFolders)
    }
  }
}
