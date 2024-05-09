package com.kotlinspring.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlinspring.model.Bank
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper
) {


    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should return all banks`() {

            //when/then
            mockMvc.get("/api/banks")
                .andDo {
                    print()
                }
                .andExpect {
                    status {
                        isOk()
                        jsonPath("$[0].account_number") {
                            value("46544345")
                        }
                    }
                }


        }


    }

    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {

        @Test
        fun `should return a bank with the given accountNumber`() {

            //given

            val accountNumber = 46544345

            //when/then
            mockMvc.get("/api/banks/$accountNumber")
                .andDo {
                    print()
                }
                .andExpect {
                    status {
                        isOk()
                    }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") {
                        value(5.5)
                    }
                }


        }


        @Test
        fun `should return NOT FOUND if the account number does not exist`() {
            //given
            val accountNumber = "not exist"

            //when /then

            mockMvc.get("/api/banks/$accountNumber")
                .andDo {
                    print()
                }
                .andExpect {
                    status { isNotFound() }
                }


        }


    }


    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {

        @Test
        fun `should add the new bank`() {
            //given
            val newBank = Bank(accountNumber = "maecenas", trust = 2.3, transactionFee = 9972)


            //when
            val performPost = mockMvc.post("/api/banks") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            //then
            performPost.andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.account_number") { value("maecenas") }
                    jsonPath("$.trust") { value(2.3) }
                    jsonPath("$.default_transaction_fee") { value(9972) }
                }


        }

        @Test
        fun `should return BAD REQUESTS if the bank with given account number already exist`() {
            //given
            val invalidBank = Bank(accountNumber = "46544345", trust = 6.7, transactionFee = 4158)

            //when

            val performPost = mockMvc.post("/api/banks") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            //then
            performPost.andDo { print() }
                .andExpect {
                    status { isBadRequest() }

                }
        }

    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchBank {

        @Test
        fun `should update existing bank`() {
            //given
            val existingBank = Bank(accountNumber = "46544345", trust = 6.8, transactionFee = 4158)

            //when
            val performPatchRequest = mockMvc.patch("/api/banks") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(existingBank)
            }
            //then

            performPatchRequest.andDo { print() }
                .andExpect { status { isOk() } }


        }

        @Test
        fun `should return NOT FOUND if the no bank with given account number exists`() {
            //given
            val invalidBank = Bank(accountNumber = "19121256", trust = 6.7, transactionFee = 4158)

            //when

            val performPost = mockMvc.patch("/api/banks") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            //then
            performPost.andDo { print() }
                .andExpect {
                    status { isNotFound() }

                }
        }


    }

    @Nested
    @DisplayName("DELETE /api/banks")
    @DirtiesContext
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingBank {

        @Test
        fun `should delete the existing bank`() {
            //given
            val accountNumber = "46544345"

            //when
            val performDeleteRequest = mockMvc.delete("/api/banks/$accountNumber")

            //then
            performDeleteRequest.andDo { print() }
                .andExpect { status { isNoContent() } }


        }

        @Test
        fun `should  return NOT FOUND if the bank  with given account number not exists`() {
            //given
            val accountNumber = "abc"

            //when
            val performDeleteRequest = mockMvc.delete("/api/banks/$accountNumber")

            //then
            performDeleteRequest.andDo { print() }
                .andExpect { status { isNotFound() } }


        }
    }

}