package kr.valor.juggernaut

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
fun runTestAndCleanup(body: TestScope.() -> Job) = runTest {
    var job: Job? = null
    try {
        job = body()
    } finally {
        job!!.cancel()
    }
}