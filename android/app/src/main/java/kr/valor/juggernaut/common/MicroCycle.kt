package kr.valor.juggernaut.common

enum class MicroCycle(val abbreviatedName: String) {
    ACCUMULATION("A"),
    INTENSIFICATION("I"),
    REALIZATION("R"),
    DELOAD("D");

    companion object {
        const val  TOTAL_MICROCYCLE_COUNT = 4
        val INITIAL = ACCUMULATION
        val FINAL = DELOAD
    }
}