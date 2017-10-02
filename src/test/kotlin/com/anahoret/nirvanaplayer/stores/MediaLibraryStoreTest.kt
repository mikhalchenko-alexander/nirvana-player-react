package com.anahoret.nirvanaplayer.stores

import com.anahoret.nirvanaplayer.dto.FolderDto
import kotlin.test.*

class MediaLibraryStoreTest {

  @Test
  fun dummyTest() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun storeTest() {
    val initialFolder = Folder(
        id = 1,
        name = "Folder",
        isLoaded = false,
        isOpened = false,
        folders = emptyList(),
        tracks = emptyList()
    )

    val expected = Folder(
        id = 1,
        name = "Folder",
        isLoaded = true,
        isOpened = false,
        folders = listOf(Folder(
            id = 2,
            name = "Nested",
            isLoaded = false,
            isOpened = false,
            folders = emptyList(),
            tracks = emptyList()
        )),
        tracks = emptyList()
    )

    val folderDto = FolderDto(
        id = 1,
        name = "Folder",
        folders = listOf(FolderDto(
            id = 2,
            name = "Nested",
            folders = emptyList(),
            tracks = emptyList()
        )),
        tracks = emptyList()
    )

    assertEquals(expected, initialFolder.updated(folderDto))
  }

}
