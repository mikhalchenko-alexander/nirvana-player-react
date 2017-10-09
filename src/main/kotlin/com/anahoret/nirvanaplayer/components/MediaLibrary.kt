package com.anahoret.nirvanaplayer.components

import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.rpc.loadFolder
import com.anahoret.nirvanaplayer.rpc.loadRoot
import com.anahoret.nirvanaplayer.stores.Folder
import com.anahoret.nirvanaplayer.stores.MediaLibraryFolderLoaded
import com.anahoret.nirvanaplayer.stores.MediaLibraryRootLoaded
import com.anahoret.nirvanaplayer.stores.Track
import com.anahoret.nirvanaplayer.toTimeString
import com.anahoret.nirvanaplayer.stores.*
import kotlinx.html.div
import kotlinx.html.js.onClickFunction
import kotlinx.html.span
import kotlinx.html.style
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent
import runtime.wrappers.jsstyle

const val TREE_NODE_MARGIN = 10

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
          treeNodeMargin = 0
        }
      }
    }
  }

  class Props(var folder: Folder?): RProps()
}

class FolderView: ReactDOMStatelessComponent<FolderView.Props>() {
  companion object: ReactComponentSpec<FolderView, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    div("folder") {
      style = jsstyle { marginLeft = "${props.treeNodeMargin}px" }
      span("name") {
        +("${props.folder.name}${if (props.folder.isEmpty()) " (empty)" else ""}")

        onClickFunction = {
          if (!props.folder.isLoaded) {
            loadFolder(props.folder.id)
              .then({ PlayerDispatcher.dispatch(MediaLibraryFolderLoaded(it)) })
              .then({ PlayerDispatcher.dispatch(MediaLibraryFolderOpenToggle(props.folder.id)) })
          } else {
            PlayerDispatcher.dispatch(MediaLibraryFolderOpenToggle(props.folder.id))
          }
        }
      }
      val openedClass = if (props.folder.isOpened) " opened" else ""
      div("folder-content$openedClass") {
        props.folder.folders.forEach { subFolder ->
          FolderView {
            folder = subFolder
            treeNodeMargin = TREE_NODE_MARGIN
          }
        }

        props.folder.tracks.forEach { t ->
          TrackView {
            track = t
            treeNodeMargin = TREE_NODE_MARGIN
          }
        }
      }

    }
  }

  class Props(var folder: Folder, var treeNodeMargin: Int): RProps()
}

class TrackView: ReactDOMStatelessComponent<TrackView.Props>() {
  companion object: ReactComponentSpec<TrackView, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    div("track") {
      style = jsstyle { marginLeft = "${props.treeNodeMargin}px" }
      +"${props.track.title} (${props.track.duration.toTimeString()})"
    }
  }

  class Props(var track: Track, var treeNodeMargin: Int): RProps()
}
