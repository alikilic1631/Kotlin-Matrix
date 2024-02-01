package algebra.generic

import org.junit.Test
import kotlin.test.assertEquals

class StringMatrixes {

    @Test
    fun `multiply string matrices`() {
        val stringAlgebraFactory = AlgebraFactory<String>(
            plus = { a, b -> a + "+" + b },
            times = { a, b -> a + "*" + b },
        )

        val m1 = stringAlgebraFactory.makeMatrix(
            listOf(
                listOf("ant", "bug", "croc"),
                listOf("deer", "elephant", "frog"),
            ),
        )

        val m2 = stringAlgebraFactory.makeMatrix(
            listOf(
                listOf("wasp", "beetle"),
                listOf("goblin", "midge"),
                listOf("mite", "kangaroo"),
            ),
        )

        val product = stringAlgebraFactory.makeMatrix(
            listOf(
                listOf("ant*wasp+bug*goblin+croc*mite", "ant*beetle+bug*midge+croc*kangaroo"),
                listOf("deer*wasp+elephant*goblin+frog*mite", "deer*beetle+elephant*midge+frog*kangaroo"),
            ),
        )

        assertEquals(product, m1 * m2)

        val expectedString =
            """
                [       ant*wasp+bug*goblin+croc*mite       ant*beetle+bug*midge+croc*kangaroo ]
                [ deer*wasp+elephant*goblin+frog*mite deer*beetle+elephant*midge+frog*kangaroo ]
            """.trimIndent()

        assertEquals(expectedString, product.toString())
    }
}
