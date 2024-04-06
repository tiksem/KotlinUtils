package tiksem.ext.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

object Coroutines {
    suspend fun <T> waitForOneTaskInTheListToComplete(
        tasks: List<suspend () -> T>
    ): T {
        val resultChannel = Channel<T>(
            capacity = 1
        )
        coroutineScope {
            val jobs = mutableListOf<Job>()
            tasks.mapTo(jobs) { task ->
                launch {
                    try {
                        task().also { value ->
                            resultChannel.trySend(value)
                            jobs.forEach {
                                it.cancel()
                            }
                        }
                    } catch (_: CancellationException) {
                    }
                }
            }
            jobs.forEach {
                it.join()
            }
        }
        return resultChannel.receive()
    }

    suspend fun runSeveralTasksInParallelAndWait(tasks: List<suspend () -> Unit>) {
        coroutineScope {
            val jobs = mutableListOf<Job>()
            tasks.mapTo(jobs) { task ->
                launch {
                    task()
                }
            }
            jobs.forEach {
                it.join()
            }
        }
    }
}