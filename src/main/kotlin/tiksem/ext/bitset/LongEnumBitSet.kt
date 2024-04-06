package tiksem.ext.bitset

inline fun Long.forEachSetBit(action: (Int) -> Unit) {
    for (bitIndex in 0 until 64) {
        if (this and (1L shl bitIndex) != 0L) {
            action(bitIndex)
        }
    }
}

class LongEnumBitSet<E : Enum<E>>(enumClass: Class<E>) {
    val enumConstants: Array<E> = enumClass.enumConstants
        ?: throw IllegalArgumentException("Class must be an enum type")

    init {
        if (enumConstants.size > 64) {
            throw IllegalArgumentException("LongEnumBitSet can contain maximum 64 items")
        }
    }

    var bits: Long = 0L
        private set

    private fun getIndex(enumConstant: E): Int = enumConstant.ordinal

    fun add(element: E) {
        bits = bits or (1L shl getIndex(element))
    }

    fun remove(element: E) {
        bits = bits and (1L shl getIndex(element)).inv()
    }

    fun toggle(element: E) {
        bits = bits xor (1L shl getIndex(element))
    }

    fun toList(): List<E> {
        return enumConstants.filter {
            contains(it)
        }
    }

    inline fun forEach(action: (E) -> Unit) {
        bits.forEachSetBit {
            action(enumConstants[it])
        }
    }

    val size: Int
        get() = bits.countOneBits()

    fun contains(element: E): Boolean = bits and (1L shl getIndex(element)) != 0L

    fun containsAll(elements: Array<E>): Boolean {
        return elements.all {
            contains(it)
        }
    }

    fun clear() {
        bits = 0L
    }

    override fun toString(): String {
        return enumConstants.filter { contains(it) }.joinToString(",")
    }
}

fun <E : Enum<E>> bitSetOf(value: E, vararg values: E): LongEnumBitSet<E> {
    return LongEnumBitSet(value.javaClass).apply {
        add(value)
        values.forEach {
            add(it)
        }
    }
}

fun <E : Enum<E>> emptyBitSet(enumClass: Class<E>): LongEnumBitSet<E> {
    return LongEnumBitSet(enumClass)
}

fun <E : Enum<E>> fullBitSet(enumClass: Class<E>): LongEnumBitSet<E> {
    return LongEnumBitSet(enumClass).apply {
        enumClass.enumConstants.forEach {
            add(it)
        }
    }
}