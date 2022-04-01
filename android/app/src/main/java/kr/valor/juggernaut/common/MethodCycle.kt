package kr.valor.juggernaut.common

@JvmInline
value class MethodCycle(val value: Int) {
    companion object {
        const val INITIAL_VALUE = 1

        val INITIAL = MethodCycle(INITIAL_VALUE)

        operator fun MethodCycle.plus(other: Int): MethodCycle =
            MethodCycle(this.value + other)

        operator fun MethodCycle.minus(other: Int): MethodCycle =
            MethodCycle(this.value - other)
    }
}