package algebra.real
import java.lang.StringBuilder

data class Matrix(val vectors: List<Vector>) {

    init {
        if (this.vectors.isEmpty()) {
            throw IllegalArgumentException()
        }
    }

    private val numCols = this.vectors.size
    private val numRows = this.vectors[0].length

    operator fun get(indexRow: Int): Vector {
        if (indexRow > this.numRows) {
            throw IndexOutOfBoundsException()
        } else {
            return this.vectors[indexRow]
        }
    }

    fun getRow(indexRow: Int): Vector {
        return this[indexRow]
    }

    fun getColumn(indexCol: Int): Vector {
        if (indexCol > this.numCols) {
            throw IndexOutOfBoundsException()
        } else {
            return Vector(this.vectors.map { it[indexCol] })
        }
    }

    operator fun get(indexRow: Int, indexCol: Int): Double {
        if (indexCol > this.numCols || indexRow > this.numRows) {
            throw IndexOutOfBoundsException()
        } else {
            return this.vectors[indexRow][indexCol]
        }
    }

    operator fun plus(mat: Matrix): Matrix {
        if (this.numCols != mat.numCols || this.numRows != mat.numRows) {
            throw UnsupportedOperationException()
        } else {
            return Matrix((0..<numRows).map { i -> this[i] + mat[i] })
        }
    }

    operator fun times(s: Double): Matrix {
        return Matrix(this.vectors.map { it * s })
    }

    operator fun times(mat: Matrix): Matrix {
        if (this.numCols != mat.numRows) {
            throw UnsupportedOperationException()
        } else {
            return Matrix(
                this.vectors.map { v ->
                    Vector(
                        (0..<mat.numCols).map { i -> v dot mat.getColumn(i) },
                    )
                },
            )
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        this.vectors.forEachIndexed { indexRow, _ ->
            val rows = this.getRow(indexRow).doubles
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

operator fun Double.times(mat: Matrix): Matrix =
    Matrix(mat.vectors.map { it * this })


