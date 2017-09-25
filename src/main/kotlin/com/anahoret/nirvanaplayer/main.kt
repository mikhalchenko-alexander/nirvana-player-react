package com.anahoret.nirvanaplayer

import com.anahoret.flux.Dispatcher
import com.anahoret.nirvanaplayer.components.Player
import org.jetbrains.react.dom.ReactDOM
import kotlin.browser.document
import kotlinx.html.*
import org.jetbrains.react.dom.render

object PlayerDispatcher: Dispatcher()

fun main(args: Array<String>) {
  ReactDOM.render(document.getElementById("player-root")) {
    div {
      Player {}
    }
  }

}
