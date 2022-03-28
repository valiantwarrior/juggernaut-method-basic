package kr.valor.juggernaut.ui.home

class NavigationClickListener(private val clickListener: (Long) -> Unit) {
    fun onClick(sessionId: Long) = clickListener(sessionId)
}