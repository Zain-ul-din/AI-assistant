package com.randoms.ai_assistant.lib


import retrofit2.Call
import retrofit2.http.GET

data class Course(val sections: List<String>)
data class Semester(val courses: Map<String, Course>)
data class ApiResponse(val semesters: Map<String, Semester>)

interface ApiService {
    @GET("api/public/metadata?apikey=01HM4E10J21S2K1T32WXB4T4AB")
    fun getMetaData(): Call<ApiResponse>
}



