package kr.valor.juggernaut.domain.session.usecase

//class SynchronizeSessionUseCaseImplTest {
//    private lateinit var useCase: SynchronizeSessionUseCase
//    private lateinit var sessionRepository: SessionRepository
//    private lateinit var userRepository: UserRepository
//
//    @Before
//    fun `init`() {
//        sessionRepository = TestServiceLocator.sessionRepository
//        userRepository = TestServiceLocator.userRepository
//
//        useCase = TestServiceLocator.provideSynchronizeSessionUseCase()
//    }
//
//    @After
//    fun `nuke session`() = runBlocking {
//        sessionRepository.clear()
//    }
//
//    @Test
//    fun `synchronize session use case works as expected`() = runTest {
//        sessionRepository.getAllSessions().first().let {
//            assertThat(it.size, `is`(0))
//        }
//
//        useCase()
//
//        sessionRepository.getAllSessions().first().let {
//            assertThat(it.size, `is`(1))
//        }
//
//        sessionRepository.getLatestSession().first().let {
//            assertThat(it, instanceOf(Session::class.java))
//        }
//    }
//}