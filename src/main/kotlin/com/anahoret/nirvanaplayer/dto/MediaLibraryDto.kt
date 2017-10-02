package com.anahoret.nirvanaplayer.dto

class FolderDto(
  val id: Long,
  val name: String,
  val folders: List<FolderDto>,
  val tracks: List<TrackDto>
)

class TrackDto(
  val id: Long,
  val title: String,
  val artist: String,
  val duration: Int
)
