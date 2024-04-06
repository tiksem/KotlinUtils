package tiksem.ext.coroutines

import kotlinx.coroutines.channels.Channel

suspend fun <E> Channel<E>.readAll(): List<E> {
    return arrayListOf<E>().also {
        while (!isEmpty) {
            it.add(receive())
        }
    }
}