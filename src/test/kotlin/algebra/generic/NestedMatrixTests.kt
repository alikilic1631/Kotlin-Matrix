package algebra.generic

import kotlin.test.Test
import kotlin.test.assertEquals

class NestedMatrixTests {

    private val innerFactory = AlgebraFactory(Int::plus, Int::times)

    private val outerFactory = AlgebraFactory(Matrix<Int>::plus, Matrix<Int>::times)

    @Test
    fun `add nested matrices`() {
        val allZeroes2x2 = innerFactory.makeMatrix(
            listOf(
                listOf(0, 0),
                listOf(0, 0),
            ),
        )

        val allOnes2x2 = innerFactory.makeMatrix(
            listOf(
                listOf(1, 1),
                listOf(1, 1),
            ),
        )

        val m1 = outerFactory.makeMatrix(
            listOf(
                listOf(allZeroes2x2, allOnes2x2),
                listOf(allOnes2x2, allZeroes2x2),
            ),
        )
        val m2 = outerFactory.makeMatrix(
            listOf(
                listOf(allOnes2x2, allZeroes2x2),
                listOf(allZeroes2x2, allOnes2x2),
            ),
        )
        val expectedSum = outerFactory.makeMatrix(
            listOf(
                listOf(allOnes2x2, allOnes2x2),
                listOf(allOnes2x2, allOnes2x2),
            ),
        )
        assertEquals(expectedSum, m1 + m2)
    }

    private fun make2x2Diagonal(value: Int): Matrix<Int> =
        innerFactory.makeMatrix(
            listOf(
                listOf(value, 0),
                listOf(0, value),
            ),
        )

    @Test
    fun `multiply nested matrices`() {
        val intMatrix1 = innerFactory.makeMatrix(
            listOf(
                listOf(1, 2),
                listOf(3, 4),
            ),
        )

        val intMatrix2 = innerFactory.makeMatrix(
            listOf(
                listOf(5, 6),
                listOf(7, 8),
            ),
        )

        val intMatrixProduct1 = innerFactory.makeMatrix(
            listOf(
                listOf(19, 22),
                listOf(43, 50),
            ),
        )

        val intMatrixProduct2 = innerFactory.makeMatrix(
            listOf(
                listOf(23, 34),
                listOf(31, 46),
            ),
        )

        assertEquals(intMatrixProduct1, intMatrix1 * intMatrix2)

        assertEquals(intMatrixProduct2, intMatrix2 * intMatrix1)

        val nestedMatrix1 = outerFactory.makeMatrix(
            listOf(
                listOf(make2x2Diagonal(1), make2x2Diagonal(2)),
                listOf(make2x2Diagonal(3), make2x2Diagonal(4)),
            ),
        )

        val nestedMatrix2 = outerFactory.makeMatrix(
            listOf(
                listOf(make2x2Diagonal(5), make2x2Diagonal(6)),
                listOf(make2x2Diagonal(7), make2x2Diagonal(8)),
            ),
        )

        val nestedMatrixProduct1 = outerFactory.makeMatrix(
            listOf(
                listOf(make2x2Diagonal(19), make2x2Diagonal(22)),
                listOf(make2x2Diagonal(43), make2x2Diagonal(50)),
            ),
        )

        val nestedMatrixProduct2 = outerFactory.makeMatrix(
            listOf(
                listOf(make2x2Diagonal(23), make2x2Diagonal(34)),
                listOf(make2x2Diagonal(31), make2x2Diagonal(46)),
            ),
        )

        assertEquals(nestedMatrixProduct1, nestedMatrix1 * nestedMatrix2)

        assertEquals(nestedMatrixProduct2, nestedMatrix2 * nestedMatrix1)
    }

    @Test
    fun `left and right multiplication by matrix scalar`() {
        val m1 = innerFactory.makeMatrix(
            listOf(
                listOf(1, 2),
                listOf(1, 2),
            ),
        )

        val m2 = innerFactory.makeMatrix(
            listOf(
                listOf(1, 3),
                listOf(1, 3),
            ),
        )

        val m1xm1 = innerFactory.makeMatrix(
            listOf(
                listOf(3, 6),
                listOf(3, 6),
            ),
        )

        val m2xm2 = innerFactory.makeMatrix(
            listOf(
                listOf(4, 12),
                listOf(4, 12),
            ),
        )

        val m1xm2 = innerFactory.makeMatrix(
            listOf(
                listOf(3, 9),
                listOf(3, 9),
            ),
        )

        val m2xm1 = innerFactory.makeMatrix(
            listOf(
                listOf(4, 8),
                listOf(4, 8),
            ),
        )

        assertEquals(m1xm1, m1 * m1)
        assertEquals(m2xm2, m2 * m2)
        assertEquals(m1xm2, m1 * m2)
        assertEquals(m2xm1, m2 * m1)

        val nestedMatrix = outerFactory.makeMatrix(
            listOf(
                listOf(m1, m2, m1, m2),
                listOf(m2, m1, m2, m1),
            ),
        )

        val nestedMatrixLeftScaledByM1 = outerFactory.makeMatrix(
            listOf(
                listOf(m1xm1, m1xm2, m1xm1, m1xm2),
                listOf(m1xm2, m1xm1, m1xm2, m1xm1),
            ),
        )

        val nestedMatrixRightScaledByM1 = outerFactory.makeMatrix(
            listOf(
                listOf(m1xm1, m2xm1, m1xm1, m2xm1),
                listOf(m2xm1, m1xm1, m2xm1, m1xm1),
            ),
        )

        val nestedMatrixLeftScaledByM2 = outerFactory.makeMatrix(
            listOf(
                listOf(m2xm1, m2xm2, m2xm1, m2xm2),
                listOf(m2xm2, m2xm1, m2xm2, m2xm1),
            ),
        )

        val nestedMatrixRightScaledByM2 = outerFactory.makeMatrix(
            listOf(
                listOf(m1xm2, m2xm2, m1xm2, m2xm2),
                listOf(m2xm2, m1xm2, m2xm2, m1xm2),
            ),
        )

        assertEquals(nestedMatrixLeftScaledByM1, m1 * nestedMatrix)
        assertEquals(nestedMatrixRightScaledByM1, nestedMatrix * m1)
        assertEquals(nestedMatrixLeftScaledByM2, m2 * nestedMatrix)
        assertEquals(nestedMatrixRightScaledByM2, nestedMatrix * m2)
    }
}
