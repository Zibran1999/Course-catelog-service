package com.kotlinspring.datasource.network

import com.kotlinspring.datasource.BankDataSource
import com.kotlinspring.datasource.network.dto.BankList
import com.kotlinspring.model.Bank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.io.IOException

@Repository("network")
class NetworkDataSource(
    @Autowired private val restTemplate: RestTemplate
) : BankDataSource {
    override fun getBanks(): Collection<Bank> {
        val response = restTemplate.getForEntity<BankList>("http://54.193.31.159/banks")
        return response.body?.results ?: throw IOException("Could not fetch bank from the network")
    }

    override fun getBank(accountNumber: String): Bank {
        TODO("Not yet implemented")
    }

    override fun addBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun updateBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(accountNumber: String) {
        TODO("Not yet implemented")
    }
}