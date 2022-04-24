package com.demo.application.sample

import com.demo.sample.SampleDto
import com.demo.sample.SampleWebService
import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.snippet.Attributes
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder
import org.springframework.web.filter.CharacterEncodingFilter

@ExtendWith(RestDocumentationExtension::class)
internal class SampleControllerTest {
    lateinit var mockMvc: MockMvc

    private lateinit var sampleWebService: SampleWebService

    private lateinit var sampleController: SampleController

    lateinit var restDocumentation: RestDocumentationContextProvider

    @BeforeEach
    fun before(restDocumentation: RestDocumentationContextProvider) {
        sampleWebService = mockk()
        sampleController = SampleController(
            sampleWebService
        )
        this.restDocumentation = restDocumentation
        mockMvc = MockMvcBuilders.standaloneSetup(sampleController)
            .addFilter<StandaloneMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .alwaysDo<StandaloneMockMvcBuilder>(MockMvcResultHandlers.print())
            .apply<StandaloneMockMvcBuilder>(documentationConfiguration(restDocumentation))
            .build()
    }

    @Test
    fun `sample 리스트`() {
        every {
            sampleWebService.findAll()
        } returns listOf(
            SampleDto(
                id = 100,
                name = "이름"
            )
        )

        val response = RestAssuredMockMvc.given()
            .mockMvc(mockMvc)
            .get("/sample")

        response.prettyPrint()

        response.then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .apply(
                document(
                    "sample-list",
                    preprocessRequest(),
                    preprocessResponse(),
                    responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER)
                            .attributes(
                                Attributes.key("format").value(""),
                                Attributes.key("sample").value(""),
                                Attributes.key("required").value("true"),
                            )
                            .description("기본키"),
                        fieldWithPath("[].name").type(JsonFieldType.STRING)
                            .attributes(
                                Attributes.key("format").value(""),
                                Attributes.key("sample").value(""),
                                Attributes.key("required").value("true"),
                            )
                            .description("이름")
                    ),
                )
            )
    }

    private fun preprocessRequest() = Preprocessors.preprocessRequest(
        Preprocessors.modifyUris().scheme("http").host("localhost"),
        Preprocessors.prettyPrint()
    )

    private fun preprocessResponse() = Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
}
