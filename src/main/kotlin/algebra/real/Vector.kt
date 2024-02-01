package algebra.real

import java.lang.StringBuilder

data class Vector(val doubles: List<Double>) {
    init {
        if (doubles.isEmpty()) {
            throw IllegalArgumentException()
        }
    }

    val length = doubles.size

    operator fun get(index: Int): Double {
        if (index >= this.length) {
            throw IndexOutOfBoundsException()
        } else {
            return this.doubles[index]
        }
    }

    operator fun plus(u: Vector): Vector {
        if (u.length != this.length) {
            throw UnsupportedOperationException()
        } else {
            return Vector((0..<length).map { i -> u[i] + this[i] })
        }
    }

    operator fun times(x: Double): Vector {
        return Vector(this.doubles.map { it * x })
    }

    infix fun dot(u: Vector): Double {
        if (this.length != u.length) {
            throw UnsupportedOperationException()
        } else {
            return (0..<length).sumOf { i -> u[i] * this[i] }
        }
    }

    override fun toString(): String {
        val builder = StringBuilder() // ktlint-disable curly-spacing

        this.doubles.forEachIndexed { index, d ->
            when (index) {
                0 -> {
                    builder.append("($d, ")
                }

                (length - 1) -> {
                    builder.append("$d)")
                }

                else -> {
                    builder.append("$d, ")
                }
            }
        }

        return builder.toString()
    }

    fun getMaxLen(): Int {
        return this.doubles.maxOf { i -> i.toString().length }
    }
}

operator fun Double.times(v: Vector): Vector =
    Vector(v.doubles.map { it * this })
