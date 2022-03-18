package kr.valor.juggernaut

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
fun TestScope.runTestAndCleanup(clean: suspend () -> Unit, body: suspend () -> Unit) = runTest {
    try {
        body()
    } finally {
        clean()
    }
}