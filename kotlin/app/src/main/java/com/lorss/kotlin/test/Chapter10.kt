package com.lorss.kotlin.test

import ru.yole.jkid.JsonExclude
import ru.yole.jkid.JsonName
import ru.yole.jkid.findAnnotation
import ru.yole.jkid.joinToStringBuilder
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

/**
 * Created by Lorss on 18-10-18.
 */
class Chapter10 {


    companion object {


        fun test() {

            remove(1)

            Person::class.memberProperties
        }

        @Deprecated("", ReplaceWith("removeAt(index, 0)"))
        fun remove(index: Int) {
        }


        data class Person(
                @JsonName("n") val name: String,
                val age: Int
        )


    }

    private fun StringBuilder.serializeObject(obj: Any) {
        obj.javaClass.kotlin.memberProperties
                .filter { it.findAnnotation<JsonExclude>() == null }
                .joinToStringBuilder(this, prefix = "{", postfix = "}") {
                    serializeProperty(it, obj)
                }
    }

    private fun StringBuilder.serializeProperty(
            prop: KProperty1<Any, *>, obj: Any
    ) {
        val jsonNameAnn = prop.findAnnotation<JsonName>()
        val propName = jsonNameAnn?.name ?: prop.name
        serializeString(propName)
        append(": ")
        serializePropertyValue(prop.get(obj))
    }

    private fun StringBuilder.serializeString(propName: String) {
        append(propName)
    }

    private fun StringBuilder.serializePropertyValue(value: Any?) {
        append(value)
    }
}



