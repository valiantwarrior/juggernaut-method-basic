package kr.valor.juggernaut.data.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FakeRepository