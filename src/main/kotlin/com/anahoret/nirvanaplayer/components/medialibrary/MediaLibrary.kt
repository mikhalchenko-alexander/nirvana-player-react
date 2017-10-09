package com.anahoret.nirvanaplayer.components.medialibrary

import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.rpc.loadRoot
import com.anahoret.nirvanaplayer.stores.Folder
import com.anahoret.nirvanaplayer.stores.MediaLibraryRootLoaded
import kotlinx.html.div
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent

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
