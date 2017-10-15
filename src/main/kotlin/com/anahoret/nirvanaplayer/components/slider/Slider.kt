package com.anahoret.nirvanaplayer.components.slider

import com.anahoret.nirvanaplayer.PlayerDispatcher
import com.anahoret.nirvanaplayer.stores.SliderValueChangedAction
import kotlinx.html.InputType
import kotlinx.html.div
import kotlinx.html.input
import kotlinx.html.js.*
import org.jetbrains.common.inputValue
import org.jetbrains.react.RProps
import org.jetbrains.react.ReactComponentNoState
import org.jetbrains.react.ReactComponentSpec
import org.jetbrains.react.dom.ReactDOMBuilder
import org.jetbrains.react.dom.ReactDOMStatelessComponent

class Slider: ReactDOMStatelessComponent<Slider.Props>() {
  companion object: ReactComponentSpec<Slider, Props, ReactComponentNoState>

  override fun ReactDOMBuilder.render() {
    div("slider") {
      input {
        type = InputType.range
        min = "${props.min}"
        max = "${props.max}"
        step = "${props.step}"
        value = "${props.value}"

        onChangeFunction = { e ->
          PlayerDispatcher.dispatch(SliderValueChangedAction(props.tag, e.inputValue.toInt()))
        }
      }
    }
  }

  class Props(var min: Int = 0,
              var max: Int = 100,
              var step: Int = 1,
              var value: Int = 0,
              var tag: String): RProps()
}
