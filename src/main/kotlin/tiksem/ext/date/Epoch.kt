package tiksem.ext.date

import java.util.*

fun Int.yearsToEpoch(): Long {
    return Calendar.getInstance().also {
        it.set(this, 0, 0)
    }.timeInMillis
}

fun UInt.yearsToEpoch(): Long {
    return toInt().yearsToEpoch()
}

object Epoch {
    fun fromDate(year: Int, month: Int, day: Int): Long {
        return Calendar.getInstance().also {
            it.set(year, month, day, 0, 0, 0)
        }.timeInMillis.let {
            it - it % 1000
        }
    }

    fun modifiedYearOfCurrent(diff: Int): Long {
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, get(Calendar.YEAR) + diff)
        }.timeInMillis
    }
}

fun Long.getAge(): Int {
    val c = Calendar.getInstance().also {
        it.timeInMillis = this
    }
    val now = Calendar.getInstance()
    val nowYear = now.get(Calendar.YEAR)
    var result = nowYear - c.get(Calendar.YEAR)
    c.set(Calendar.YEAR, nowYear)
    if (c.get(Calendar.DAY_OF_YEAR) > now.get(Calendar.DAY_OF_YEAR)) {
        result--
    }

    return result
}