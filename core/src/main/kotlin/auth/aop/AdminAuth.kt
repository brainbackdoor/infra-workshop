package auth.aop

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AdminAuth(
    val validAdmin: Boolean = true,

    val validParameter: Boolean = false,
    val parameterValue: String = "memberId",

    val validPathVariable: Boolean = false,
    val pathVariable: String = "memberId",

    val validRequestBody: Boolean = false,
    val requestBody: String = "memberId",
) {
    companion object {
        const val ACCESS_TOKEN = "x-access-token"
    }
}