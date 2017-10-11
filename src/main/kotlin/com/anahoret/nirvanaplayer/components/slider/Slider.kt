package com.anahoret.nirvanaplayer.components.slider

import kotlinx.html.div
import kotlinx.html.js.onMouseDownFunction
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent
import kotlin.browser.document
import kotlin.browser.window

class Slider: ReactDOMStatelessComponent<Slider.Props>() {
  companion object: ReactComponentSpec<Slider, Props, ReactComponentNoState>

  @Suppress("UNCHECKED_CAST_TO_NATIVE_INTERFACE")
  override fun ReactDOMBuilder.render() {
    val orientationClass = if (props.horizontal) "horizontal" else "vertical"
    div("slider $orientationClass") {
      div("handle") {
        val mouseDownHandler =
          if (props.horizontal) ::horizontalSliderMouseDownEventHandler
          else ::verticalSliderMouseDownEventHandler

        onMouseDownFunction = mouseDownHandler

      }
    }
  }

  private fun HTMLDivElement.getCoords(): RelativeCoords {
    val box = getBoundingClientRect()

    return RelativeCoords(box.left + window.pageXOffset, box.top + window.pageYOffset)
  }

  private fun horizontalSliderMouseDownEventHandler(mde: Event) {
    val mouseDownEvent = mde.asDynamic().nativeEvent as MouseEvent
    val handle = mouseDownEvent.target as HTMLDivElement
    val slider = handle.parentElement as HTMLDivElement
    val handleCoords = handle.getCoords()
    val shiftX = mouseDownEvent.pageX - handleCoords.left
    val sliderCoords = slider.getCoords()

    document.onmousemove = { mme ->
      val mouseMoveEvent = mme as MouseEvent
      var newLeft = mouseMoveEvent.pageX - shiftX - sliderCoords.left
      if (newLeft < 0) {
        newLeft = 0.0
      }
      val rightEdge = slider.offsetWidth - handle.offsetWidth
      if (newLeft > rightEdge) {
        newLeft = rightEdge.toDouble()
      }

      handle.style.left = "${newLeft}px"
      Unit
    }

    document.onmouseup = { _ ->
      document.onmousemove = null
      document.onmouseup = null
      Unit
    }
  }

  private fun verticalSliderMouseDownEventHandler(mde: Event) {
    val mouseDownEvent = mde.asDynamic().nativeEvent as MouseEvent
    val handle = mouseDownEvent.target as HTMLDivElement
    val slider = handle.parentElement as HTMLDivElement
    val handleCoords = handle.getCoords()
    val shiftY = mouseDownEvent.pageY - handleCoords.top
    val sliderCoords = slider.getCoords()

    document.onmousemove = { mme ->
      val mouseMoveEvent = mme as MouseEvent
      var newTop = mouseMoveEvent.pageY - shiftY - sliderCoords.top
      if (newTop < 0) {
        newTop = 0.0
      }
      val bottomEdge = slider.offsetHeight - handle.offsetHeight
      if (newTop > bottomEdge) {
        newTop = bottomEdge.toDouble()
      }

      handle.style.top = "${newTop}px"
      Unit
    }

    document.onmouseup = { _ ->
      document.onmousemove = null
      document.onmouseup = null
      Unit
    }
  }

  private class RelativeCoords(val left: Double, val top: Double)

  class Props(var horizontal: Boolean): RProps()
}
