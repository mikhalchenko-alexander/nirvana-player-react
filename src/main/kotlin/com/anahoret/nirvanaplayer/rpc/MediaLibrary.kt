package com.anahoret.nirvanaplayer.rpc

import com.anahoret.nirvanaplayer.dto.FolderDto
import com.anahoret.nirvanaplayer.dto.TrackDto
import kotlinx.coroutines.experimental.async
import kotlin.js.Promise

fun loadMediaLibrary(): Promise<FolderDto> = async {
  FolderDto(
    id = 1,
    name = "<ROOT>",
    folders = listOf(
      FolderDto(id = 2, name = "ACDC", folders = emptyList(), tracks = listOf(
        TrackDto(id = 1, title = "Thunderstruck", artist = "ACDC", album = "The Razor's edge", duration = 120),
        TrackDto(id = 2, title = "High voltage", artist = "ACDC", album = "High voltage", duration = 255)
      )),
      FolderDto(id = 3, name = "Black sabbath", folders = emptyList(), tracks = listOf(
        TrackDto(id = 3, title = "Iron man", artist = "Black sabbath", album = "Paranoid", duration = 230),
        TrackDto(id = 4, title = "Paranoid", artist = "Black sabbath", album = "Paranoid", duration = 245)
      ))
    ),
    tracks = emptyList()
  )
}

fun parseFolderDto(json: dynamic): FolderDto = FolderDto(
    id = json.id as Long,
    name = json.name as String,
    folders = (json.folders as Array<dynamic>).map(::parseFolderDto),
    tracks = (json.tracks as Array<dynamic>).map(::parseTrackDto)
)

fun parseTrackDto(json: dynamic): TrackDto = TrackDto(
    id = json.id as Long,
    title = json.title as String,
    artist = json.artist as String,
    album = json.album as String,
    duration = json.duration as Int
)
