package com.anahoret.nirvanaplayer.rpc

import com.anahoret.nirvanaplayer.dto.FolderDto
import com.anahoret.nirvanaplayer.dto.TrackDto
import kotlinx.coroutines.experimental.async
import kotlin.js.Promise

fun loadMediaLibrary(url: String): Promise<FolderDto> = async {
  getAndParseResult(url, null, ::parseFolderDto)
}

fun parseFolderDto(json: dynamic): FolderDto = FolderDto(
  id = (json.id as Int).toLong(),
  name = json.name as String,
  folders = (json.folders as Array<dynamic>).map(::parseFolderDto),
  tracks = (json.tracks as Array<dynamic>).map(::parseTrackDto)
)

fun parseTrackDto(json: dynamic): TrackDto = TrackDto(
    id = (json.id as Int).toLong(),
    title = json.title as String,
    artist = json.artist as String,
    album = json.album as String,
    duration = json.duration as Int
)
