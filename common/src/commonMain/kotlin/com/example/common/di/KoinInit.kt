package com.example.common.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(koinAppDeclaration: KoinAppDeclaration={}){
    startKoin {
        koinAppDeclaration()
        modules(networkModule)
    }
}