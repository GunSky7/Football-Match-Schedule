package com.devgeek.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class Match(
        @SerializedName("idEvent")
        var eventId : String? = null,

        @SerializedName("strEvent")
        var eventName : String? = null,

        @SerializedName("strHomeTeam")
        var eventHomeTeam : String? = null,

        @SerializedName("intHomeScore")
        var eventHomeScore : String? = null,

        @SerializedName("intHomeShots")
        var eventHomeShots : String? = null,

        @SerializedName("strHomeGoalDetails")
        var eventHomeGoalDetails : String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        var eventHomeLineupGoalkeeper : String? = null,

        @SerializedName("strHomeLineupDefense")
        var eventHomeLineupDefence : String? = null,

        @SerializedName("strHomeLineupMidfield")
        var eventHomeLineupMidfield : String? = null,

        @SerializedName("strHomeLineupForward")
        var eventHomeLineupForward : String? = null,

        @SerializedName("strHomeLineupSubtitutes")
        var eventHomeLineupSubtitutes : String? = null,

        @SerializedName("strAwayTeam")
        var eventAwayTeam : String? = null,

        @SerializedName("intAwayScore")
        var eventAwayScore : String? = null,

        @SerializedName("intAwayShots")
        var eventAwayShots : String? = null,

        @SerializedName("strAwayGoalDetails")
        var eventAwayGoalDetails : String? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        var eventAwayLineupGoalkeeper : String? = null,

        @SerializedName("strAwayLineupDefense")
        var eventAwayLineupDefence : String? = null,

        @SerializedName("strAwayLineupMidfield")
        var eventAwayLineupMidfield : String? = null,

        @SerializedName("strAwayLineupForward")
        var eventAwayLineupForward : String? = null,

        @SerializedName("strAwayLineupSubtitutes")
        var eventAwayLineupSubtitutes : String? = null,

        @SerializedName("strDate")
        var eventDate : String? = null,

        @SerializedName("strTime")
        var eventTime : String? = null,

        @SerializedName("strSport")
        var eventSport : String? = null

)