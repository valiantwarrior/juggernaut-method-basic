package kr.valor.juggernaut.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun <T> Fragment.observeFlowEvent(event: Flow<T>, crossinline consume: (T) -> Unit): Job =
    viewLifecycleOwner.lifecycleScope.launch {
        event.flowWithLifecycle(lifecycle = viewLifecycleOwner.lifecycle, minActiveState = Lifecycle.State.STARTED)
            .collect { event: T ->
                consume(event)
            }
    }

inline fun <T> AppCompatActivity.observeFlowEvent(event: Flow<T>, crossinline consume: (T) -> Unit): Job =
    lifecycleScope.launch {
        event.flowWithLifecycle(lifecycle)
            .collect { event: T ->
                consume(event)
            }
    }