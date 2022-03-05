package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.deload

import kr.valor.juggernaut.data.session.mapper.delegate.property.DefaultPropertyMediateDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.PhaseEntireRoutineIntensityTable
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.`validate routines that were generated by delegate`
import org.junit.Before
import org.junit.Test


class BasicDeloadRoutinesProviderDelegateTest {

    private lateinit var targetProviderDelegate: BasicDeloadRoutinesProviderDelegate
    private lateinit var table: PhaseEntireRoutineIntensityTable
    private val action = DefaultPropertyMediateDelegate::mediate

    @Before
    fun `init`() {
        targetProviderDelegate =
            BasicDeloadRoutinesProviderDelegate(
                DefaultPropertyMediateDelegate
            )
        table = BasicDeloadRoutinesProviderDelegate
            .entireRoutineIntensityTable
    }

    @Test
    fun `BasicDeloadRoutinesProviderDelegate generates routines as expected`() {
        targetProviderDelegate.`validate routines that were generated by delegate`(table, action)
    }

}