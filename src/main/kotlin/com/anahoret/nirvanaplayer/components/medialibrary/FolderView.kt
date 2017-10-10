package com.anahoret.nirvanaplayer.components.medialibrary

import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.stores.model.Folder
import com.anahoret.nirvanaplayer.stores.MediaLibraryFolderOpenToggle
import kotlinx.html.*
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onDragStartFunction
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent
import org.w3c.dom.DragEventInit
import runtime.wrappers.jsstyle

class FolderView: ReactDOMStatelessComponent<FolderView.Props>() {
  companion object: ReactComponentSpec<FolderView, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    div("folder") {
      style = jsstyle { marginLeft = "${props.treeNodeMargin}px" }
      draggable = Draggable.true_

      span("name") {
        +("${props.folder.name}${if (props.folder.isEmpty()) " (empty)" else ""}")

        onClickFunction = {
          PlayerDispatcher.dispatch(MediaLibraryFolderOpenToggle(props.folder.id))
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

      onDragStartFunction = { e ->
        e.stopPropagation()
        @Suppress("UNCHECKED_CAST_TO_NATIVE_INTERFACE")
        val dragEvent = e as DragEventInit
        dragEvent.dataTransfer?.let { dataTransfer ->
          dataTransfer.setData("type", "folder")
          dataTransfer.setData("id", props.folder.id.toString())
        }
      }
    }
  }

  class Props(var folder: Folder, var treeNodeMargin: Int): RProps()
}
