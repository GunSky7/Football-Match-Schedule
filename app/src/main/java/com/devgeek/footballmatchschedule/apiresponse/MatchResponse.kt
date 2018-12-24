package com.devgeek.footballmatchschedule.apiresponse

import com.devgeek.footballmatchschedule.model.Match

data class MatchResponse (
        val events: List<Match>
)