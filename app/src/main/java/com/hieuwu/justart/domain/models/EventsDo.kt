package com.hieuwu.justart.domain.models

data class EventDo(
    val id: Int?,
    val apiModel: String?,
    val apiLink: String?,
    val title: String?,
    val titleDisplay: String?,
    val imageUrl: String?,
    val heroCaption: String?,
    val shortDescription: String?,
    val headerDescription: String?,
    val listDescription: String?,
    val description: String?,
    val location: String?,
    val programTitles: List<String>,
    val ticketedEventId: Int?,
    val buyButtonCaption: String?,
    val isRegistrationRequired: Boolean?,
    val isMemberExclusive: Boolean?,
    val isFree: Boolean?,
    val virtualEventUrl: String?,
    val startDate: String?,
    val endDate: String?,
    val startTime: String?,
    val endTime: String?,
    val dateDisplay: String?
)