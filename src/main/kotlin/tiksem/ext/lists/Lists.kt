package tiksem.ext.lists

inline fun <T> List<T>.contains(predicate: (T) -> Boolean): Boolean {
    return find(predicate) != null
}

inline fun <T> List<T>.mapIf(value: Boolean, mapper: (T) -> T): List<T> {
    return if (value) {
        map(mapper)
    } else {
        this
    }
}

fun <T> List<T>.removed(value: T): List<T> {
    return this.filter {
        it != value
    }
}

fun <T> List<T>.withoutLast(): List<T> {
    return object : AbstractList<T>() {
        override val size: Int
            get() = this@withoutLast.size - 1

        override fun get(index: Int): T {
            return this@withoutLast[index]
        }

    }
}

fun <T> List<T>.replaced(value: T, newValue: T): List<T> {
    val index = indexOf(value)
    return toMutableList().also {
        it[index] = newValue
    }
}

fun <T> List<T>.cutIfLongerThan(maxSize: Int): List<T> {
    return if (size > maxSize) {
        subList(0, maxSize)
    } else {
        this
    }
}