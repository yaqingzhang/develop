package com.runningmessage.kotref.kotlin.discuss

import com.runningmessage.kotref.utils.wrap

/**
 * Created by Lorss on 19-1-3.
 */
class Discuss003 {


    companion object {

        fun test() = wrap {

            val list = listOf<Int>()

            list.indices.forEach { index ->
                if (index == 2) return@forEach

                val item = list[index]

                // do something for item
            }
        }
    }
}