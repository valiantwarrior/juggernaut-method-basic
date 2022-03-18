package kr.valor.juggernaut.data.common.converter

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class KgWeightUnitTransformerTest {

    private lateinit var target: WeightUnitTransformer

    @Before
    fun `init`() {
        target = KgWeightUnitTransformer()
    }

    @Test
    fun `Case - integer part is odd validate 0`() {
        val input = 61.1
        val expected = 62
        val actual = target.transform(input)

        assertThat(actual, `is`(expected))
    }

    @Test
    fun `Case - integer part is odd validate 1`() {
        val input = 69.1
        val expected = 70
        val actual = target.transform(input)

        assertThat(actual, `is`(expected))
    }

    @Test
    fun `Case - integer part is odd validate 2`() {
        val input = 65.1
        val expected = 66
        val actual = target.transform(input)

        assertThat(actual, `is`(expected))
    }



    @Test
    fun `Case - integer part is even validate 0`() {
        val input = 62.1
        val expected = 64
        val actual = target.transform(input)

        assertThat(actual, `is`(expected))
    }

    @Test
    fun `Case - integer part is even validate 1`() {
        val input = 62.0
        val expected = 62
        val actual = target.transform(input)

        assertThat(actual, `is`(expected))
    }

    @Test
    fun `Case - integer part is even validate 2`() {
        val input = 62.5
        val expected = 64
        val actual = target.transform(input)

        assertThat(actual, `is`(expected))
    }

    @Test
    fun `Case - integer part is even validate 3`() {
        val input = 60.5
        val expected = 62
        val actual = target.transform(input)

        assertThat(actual, `is`(expected))
    }

    @Test
    fun `Case - integer part is even validate 4`() {
        val input = 60.0
        val expected = 60
        val actual = target.transform(input)

        assertThat(actual, `is`(expected))
    }

}