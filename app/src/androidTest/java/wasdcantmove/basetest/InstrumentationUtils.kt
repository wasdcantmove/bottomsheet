@file:JvmName("InstrumentationUtils")
package wasdcantmove.basetest

import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.EventCondition
import android.support.test.uiautomator.SearchCondition
import android.support.test.uiautomator.UiDevice

fun <R> UiDevice.wait(condition: EventCondition<R>, timeout:Long = 2000): R? =
        this.performActionAndWait({}, condition, timeout)

fun <R> UiDevice.wait(condition: SearchCondition<R>, timeout: Long = 2000): R? =
        this.wait(condition, timeout)

val uiDevice:UiDevice
        get()=UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())