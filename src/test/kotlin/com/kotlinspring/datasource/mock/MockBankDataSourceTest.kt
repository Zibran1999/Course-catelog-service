package com.kotlinspring.datasource.mock


import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class MockBankDataSourceTest {
    private val bankDataSource: MockBankDataSource = MockBankDataSource()


    @Test
    fun `should provide collection of banks`() {
        //given

        //when
        val banks = bankDataSource.getBanks()

        //then
        Assertions.assertThat(banks).isNotEmpty
        Assertions.assertThat(banks.size).isGreaterThanOrEqualTo(3)

    }

    @Test
    fun `should provide some mock data`() {

        //when
        val banks = bankDataSource.getBanks()

        //then
        Assertions.assertThat(banks).allMatch { it.accountNumber.isNotBlank() }
        Assertions.assertThat(banks).anyMatch { it.trust != 0.0 }
        Assertions.assertThat(banks).anyMatch { it.trust != 0.0 }
        Assertions.assertThat(banks).allMatch { it.transactionFee != 0 }


    }
}