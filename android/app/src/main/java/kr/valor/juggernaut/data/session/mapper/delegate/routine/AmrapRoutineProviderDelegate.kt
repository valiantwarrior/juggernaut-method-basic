package kr.valor.juggernaut.data.session.mapper.delegate.routine

import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.common.converter.WeightUnitTransformer
import kr.valor.juggernaut.data.common.di.KgWeightUnit
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.RoutineIntensitySource
import kr.valor.juggernaut.data.session.mapper.di.InMemorySource
import kr.valor.juggernaut.domain.session.model.Routine
import kr.valor.juggernaut.domain.session.model.SessionProgression
import kr.valor.juggernaut.domain.session.model.toRoutineModel
import javax.inject.Inject

class AmrapRoutineProviderDelegate @Inject constructor(
    @InMemorySource private val routineIntensitySource: RoutineIntensitySource<MicroCycle, Phase>,
    @KgWeightUnit private val transformer: WeightUnitTransformer // run time injection (maybe)
): RoutineProviderDelegate<SessionProgression, Routine?> {

    override fun provideSessionRoutine(progression: SessionProgression, tmWeights: Int, actualRepetitions: Int?): Routine? {
        return if (progression.isAmrapSession) {
            val amrapRoutineIntensity =
                routineIntensitySource.provideRoutineIntensityMap(progression.microCycle)[progression.phase]!!.last()

            amrapRoutineIntensity.toRoutineModel(tmWeights, actualRepetitions, transformer::transform)
        } else {
            null
        }
    }

}