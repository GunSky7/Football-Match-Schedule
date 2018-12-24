package com.devgeek.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class League(
        @SerializedName("idLeague")
        var leagueId : String? = null,

        @SerializedName("strLeague")
        var leagueName : String? = null
)