package com.devgeek.footballmatchschedule.apiresponse

import com.devgeek.footballmatchschedule.model.Match

data class SearchMatchResponse (
        val event: List<Match>
)