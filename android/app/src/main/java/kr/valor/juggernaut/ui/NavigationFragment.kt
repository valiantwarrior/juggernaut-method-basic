package kr.valor.juggernaut.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

open class NavigationFragment : Fragment() {

    fun navigate(@IdRes resId: Int) {
        findNavController().navigate(resId)
    }

    fun navigate(navDirections: NavDirections) {
        findNavController().navigate(navDirections)
    }

}