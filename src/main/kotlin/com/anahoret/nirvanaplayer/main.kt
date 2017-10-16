package com.anahoret.nirvanaplayer

import com.anahoret.flux.Dispatcher
import com.anahoret.nirvanaplayer.components.Player
import org.jetbrains.react.dom.ReactDOM
import kotlin.browser.document
import kotlinx.html.*
import org.jetbrains.react.dom.render

object PlayerDispatcher: Dispatcher()

fun main(args: Array<String>) {
  val root = document.getElementById("player-root")
  if (root == null) {
    println("Can't find root node (#player-root).")
    return
  }

  val mediaLibraryRoot = root.getAttribute("data-media-library-url")
  if (mediaLibraryRoot == null) {
    println("Can't find 'data-media-library-url' attribute on root node.")
    return
  }

  val trackRoot = root.getAttribute("data-track-url")
  if (trackRoot == null) {
    println("Can't find 'data-track-url' attribute on root node.")
    return
  }

  val iconsRoot = root.getAttribute("data-icons-url")
  if (iconsRoot == null) {
    println("Can't find 'data-icons-url' attribute on root node.")
  }

  runtime.wrappers.require("Style/player.styl")
  ReactDOM.render(root) {
    div {
      Player {
        mediaLibraryUrl = mediaLibraryRoot
        trackUrl = trackRoot
        iconsUrl = iconsRoot ?: ""
      }
    }
  }
}
