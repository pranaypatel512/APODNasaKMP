package com.example.common.di

import com.example.common.data.sources.NasaPicturesRepositoryImpl
import com.example.common.domain.repositories.NasaPicturesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<NasaPicturesRepository> { NasaPicturesRepositoryImpl(httpClient = get()) }
}