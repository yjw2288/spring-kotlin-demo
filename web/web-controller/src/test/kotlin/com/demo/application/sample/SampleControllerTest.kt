package com.demo.application.sample

import com.demo.sample.SampleDto
import com.demo.sample.SampleWebService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.snippet.Attributes
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@SpringBootTest
@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
internal class SampleControllerTest {
    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var sampleWebService: SampleWebService

    lateinit var restDocumentation: RestDocumentationContextProvider

    @BeforeEach
    fun before(restDocumentation: RestDocumentationContextProvider) {
        this.restDocumentation = restDocumentation
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .apply<DefaultMockMvcBuilder>(documentationConfiguration(restDocumentation))
            .build()
    }

    @Test
    fun `sample 리스트`() {
        every {
            sampleWebService.findAll()
        } returns listOf(
            SampleDto(
                id = 100,
                name = "name"
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
