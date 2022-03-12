package kr.valor.juggernaut.data.common.converter

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class KgWeightUnitConversionDelegateTest {

    private lateinit var target: WeightUnitConversionDelegate

    @Before
    fun `init`() {
        target = KgWeightUnitConversionDelegate
    }

    @Test
    fun `Case - integer part is odd validate`() {
        var input = 63.1
        var expected = 64
        var actual = target.convert(input)

        assertThat(actual, `is`(expected))

        input = 63.0
        expected = 64
        actual = target.convert(input)

        assertThat(actual, `is`(expected))
    }

    @Test
    fun `Case - integer part is even validate`() {
        var input = 64.4
        var expected = 64
        var actual = target.convert(input)

        assertThat(actual, `is`(expected))

        input = 64.0
        expected = 64
        actual = target.convert(input)

        assertThat(actual, `is`(expected))
    }
}