package com.anahoret.nirvanaplayer

import react.dom.ReactDOM
import kotlin.browser.document
import react.dom.*
import kotlinx.html.*

fun main(args: Array<String>) {
  ReactDOM.render(document.getElementById("player-root")) {
    div {
      PlayerView {}
    }
  }

}
