package algebra.generic

import java.lang.StringBuilder

data class Vector<T>(
    val entries: List<T>,
    var addition: (T, T) -> T,
    var multiplication: (T, T) -> T
) {
    init {
        if (entries.isEmpty()) {
            throw IllegalArgumentException()
        }
    }

    val length = entries.size

    operator fun get(index: Int): T {
        if (index >= this.length) {
            throw IndexOutOfBoundsException()
        } else {
            return this.entries[index]
        }
    }

    operator fun plus(u: Vector<T>): Vector<T> {
        if (u.length != this.length) {
            throw UnsupportedOperationException()
        } else {
            return Vector(
                (0..<length).map { i -> addition(u[i], this[i]) },
                addition,
                multiplication
            )
        }
    }

    operator fun times(x: T): Vector<T> {
        return Vector(
            this.entries.map { multiplication(it, x) }, addition, multiplication
        )
    }

    infix fun dot(u: Vector<T>): T {
        if (this.length != u.length) {
            throw UnsupportedOperationException()
        } else {
            return (0..<length).map { i -> multiplication(u[i], this[i]) }
                .reduceRight(addition)
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()

        this.entries.forEachIndexed { index, d ->
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
        return this.entries.maxOf { i -> i.toString().length }
    }
}

operator fun Any.times(v: Vector<Any>): Vector<Any> = Vector(
    v.entries.map { v.multiplication(it, this) }, v.addition, v.multiplication
)