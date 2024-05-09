package com.kotlinspring.datasource.mock

import com.kotlinspring.datasource.BankDataSource
import com.kotlinspring.model.Bank
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository("mock")
class MockBankDataSource : BankDataSource {
    val banks = mutableListOf(
        Bank(accountNumber = "46544345", trust = 5.5, 100),
        Bank(accountNumber = "4345376", trust = 9.0, 9),
        Bank(accountNumber = "1912121", trust = 54.5, 43)
    )

    override fun getBanks(): Collection<Bank> = banks
    override fun getBank(accountNumber: String): Bank = banks.firstOrNull { it.accountNumber == accountNumber }
        ?: throw NoSuchElementException(" Could not found a bank with account number of $accountNumber")

    override fun addBank(bank: Bank): Bank {
        if (!banks.any { it.accountNumber == bank.accountNumber }) {
            banks.add(bank)
            return bank
        }
        throw IllegalArgumentException("bank with account number ${bank.accountNumber} already exists")
    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank = banks.firstOrNull { it.accountNumber == bank.accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with given account number ${bank.accountNumber}")

        banks.remove(currentBank)
        banks.add(bank)
        return bank
    }

    override fun deleteBank(accountNumber: String) {
        val currentBank = banks.firstOrNull { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with given account number $accountNumber")

        banks.remove(currentBank)

    }
}