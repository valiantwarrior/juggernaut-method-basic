package kr.valor.juggernaut.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

open class ViewHolderDataBindingFactory {

    inline fun <reified VB: ViewDataBinding> provideDataBinding(parent: ViewGroup, layoutId: Int): VB {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<VB>(layoutInflater, layoutId, parent, false)

        if (binding is VB) {
            return binding
        } else {
            throw IllegalArgumentException("Illegal layoutId for ${VB::class.java}")
        }
    }

}