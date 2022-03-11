package kr.valor.juggernaut.domain.session.usecases

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kr.valor.juggernaut.TestServiceLocator
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.user.repository.UserRepository
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class SynchronizeSessionUseCaseImplTest {
    private lateinit var useCase: SynchronizeSessionUseCase
    private lateinit var sessionRepository: SessionRepository
    private lateinit var userRepository: UserRepository

    @Before
    fun `init`() {
        sessionRepository = TestServiceLocator.provideSessionRepository()
        userRepository = TestServiceLocator.provideUserRepository()

        useCase = SynchronizeSessionUseCaseImpl(sessionRepository, userRepository)
    }

    @Test
    fun `synchronize session use case works as expected`() = runTest {
        sessionRepository.getAllSessions().first().let {
            assertThat(it.size, `is`(0))
        }

        useCase()

        sessionRepository.getAllSessions().first().let {
            assertThat(it.size, `is`(1))
        }

        sessionRepository.getSession().first().let {
            assertThat(it, instanceOf(Session::class.java))
        }
    }
}