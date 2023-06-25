package dev.mr3n.paperallinone

import kotlin.random.Random

fun <T> Map<Int, T>.biasedRandom(random: Random): T {
    val sum = this.keys.sum()
    val v = random.nextInt(1,sum)
    var count = 0
    this.forEach { (bias, value) ->
        if(v in count..count+bias) {
            return value
        }
        count+=bias
    }
    return this.values.random(random)
}