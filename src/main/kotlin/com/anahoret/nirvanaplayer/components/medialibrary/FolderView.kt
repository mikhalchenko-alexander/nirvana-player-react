package com.anahoret.nirvanaplayer.components.medialibrary

import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.rpc.loadFolder
import com.anahoret.nirvanaplayer.stores.DragStarted
import com.anahoret.nirvanaplayer.stores.DraggableFolder
import com.anahoret.nirvanaplayer.stores.model.Folder
import com.anahoret.nirvanaplayer.stores.MediaLibraryFolderLoaded
import com.anahoret.nirvanaplayer.stores.MediaLibraryFolderOpenToggle
import kotlinx.html.div
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onMouseDownFunction
import kotlinx.html.span
import kotlinx.html.style
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent
import runtime.wrappers.jsstyle

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
            treeNodeMargin = MediaLibrary.TREE_NODE_MARGIN
          }
        }

        props.folder.tracks.forEach { t ->
          TrackView {
            track = t
            treeNodeMargin = MediaLibrary.TREE_NODE_MARGIN
          }
        }
      }

      onMouseDownFunction = { e->
        e.preventDefault()
        e.stopPropagation()
        PlayerDispatcher.dispatch(DragStarted(DraggableFolder(props.folder)))
      }
    }
  }

  class Props(var folder: Folder, var treeNodeMargin: Int): RProps()
}
