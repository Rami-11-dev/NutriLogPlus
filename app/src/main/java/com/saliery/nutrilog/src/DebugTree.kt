package com.saliery.nutrilog.src

import timber.log.Timber

class DebugTree : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String? {
        return String.format(
            "\"[%s:%s] %s\"",
            element.fileName,
            element.lineNumber,
            element.methodName
        )
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, tag, message, t)
    }
}