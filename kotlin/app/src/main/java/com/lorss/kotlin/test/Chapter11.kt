package com.lorss.kotlin.test

/**
 * Created by Lorss on 18-10-19.
 */
class Chapter11 {


    companion object {

        fun test() {

            "android" should start with "lag : "
        }


        fun t21() {
            createHTML().table {
                tr {
                    td {
                        this + "cell"
                    }
                }
            }
        }

        fun createHTML(): HTML {
            return HTML()
        }

        class HTML {

            fun table(init: TABLE.() -> Unit) {

            }
        }

        class TABLE {

            fun tr(init: TR.() -> Unit) {

            }
        }

        class TR {
            fun td(init: TD.() -> Unit) {}
        }

        class TD {
            operator fun plus(another: Any) = Unit
        }


        object start

        infix fun String.should(x: start): StartWrapper = StartWrapper(this)

        class StartWrapper(val value: String) {

            infix fun with(prefix: String) =
                    if (!value.startsWith(prefix)) throw AssertionError("String does not start with $prefix: $value")
                    else Unit
        }

    }
}