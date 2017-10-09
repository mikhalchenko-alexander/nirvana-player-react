package com.anahoret.nirvanaplayer.components.medialibrary

import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.rpc.loadMediaLibrary
import com.anahoret.nirvanaplayer.stores.MediaLibraryLoaded
import com.anahoret.nirvanaplayer.stores.model.Folder
import kotlinx.html.div
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent

class MediaLibrary: ReactDOMStatelessComponent<MediaLibrary.Props>() {
  companion object: ReactComponentSpec<MediaLibrary, Props, ReactComponentNoState> {
    const val TREE_NODE_MARGIN = 10
  }

  init {
    loadMediaLibrary()
      .then({ PlayerDispatcher.dispatch(MediaLibraryLoaded(it)) })
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
