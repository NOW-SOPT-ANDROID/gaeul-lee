import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val authenticationId: String,
    val nickname: String,
    val phone: String,
)