package com.anahoret.nirvanaplayer.stores.model

import com.anahoret.nirvanaplayer.dto.TrackDto

data class Track(
  val id: Long,
  val title: String,
  val artist: String,
  val album: String,
  val duration: Int
) {
  constructor(trackDto: TrackDto) : this(
    id = trackDto.id,
    title = trackDto.title,
    artist = trackDto.artist,
    album = trackDto.album,
    duration = trackDto.duration
  )

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || this::class.js != other::class.js) return false

    other as Track

    if (id != other.id) return false

    return true
  }

  override fun hashCode(): Int = id.hashCode()

}
