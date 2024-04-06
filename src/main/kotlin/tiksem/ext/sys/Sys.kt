package tiksem.ext.sys

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader

data class CommandResult(
    val output: String,
    val code: Int
)

object Sys {
    suspend fun executeCommand(command: String): CommandResult = withContext(Dispatchers.IO) {
        val p = Runtime.getRuntime().exec(command)
        handleCommandResult(p)
    }

    suspend fun executeCommand(command: Array<String>): CommandResult = withContext(Dispatchers.IO) {
        val p = Runtime.getRuntime().exec(command)
        handleCommandResult(p)
    }

    private fun handleCommandResult(p: Process): CommandResult {
        val code = p.waitFor()

        val streams = if (code != 0) {
            arrayOf(p.errorStream, p.inputStream)
        } else {
            arrayOf(p.inputStream, p.errorStream)
        }

        return try {
            var text = ""
            for (stream in streams) {
                text = BufferedReader(stream.reader()).readText()
                if (text.isNotEmpty()) {
                    break
                }
            }

            CommandResult(
                output = text,
                code = code
            )
        } finally {
            streams.forEach {
                it.close()
            }
        }
    }
}