@file:Suppress("FunctionName")

package kr.valor.juggernaut.extensions

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified R : Any?, reified T : Any> LateInitReadyOnlyProperty(
    crossinline block: R.() -> T
) = object : ReadOnlyProperty<R, T> {
    private var field: T? = null

    override fun getValue(thisRef: R, property: KProperty<*>): T =
        field ?: thisRef.block().also(::field::set)
}