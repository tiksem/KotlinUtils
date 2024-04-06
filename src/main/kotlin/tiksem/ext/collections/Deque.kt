package tiksem.ext.collections

fun <T> Collection<T>.toArrayDeque(): ArrayDeque<T> {
    return ArrayDeque(this)
}

fun <T> Iterable<T>.toArrayDeque(): ArrayDeque<T> {
    return ArrayDeque<T>().also {
        for (i in this) {
            it.add(i)
        }
    }
}