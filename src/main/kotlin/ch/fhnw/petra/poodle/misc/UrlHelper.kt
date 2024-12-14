package ch.fhnw.petra.poodle.misc

import org.springframework.stereotype.Component

@Component
class UrlHelper {

    //todo: configuration
    private val baseUrl: String = "http://localhost:8080"

    fun createUrl(path: String): String {
        return baseUrl + (if (path.startsWith("/")) path else "/$path")
    }

}