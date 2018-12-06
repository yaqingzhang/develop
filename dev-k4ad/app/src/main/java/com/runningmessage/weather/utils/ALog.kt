package com.runningmessage.weather.utils

import android.annotation.SuppressLint

import android.os.Looper
import android.os.Process
import android.text.TextUtils
import java.lang.ref.Reference
import java.lang.ref.SoftReference
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap


private const val LINE = "-----------------------------------"

/**
 *  * Creator: lorss
 * <br></br> * Date: 17-8-16
 * <br></br> *
 */
@Suppress("unused")
open class ALog private constructor(moduleName: String, threadTag: String, defaultDepth: Int) {

    private val moduleName: String = if (TextUtils.isEmpty(moduleName)) "" else moduleName.trim { it <= ' ' }
    private val threadTag: String = if (TextUtils.isEmpty(threadTag)) "" else threadTag.trim { it <= ' ' }
    private val depthMin: Int = if (defaultDepth < 0) 0 else defaultDepth
    private var mDepth = 0
    private val mDepthList = ArrayList<Int>()

    private var mIsStarted = false
    private var mInitStartTime: Long = 0
    private var mInitLastTime: Long = 0
    @SuppressLint("SimpleDateFormat")
    private val timeFormat: SimpleDateFormat = SimpleDateFormat("yy-MM-dd HH:mm:ss:SSS")


    protected open val isShowThreadTag: Boolean
        get() = false

    protected open val isDebug: Boolean
        get() = true

    protected open val isForceDebug: Boolean
        get() = false

    protected open val isPrintErrorTrace: Boolean
        get() = true

    protected open val isForcePrintErrorTrace: Boolean
        get() = false

    protected open val isShowTime: Boolean
        get() = false

    protected open val isShowMethodTrace: Boolean
        get() = false

    init {
        mDepth = depthMin
    }

    private object Singleton {
        internal var INSTANCE: ALog = object : ALog("", "", 0) {
            override val isDebug: Boolean
                get() = false

            override val isPrintErrorTrace: Boolean
                get() = false
        }
    }

    fun nextDepth(depth: Int? = null) {
        if (depth == null) {
            setDepth(mDepth + 1)
        } else {
            setDepth(depth)
        }
    }

    fun lastDepth() {
        if (mDepthList.size == 0) {
            resetDepth()
        } else {
            var last: Int? = null
            while (last == null && mDepthList.size > 0)
                last = mDepthList.removeAt(mDepthList.size - 1)
            if (last == null)
                resetDepth()
            else
                mDepth = last
        }
    }

    private fun resetDepth() {
        mDepth = depthMin
        mDepthList.clear()
    }

    private fun setDepth(d: Int) {
        var depth = d
        if (depth < depthMin) depth = depthMin
        if (mDepth != depth) {
            mDepth = depth
            mDepthList.add(mDepth)
        }
    }

    private fun tabs(depth: Int): String {
        val sb = StringBuilder()
        for (i in 0 until depth) {
            sb.append("   \t")
        }
        return sb.toString()
    }

    fun v(tag: String, msg: String, vararg e: Throwable) {
        val debug = mIsDebug()
        val printTrace = mIsPrintErrorTrace()
        if (!debug && !printTrace) return
        val showTag = moduleName + (if (isShowThreadTag) SEPARATOR + threadTag else "") + SEPARATOR + tag
        val showTime = checkShowTime(tag, msg)
        val methodTrace = checkMethodTrace()
        if (e.isEmpty()) {
            if (debug) {
                android.util.Log.v(showTag, tabs(mDepth) + msg.replace("\n", "\n" + tabs(mDepth)) + showTime + methodTrace)
            }
        } else {
            if (debug) {
                android.util.Log.v(showTag, tabs(mDepth) + msg.replace("\n", "\n" + tabs(mDepth)) + " - catch Exception : " + e[0].javaClass.name + showTime + methodTrace)
            }
            if (printTrace) {
                e[0].printStackTrace()
            }
        }

    }

    fun d(tag: String, msg: String, vararg e: Throwable) {
        val debug = mIsDebug()
        val printTrace = mIsPrintErrorTrace()
        if (!debug && !printTrace) return
        val showTag = moduleName + (if (isShowThreadTag) SEPARATOR + threadTag else "") + SEPARATOR + tag
        val showTime = checkShowTime(tag, msg)
        val methodTrace = checkMethodTrace()
        if (e.isEmpty()) {
            if (debug) {
                android.util.Log.d(showTag, tabs(mDepth) + msg.replace("\n", "\n" + tabs(mDepth)) + showTime + methodTrace)
            }
        } else {
            if (debug) {
                android.util.Log.d(showTag, tabs(mDepth) + msg.replace("\n", "\n" + tabs(mDepth)) + " - catch Exception : " + e[0].javaClass.name + showTime + methodTrace)
            }
            if (printTrace) {
                e[0].printStackTrace()
            }
        }

    }

    fun e(tag: String, msg: String, vararg e: Throwable) {
        val debug = mIsDebug()
        val printTrace = mIsPrintErrorTrace()
        if (!debug && !printTrace) return
        val showTag = moduleName + (if (isShowThreadTag) SEPARATOR + threadTag else "") + SEPARATOR + tag
        val showTime = checkShowTime(tag, msg)
        val methodTrace = checkMethodTrace()
        if (e.isEmpty()) {
            if (debug) {
                android.util.Log.e(showTag, tabs(mDepth) + msg.replace("\n", "\n" + tabs(mDepth)) + showTime + methodTrace)
            }
        } else {
            if (debug) {
                android.util.Log.e(showTag, tabs(mDepth) + msg.replace("\n", "\n" + tabs(mDepth)) + " - catch Exception : " + e[0].javaClass.name + showTime + methodTrace)
            }
            if (printTrace) {
                e[0].printStackTrace()
            }
        }

    }

    private fun checkShowTime(tag: String, @Suppress("unused_parameter") msg: String): String {
        if (!isShowTime) return ""
        var showTime: String
        if (!mIsStarted) {
            mIsStarted = true
            showTime = start(tag)

        } else {
            showTime = showTime(tag, "")
        }
        if (!TextUtils.isEmpty(showTime)) {
            showTime = "\n" + tabs(4) + LINE + "\n" + tabs(4) + showTime + "\n" + tabs(4) + LINE
        }
        return showTime
    }


    private fun checkMethodTrace(): String {
        if (!isShowMethodTrace) return ""
        val elements = Throwable().stackTrace
        val sb = StringBuilder()
        if (elements != null && elements.size > 2) {
            for (i in 2 until elements.size) {
                sb.append("[" + toString(elements[i]) + "]")
                if (i != elements.size) {
                    sb.append(" <== ")
                }
            }
        }
        var methodTrace = sb.toString()
        if (!TextUtils.isEmpty(methodTrace)) {
            methodTrace = "\n" + tabs(4) + LINE + "\n" + tabs(4) + methodTrace + "\n" + tabs(4) + LINE
        }
        return methodTrace
    }

    private fun start(tag: String): String {
        mInitStartTime = 0
        mInitLastTime = 0
        return mShowTime(tag, "start >>> >>>")
    }

    private fun showTime(tag: String, msg: String): String {
        return mShowTime(tag, msg)
    }

    private fun mShowTime(@Suppress("unused_parameter") tag: String, msg: String): String {
        val debug = mIsDebug()
        val printTrace = mIsPrintErrorTrace()
        if (!debug && !printTrace) return ""

        val current = System.currentTimeMillis()
        if (mInitStartTime == 0L) mInitStartTime = current
        if (mInitLastTime == 0L) mInitLastTime = current
        val now = current - mInitStartTime
        val interval = current - mInitLastTime
        mInitLastTime = current

        return ((if (TextUtils.isEmpty(msg)) "" else " $msg")
                + " == TIME: " + timeFormat.format(Date(current))
                + " -- now: " + now / 1000 + "-秒-" + now % 1000
                + " >> interval: " + interval / 1000 + "-秒-" + interval % 1000)
    }

    private fun end(tag: String): String {
        try {
            return mShowTime(tag, "end >>> >>>")
        } finally {
            mInitStartTime = 0
            mInitLastTime = 0
        }
    }

    private fun mIsDebug(): Boolean {
        return isProgramDebug && isDebug || isForceDebug
    }

    private fun mIsPrintErrorTrace(): Boolean {
        return isProgramDebug && isPrintErrorTrace || isForcePrintErrorTrace
    }

    companion object {

        private val sCachedLog = ConcurrentHashMap<String, Reference<ALog?>?>()
        private const val SEPARATOR = ":#$#:"
        private var mProcessID: Int = 0

        val isProgramDebug: Boolean
            get() = Const.isDebug

        private val processID: Int
            get() {
                if (mProcessID <= 0) {
                    mProcessID = Process.myPid()
                }

                return mProcessID
            }

        /**
         * @param flags see [.isShowThreadTag], [.isDebug], [.isForceDebug], [.isPrintErrorTrace], [.isForcePrintErrorTrace], [.isShowTime], [.isShowMethodTrace]
         * @return
         */
        fun m(moduleName: String, vararg flags: Boolean): ALog {
            var fixModuleName = moduleName
            if (!isProgramDebug) return Singleton.INSTANCE
            var log: ALog?
            var refLog: Reference<ALog?>?
            fixModuleName = if (TextUtils.isEmpty(fixModuleName)) "" else fixModuleName.trim { it <= ' ' }
            val threadTag = Thread.currentThread().name + "(" + processID + ":" + Thread.currentThread().id + ")"

            val newModuleName = fixModuleName + SEPARATOR + threadTag
            refLog = sCachedLog[newModuleName]
            log = refLog?.get()

            if (log != null) {
                return log
            } else {

                var depth = 0
                if (!isMainThread) {
                    depth = 1
                    for (key in sCachedLog.keys) {

                        if (key.startsWith(fixModuleName + SEPARATOR)) {

                            val r: Reference<*>? = sCachedLog[key]
                            if (r?.get() != null && r !is SoftReference<*>) {
                                depth++
                            }
                        }
                    }
                }

                log = object : ALog(fixModuleName, threadTag, depth) {
                    override val isShowThreadTag: Boolean
                        get() = if (flags.isNotEmpty()) flags[0] else super.isShowThreadTag

                    override val isDebug: Boolean
                        get() = if (flags.size > 1) flags[1] else super.isDebug

                    override val isForceDebug: Boolean
                        get() = if (flags.size > 2) flags[2] else super.isForceDebug

                    override val isPrintErrorTrace: Boolean
                        get() = if (flags.size > 3) flags[3] else super.isPrintErrorTrace

                    override val isForcePrintErrorTrace: Boolean
                        get() = if (flags.size > 4) flags[4] else super.isForcePrintErrorTrace

                    override val isShowTime: Boolean
                        get() = if (flags.size > 5) flags[5] else super.isShowTime

                    override val isShowMethodTrace: Boolean
                        get() = if (flags.size > 6) flags[6] else super.isShowMethodTrace
                }

                refLog = if (depth == 0) {
                    SoftReference(log)
                } else {
                    WeakReference(log)
                }
                sCachedLog[newModuleName] = refLog
                return log
            }
        }

        private val isMainThread: Boolean
            get() = Looper.getMainLooper().thread.id == Thread.currentThread().id

        private fun toString(trace: StackTraceElement?): String {
            if (trace == null) return ""
            val buf = StringBuilder()

            buf.append(trace.className.substring(trace.className.lastIndexOf('.') + 1))
            buf.append('.')
            buf.append(trace.methodName)

            if (trace.isNativeMethod) {
                buf.append("(Native Method)")
            } else {
                val fName = trace.fileName

                if (fName == null) {
                    buf.append("(Unknown Source)")
                } else {
                    val lineNum = trace.lineNumber

                    buf.append('(')
                    buf.append(fName)
                    if (lineNum >= 0) {
                        buf.append(':')
                        buf.append(lineNum)
                    }
                    buf.append(')')
                }
            }
            return buf.toString()
        }
    }
}