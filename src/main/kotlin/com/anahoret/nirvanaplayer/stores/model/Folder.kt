package com.anahoret.nirvanaplayer.stores.model

import com.anahoret.nirvanaplayer.dto.FolderDto

data class Folder(
  val id: Long,
  val name: String,
  val isOpened: Boolean,
  val folders: List<Folder> = emptyList(),
  val tracks: List<Track> = emptyList()
) {
  constructor(folderDto: FolderDto) : this(
    id = folderDto.id,
    name = folderDto.name,
    isOpened = false,
    folders = folderDto.folders.map(::Folder),
    tracks = folderDto.tracks.map(::Track))

  fun isEmpty(): Boolean = folders.isEmpty() && tracks.isEmpty()

  fun toggleOpen(folderId: Long): Folder {
    return if (id == folderId) copy(isOpened = !isOpened)
    else {
      val updatedFolders = folders.map { it.toggleOpen(folderId) }
      copy(folders = updatedFolders)
    }
  }
}
