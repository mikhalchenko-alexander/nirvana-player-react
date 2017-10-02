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
  if (root != null) {
    ReactDOM.render(root) {
      div {
        Player {}
      }
    }
  } else {
    println("Can't find root node (#player-root).")
  }
}
