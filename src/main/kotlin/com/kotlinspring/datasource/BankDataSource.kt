package com.kotlinspring.datasource

import com.kotlinspring.model.Bank

interface BankDataSource {

    fun getBanks(): Collection<Bank>
    fun getBank(accountNumber: String): Bank
    fun addBank(bank: Bank): Bank
    fun updateBank(bank: Bank): Bank
    fun deleteBank(accountNumber: String): Unit

}