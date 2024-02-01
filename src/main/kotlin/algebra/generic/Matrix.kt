package algebra.generic

import java.lang.StringBuilder


data class Matrix<T>(
    val vecEntries: List<Vector<T>>,
    var addition: (T, T) -> T,
    var multiplication: (T, T) -> T
) {

    init {
        if (this.vecEntries.isEmpty()) {
            throw IllegalArgumentException()
        }
    }

    private val numCols = this.vecEntries.size
    private val numRows = this.vecEntries[0].length

    operator fun get(indexRow: Int): Vector<T> {
        if (indexRow > this.numRows) {
            throw IndexOutOfBoundsException()
        } else {
            return this.vecEntries[indexRow]
        }
    }

    private fun getRow(indexRow: Int): Vector<T> {
        return this[indexRow]
    }

    private fun getColumn(indexCol: Int): Vector<T> {
        if (indexCol >= this.numCols) {
            throw IndexOutOfBoundsException()
        } else {
            return Vector(
                this.vecEntries.map { it[indexCol] }, addition, multiplication
            )
        }
    }

    operator fun get(indexRow: Int, indexCol: Int): T {
        if (indexCol > this.numCols || indexRow > this.numRows) {
            throw IndexOutOfBoundsException()
        } else {
            return this.vecEntries[indexRow][indexCol]
        }
    }

    operator fun plus(mat: Matrix<T>): Matrix<T> {
        if (this.numCols != mat.numCols || this.numRows != mat.numRows) {
            throw UnsupportedOperationException()
        } else {
            return Matrix(
                (0..<numRows).map { i -> this[i] + mat[i] },
                addition,
                multiplication
            )
        }
    }

    @JvmName("matrix")
    operator fun times(s: T): Matrix<T> {
        return Matrix(this.vecEntries.map { it * s }, addition, multiplication)
    }


    operator fun times(mat: Matrix<T>): Matrix<T> {
        if (this.numCols != mat.numRows) {
            throw UnsupportedOperationException()
        } else {
            return Matrix(
                this.vecEntries.map { v ->
                    Vector(
                        (0..<mat.numCols).map { i -> v dot mat.getColumn(i) },
                        addition,
                        multiplication
                    )
                }, addition, multiplication
            )
        }
    }

    @JvmName("matrices")
    operator fun times(nestedMat: Matrix<Matrix<T>>): Matrix<Matrix<T>> {
        if (this.numCols != nestedMat[0, 0].numRows) {
            throw UnsupportedOperationException()
        } else {
            val nestedNew = (0..<nestedMat.numRows).map { i ->
                (0..<nestedMat.numCols).map { j -> this * nestedMat[i, j] }
            }

            return makeMatrix(nestedNew,
                { x, y -> x.plus(y) },
                { _, y -> times(y) })
        }
    }


    override fun toString(): String {
        val builder = StringBuilder()
        this.vecEntries.forEachIndexed { indexRow, _ ->
            val rows = this.getRow(indexRow).entries
            builder.append("[ ")
            rows.forEachIndexed { indexCol, entry ->
                val maxLen = this.getColumn(indexCol).getMaxLen()
                val diff = maxLen - entry.toString().length
                val entryStr = (" ".repeat(diff)) + entry.toString() + " "
                builder.append(entryStr)
            }
            if (indexRow != this.numRows - 1) {
                builder.append("]\n")
            } else {
                builder.append("]")
            }
        }
        return builder.toString().trimIndent()
    }
}

fun <T> makeMatrix(
    lists: List<List<T>>,
    plus: (T, T) -> T, times: (T, T) -> T
): Matrix<T> {
    return Matrix(lists.map { vec -> Vector(vec, plus, times) }, plus, times)
}

operator fun Any.times(mat: Matrix<Any>): Matrix<Any> =
    Matrix(mat.vecEntries.map { it * this }, mat.addition, mat.multiplication)