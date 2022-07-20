package com.example.helloword.api

import com.example.helloword.models.Repo

data class SearchResult(
    val items: List<Repo>
)