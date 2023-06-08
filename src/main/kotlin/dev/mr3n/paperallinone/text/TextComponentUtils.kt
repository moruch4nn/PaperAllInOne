@file:Suppress("unused")

package dev.mr3n.paperallinone.text

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor

fun colouredComponent(vararg components: Pair<TextColor,String>): Component {
    val component = Component.text()
    components.forEach { component.append(Component.text(it.second, it.first)) }
    return component.build()
}

fun styledComponent(vararg components: Pair<Style,String>): Component {
    val component = Component.text()
    components.forEach { component.append(Component.text(it.second, it.first)) }
    return component.build()
}

fun componentBuilder(block: ComponentBuilder.()->Unit): Component {
    val builder = ComponentBuilder()
    block.invoke(builder)
    return builder.build()
}

class ComponentBuilder {
    private val component = Component.text()

    fun line(vararg components: Pair<TextColor,String>) { components.forEach { this.component.append(Component.text(it.second, it.first)) } }
    fun line(vararg components: Pair<Style,String>) { components.forEach { this.component.append(Component.text(it.second, it.first)) } }

    fun build() = this.component.build()
}