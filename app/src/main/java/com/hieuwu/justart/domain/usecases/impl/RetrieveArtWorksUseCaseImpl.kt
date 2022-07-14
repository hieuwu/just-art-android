package com.hieuwu.justart.domain.usecases.impl

import com.hieuwu.justart.domain.usecases.RetrieveArtWorksUseCase

class RetrieveArtWorksUseCaseImpl : RetrieveArtWorksUseCase {
    override suspend fun execute(input: RetrieveArtWorksUseCase.Input): RetrieveArtWorksUseCase.Result {
        return RetrieveArtWorksUseCase.Result(null, null)
    }
}