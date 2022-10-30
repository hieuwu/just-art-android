package com.hieuwu.justart.domain.usecases

import com.hieuwu.justart.domain.models.EventDo

interface GetEventsUseCase : UseCase<GetEventsUseCase.Input, GetEventsUseCase.Result> {
    class Input
  open class Result {
        data class Success(val data: List<EventDo>?) : Result()
        data class Failure(val error: Error?) : Result()
    }

    class Error
}