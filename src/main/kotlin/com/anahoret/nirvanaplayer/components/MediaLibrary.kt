package com.anahoret.nirvanaplayer.components

import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.rpc.loadFolder
import com.anahoret.nirvanaplayer.rpc.loadRoot
import com.anahoret.nirvanaplayer.stores.Folder
import com.anahoret.nirvanaplayer.stores.MediaLibraryFolderLoaded
import com.anahoret.nirvanaplayer.stores.MediaLibraryRootLoaded
import com.anahoret.nirvanaplayer.stores.Track
import kotlinx.html.div
import kotlinx.html.js.onClickFunction
import kotlinx.html.span
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent

class MediaLibrary: ReactDOMStatelessComponent<MediaLibrary.Props>() {
  companion object: ReactComponentSpec<MediaLibrary, Props, ReactComponentNoState>

  init {
    loadRoot()
      .then({ PlayerDispatcher.dispatch(MediaLibraryRootLoaded(it)) })
  }

  override fun ReactDOMBuilder.render() {
    div("media-library") {
      props.folder?.let { rootFolder ->
        FolderView {
          folder = rootFolder
        }
      }
    }
  }

  class Props(var folder: Folder?): RProps()
}

class FolderView: ReactDOMStatelessComponent<FolderView.Props>() {
  companion object: ReactComponentSpec<FolderView, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    with(props) {
      div {
        span {
          +("${folder.name}${if (folder.isEmpty()) " (empty)" else ""}")
          if (!folder.isLoaded) {
            onClickFunction = {
              loadFolder(folder.id)
                  .then({ PlayerDispatcher.dispatch(MediaLibraryFolderLoaded(it)) })
            }
          }
        }

        folder.folders.forEach { subFolder ->
          FolderView {
            folder = subFolder
          }
        }

        folder.tracks.forEach { t ->
          TrackView {
            track = t
          }
        }

      }
    }
  }

  class Props(var folder: Folder): RProps()
}

class TrackView: ReactDOMStatelessComponent<TrackView.Props>() {
  companion object: ReactComponentSpec<TrackView, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    with(props.track) {
      div {
        +"$title ($duration)"
      }
    }
  }

  class Props(var track: Track): RProps()
}
