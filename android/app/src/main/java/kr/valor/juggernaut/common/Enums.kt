package kr.valor.juggernaut.common

sealed interface ProgressionElement

@JvmInline
value class MethodCycle(val value: Int): ProgressionElement

enum class Phase: ProgressionElement {
    REP10, REP8, REP5, REP3;
}

enum class MicroCycle: ProgressionElement {
    ACCUMULATION, INTENSIFICATION, REALIZATION, DELOAD
}

enum class LiftCategory: ProgressionElement {
    BENCH_PRESS, DEAD_LIFT, SQUAT, OVERHEAD_PRESS
}