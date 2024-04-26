package tiksem.ext.random

import kotlin.random.Random

fun Random.roll(chance: Double): Boolean {
    val aChance = chance.coerceIn(0.0, 1.0)
    return nextDouble() < aChance
}

fun <T> Random.getRandomObject(scoreMap: Map<T, Long>): T? {
    if (scoreMap.isEmpty()) {
        return null
    }

    // Calculate the total sum of all scores
    val totalScore = scoreMap.values.sum()
    if (totalScore == 0L) {
        return null
    }

    // Generate a random number between 0 and totalScore - 1
    val randomScore = Random.nextLong(totalScore)

    // Iterate over the map to find the corresponding object
    var cumulativeScore = 0L
    for ((obj, score) in scoreMap) {
        cumulativeScore += score
        if (randomScore < cumulativeScore) {
            return obj
        }
    }
    return null // Should never reach here unless there's a calculation error
}

