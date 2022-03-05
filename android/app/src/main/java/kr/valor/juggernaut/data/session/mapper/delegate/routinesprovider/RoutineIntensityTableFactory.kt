package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider

import kr.valor.juggernaut.common.Phase

typealias PhaseRoutineIntensityItem = Pair<Int, Double>
typealias PhaseEntireRoutineIntensityTable = Map<Phase, List<PhaseRoutineIntensityItem>>

interface RoutineIntensityTableFactory {
    val entireRoutineIntensityTable: PhaseEntireRoutineIntensityTable
}