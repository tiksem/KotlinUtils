package tiksem.ext.random

import kotlin.random.Random

fun Random.roll(chance: Double): Boolean {
    val aChance = chance.coerceIn(0.0, 1.0)
    return nextDouble() < aChance
}