package algebra.real

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class VectorTests {

    @Test
    fun `get from vector`() {
        val v = Vector(listOf(1.0, 2.0, 3.0))
        assertEquals(1.0, v[0])
        assertEquals(2.0, v[1])
        assertEquals(3.0, v[2])
    }

    @Test
    fun `scalar times vector`() {
        val v1 = Vector(listOf(1.0, 2.0, 3.0))
        val scaled = Vector(listOf(10.0, 20.0, 30.0))
        assertEquals(scaled, v1 * 10.0)
    }

    @Test
    fun `vector times scalar`() {
        val v1 = Vector(listOf(1.0, 2.0, 3.0))
        val scaled = Vector(listOf(10.0, 20.0, 30.0))
        assertEquals(scaled, 10.0 * v1)
    }

    @Test
    fun `vectors added`(){
        val v1 = Vector(listOf(1.0, 2.0, 3.0))
        val v2 = Vector(listOf(10.0, 20.0, 30.0))
        assertEquals(Vector(listOf(11.0, 22.0, 33.0)), v1+v2)

    }

    @Test
    fun `dot product`() {
        val v1 = Vector(listOf(1.0, 0.0, 0.0))
        val v2 = Vector(listOf(0.0, 1.0, 0.0))
        assertEquals(0.0, v1 dot v2)
    }

    @Test
    fun `string representation`() {
        val v1 = Vector(listOf(1.0, 2.0, 3.0, 4.0, 5.0))
        assertEquals("(1.0, 2.0, 3.0, 4.0, 5.0)", v1.toString())
    }

    @Test
    fun `exception - empty vector`() {
        try {
            Vector(emptyList())
            fail("IllegalArgumentException was expected.")
        } catch (exception: IllegalArgumentException) {
            // Good: exception was expected.
        }
    }

    @Test
    fun `exception - lengths do not match in dot product`() {
        try {
            Vector(listOf(1.0, 2.0)) dot Vector(listOf(1.0, 2.0, 3.0))
            fail("UnsupportedOperationException was expected.")
        } catch (exception: UnsupportedOperationException) {
            // Good: exception was expected.
        }
    }

    @Test
    fun `exception - get at too large index`() {
        try {
            Vector(listOf(1.0, 2.0))[2]
            fail("IndexOutOfBoundsException was expected.")
        } catch (exception: IndexOutOfBoundsException) {
            // Good: exception was expected.
        }
    }
}
