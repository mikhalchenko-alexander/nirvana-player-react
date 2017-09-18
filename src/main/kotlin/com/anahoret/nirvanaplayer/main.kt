package com.anahoret.nirvanaplayer

import org.jetbrains.react.dom.ReactDOM
import kotlin.browser.document
import kotlinx.html.*
import org.jetbrains.react.dom.render

fun main(args: Array<String>) {
  ReactDOM.render(document.getElementById("player-root")) {
    div {
      PlayerView {}
    }
  }

}
