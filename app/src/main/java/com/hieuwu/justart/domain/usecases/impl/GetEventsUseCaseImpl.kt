package com.hieuwu.justart.domain.usecases.impl

import com.hieuwu.justart.domain.usecases.GetEventsUseCase
import com.hieuwu.justart.mapper.asDomainModel
import com.hieuwu.justartsdk.events.v1.EventsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetEventsUseCaseImpl @Inject constructor(
    private val eventService: EventsService
) : GetEventsUseCase {
    override suspend fun execute(input: GetEventsUseCase.Input): GetEventsUseCase.Result {
        withContext(Dispatchers.IO) {
            val res = eventService.getEvents()
            return@withContext GetEventsUseCase.Result.Success(
                res.response?.data?.map {
                    it.asDomainModel()
                }
            )
        }
        return GetEventsUseCase.Result.Failure(GetEventsUseCase.Error())
    }
}