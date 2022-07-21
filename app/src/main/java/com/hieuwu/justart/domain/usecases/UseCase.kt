package com.hieuwu.justart.domain.usecases

interface UseCase<InputT, OutputT> {
    suspend fun execute(input: InputT): OutputT
}