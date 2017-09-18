package org.jetbrains.react.dom

import org.jetbrains.react.RProps
import org.jetbrains.react.RState
import org.jetbrains.react.ReactComponent
import org.jetbrains.react.ReactElement
import org.w3c.dom.*

@JsModule("react-dom")
external object ReactDOM {
  fun render(element: ReactElement?, container: Element?)
  fun <P : RProps, S : RState> findDOMNode(component: ReactComponent<P, S>): Element
  fun unmountComponentAtNode(domContainerNode: Element?)
}

fun ReactDOM.render(container: Element?, handler: ReactDOMBuilder.() -> Unit) =
        render(buildElement(handler), container)
