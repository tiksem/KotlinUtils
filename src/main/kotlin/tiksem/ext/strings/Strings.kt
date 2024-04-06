package tiksem.ext.strings

fun String.quote(char: Char): String {
    return "$char$this$char"
}

fun String.isQuoted(char: Char): Boolean {
    return length >= 2 && first() == char && last() == char
}

fun String.unquote(char: Char): String {
    return if (isQuoted(char)) {
        substring(1, length - 1)
    } else {
        this
    }
}

fun String.removeSpaces(): String {
    return filter {
        !it.isWhitespace()
    }
}

fun String.splitToLongArray(delimiter: Char): LongArray {
    if (isEmpty()) {
        return longArrayOf()
    }

    val s = split(delimiter)

    return LongArray(s.size) {
        s[it].toLong()
    }
}