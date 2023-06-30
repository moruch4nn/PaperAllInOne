package dev.mr3n.paperallinone

import kotlin.random.Random

fun <T> Map<T, Int>.biasedRandom(random: Random): T {
    val sum = this.values.sum()
    val v = random.nextInt(1,sum)
    var count = 0
    this.forEach { (value, bias) ->
        if(v in count..count+bias) {
            return value
        }
        count+=bias
    }
    return this.keys.random(random)
}