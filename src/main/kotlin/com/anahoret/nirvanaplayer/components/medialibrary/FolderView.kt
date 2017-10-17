package com.anahoret.nirvanaplayer.components.medialibrary

import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.stores.model.Folder
import com.anahoret.nirvanaplayer.stores.MediaLibraryFolderOpenToggleAction
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onDragStartFunction
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent
import org.w3c.dom.DragEventInit
import runtime.wrappers.jsstyle
import kotlin.browser.document

class FolderView: ReactDOMStatelessComponent<FolderView.Props>() {
  companion object: ReactComponentSpec<FolderView, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    if (!props.isVisible) return
    val openedClass = if (props.folder.isOpened) " opened" else ""
    div("folder$openedClass") {
      style = jsstyle { marginLeft = "${props.treeNodeMargin}px" }
      draggable = Draggable.true_

      span("name") {
        span("folder-icon") {
          style = jsstyle {
            backgroundImage =
              if (props.folder.isOpened) "url('${props.iconsUrl}/folder_open.png')"
              else "url('${props.iconsUrl}/folder.png')"
          }
        }
        +("${props.folder.name}${if (props.folder.isEmpty()) " (empty)" else ""}")

        onClickFunction = {
          PlayerDispatcher.dispatch(MediaLibraryFolderOpenToggleAction(props.folder.id))
        }
      }

      div("folder-content") {
        props.folder.folders.forEach { subFolder ->
          FolderView {
            isVisible = props.folder.isOpened
            folder = subFolder
            treeNodeMargin = MediaLibrary.TREE_NODE_MARGIN
            iconsUrl = props.iconsUrl
          }
        }

        props.folder.tracks.forEach { t ->
          TrackView {
            isVisible = props.folder.isOpened
            track = t
            treeNodeMargin = MediaLibrary.TREE_NODE_MARGIN
            iconsUrl = props.iconsUrl
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
          dataTransfer.setDragImage(document.create.img { src = "${props.iconsUrl}/folder.png" }, 13, 13)
        }
      }
    }
  }

  override fun shouldComponentUpdate(nextProps: Props, nextState: ReactComponentNoState): Boolean =
    nextProps.isVisible && !props.isVisible || openChanged(nextProps.folder, props.folder)

  private fun openChanged(f1: Folder, f2: Folder): Boolean {
    if (f1.isOpened != f2.isOpened) return true
    return f1.folders.zip(f2.folders).any { openChanged(it.first, it.second) }
  }

  class Props(
    var isVisible: Boolean = false,
    var folder: Folder,
    var treeNodeMargin: Int,
    var iconsUrl: String): RProps()
}
