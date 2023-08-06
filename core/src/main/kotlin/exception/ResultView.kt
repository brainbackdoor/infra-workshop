package exception

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResultView(
    val success: Boolean,
    val message: String = "",
    var body: Any = ""
) {

    fun body(body: Any) {
        this.body = body
    }

    companion object {
        fun success(body: Any = ""): ResultView {
            return ResultView(true, body = body)
        }

        fun fail(message: String?): ResultView {
            return ResultView(false, message.orEmpty())
        }
    }
}
