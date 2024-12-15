package ch.fhnw.petra.poodle.misc

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class UrlHelper {

    @Value("\${app.base-url:http://localhost:8080}")
    private lateinit var baseUrl: String

    fun createUrl(path: String): String {
        return baseUrl + (if (path.startsWith("/")) path else "/$path")
    }

}