package kr.valor.juggernaut.ui.onboarding.form

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class FormRepositoryTest {

    private lateinit var repository: FormRepository

    @Before
    fun `init repo` () {
        repository = FormRepository()
    }

    @After
    fun `nuke repo` () {
        repository.clear()
    }

    @Test
    fun `increase and decrease repetitions works as expected`() {
        val pagePosition = FormPagePosition.BENCHPRESS
        repository.increaseRepetition(pagePosition)

        assertThat(`get page data`(pagePosition).inputRepetitions, `is`(1))

        repository.decreaseRepetition(pagePosition)
        assertThat(`get page data`(pagePosition).inputRepetitions, `is`(0))

        repository.decreaseRepetition(pagePosition)
        assertThat(`get page data`(pagePosition).inputRepetitions, `is`(0))
    }

    @Test
    fun `increase and decrease weights works as expected`() {
        val pagePosition = FormPagePosition.BENCHPRESS
        repository.pushPlates(pagePosition, 20.0)

        assertThat(`get page data`(pagePosition).inputWeights, `is`(60))
        repository.popPlates(pagePosition)
        assertThat(`get page data`(pagePosition).inputWeights, `is`(20))
    }



    private fun `get page data`(pagePosition: FormPagePosition) =
        repository.formPageDataMap[pagePosition]!!.value
}