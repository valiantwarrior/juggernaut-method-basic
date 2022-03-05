package kr.valor.juggernaut.common

enum class MicroCycle {
    ACCUMULATION, INTENSIFICATION, REALIZATION, DELOAD
}

enum class Phase {
    REP10, REP8, REP5, REP3;
}

enum class LiftCategory {
    BENCH_PRESS, DEAD_LIFT, SQUAT, OVERHEAD_PRESS
}

inline fun <V> createPhaseBasedKeyMapAndReturn(create: (Phase) -> V): Map<Phase, V> {
    val tempMap = mutableMapOf<Phase, V>()
    Phase.values().forEach { key ->
        val value = create(key)
        tempMap[key] = value
    }
    return tempMap.toMap()
}