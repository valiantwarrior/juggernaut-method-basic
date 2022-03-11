package kr.valor.juggernaut.data

import kr.valor.juggernaut.TestServiceLocator
import kr.valor.juggernaut.domain.user.repository.UserRepository
import org.junit.Before


class DefaultUserRepositoryTest {
    private lateinit var repository: UserRepository

    @Before
    fun `init`() {
        repository = TestServiceLocator.provideUserRepository()
    }


}