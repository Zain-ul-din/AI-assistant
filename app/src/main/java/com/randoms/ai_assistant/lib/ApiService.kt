package com.randoms.ai_assistant.lib


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

data class SafetyRating(
    val category: String,
    val probability: String
)

data class ContentPart(
    val text: String
)

data class Parts(
    val parts: List<ContentPart>,
    val role: String
)

data class Content(
    val parts: List<ContentPart>,
    val role: String
)

data class Candidate(
    val content: Content,
    val finishReason: String,
    val index: Int,
    val safetyRatings: List<SafetyRating>
)

data class PromptFeedback(
    val safetyRatings: List<SafetyRating>
)

data class GenerateContentResponse(
    val candidates: List<Candidate>,
    val promptFeedback: PromptFeedback
)

data class RequestBody(
    val contents: List<ContentRequest>
)

data class ContentRequest(
    val parts: List<ContentPartRequest>
)

data class ContentPartRequest(
    val text: String
)


interface ApiService {

    @POST("models/gemini-pro:generateContent")
    fun generateContent(
        @Header("Content-Type") contentType: String = "application/json",
        @Query("key") apiKey: String,
        @Body requestBody: RequestBody
    ): Call<GenerateContentResponse>

}
