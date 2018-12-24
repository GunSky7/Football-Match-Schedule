package com.devgeek.footballmatchschedule.apiresponse

import com.devgeek.footballmatchschedule.model.Player
import com.devgeek.footballmatchschedule.model.Team

data class PlayerResponse (
        val player: List<Player>
)