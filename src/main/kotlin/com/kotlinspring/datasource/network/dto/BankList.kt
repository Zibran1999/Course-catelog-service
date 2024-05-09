package com.kotlinspring.datasource.network.dto

import com.kotlinspring.model.Bank

data class BankList(
    val results: Collection<Bank>
)
