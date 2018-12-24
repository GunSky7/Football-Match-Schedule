package com.devgeek.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class Player(
        @SerializedName("idPlayer")
        var playerId: String? = null,

        @SerializedName("strPlayer")
        var playerName: String? = null,

        @SerializedName("strTeam")
        var playerTeam: String? = null,

        @SerializedName("strCutout")
        var playerImage: String? = null,

        @SerializedName("strFanart1")
        var playerFanArt: String? = null,

        @SerializedName("strPosition")
        var playerPosition: String? = null,

        @SerializedName("strHeight")
        var playerHeight: String? = null,

        @SerializedName("strWeight")
        var playerWeight: String? = null,

        @SerializedName("strDescriptionEN")
        var playerDescription: String? = null
)