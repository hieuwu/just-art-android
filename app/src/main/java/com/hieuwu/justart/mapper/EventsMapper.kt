package com.hieuwu.justart.mapper

import com.hieuwu.justart.domain.models.EventDo
import com.hieuwu.justartsdk.events.v1.domain.Event

fun Event.asDomainModel() = EventDo(
    id = this.id,
    apiModel = this.apiModel,
    apiLink = this.apiLink,
    title = this.title,
    titleDisplay = this.titleDisplay,
    imageUrl = this.imageUrl,
    heroCaption = this.heroCaption,
    shortDescription = this.shortDescription,
    headerDescription = this.headerDescription,
    listDescription = this.listDescription,
    description = this.description,
    location = this.location,
    programTitles = this.programTitles,
    ticketedEventId = this.ticketedEventId,
    buyButtonCaption = this.buyButtonCaption,
    isRegistrationRequired = this.isRegistrationRequired,
    isMemberExclusive = this.isMemberExclusive,
    isFree = this.isFree,
    virtualEventUrl = this.virtualEventUrl,
    startDate = this.startDate,
    endDate = this.endDate,
    startTime = this.startTime,
    endTime = this.endTime,
    dateDisplay = this.dateDisplay
)