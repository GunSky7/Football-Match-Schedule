package com.devgeek.footballmatchschedule.database

data class FavouriteMatch(val id: Long?, val eventId: String?, val eventName: String?, val eventHomeTeam: String?,
                          val eventHomeScore: String?, val eventAwayTeam: String?,
                          val eventAwayScore: String?, val eventDate: String?, val eventTime: String?) {

    companion object {
        const val TABLE_FAVOURITE_MATCH: String = "TABLE_FAVOURITE_MATCH"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_NAME: String = "EVENT_NAME"
        const val EVENT_HOME_TEAM: String = "EVENT_HOME_TEAM"
        const val EVENT_HOME_SCORE: String = "EVENT_HOME_SCORE"
        const val EVENT_AWAY_TEAM: String = "EVENT_AWAY_TEAM"
        const val EVENT_AWAY_SCORE: String = "EVENT_AWAY_SCORE"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val EVENT_TIME: String = "EVENT_TIME"
    }
}