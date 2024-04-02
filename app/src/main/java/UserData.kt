import java.io.Serializable

data class UserData (
    var id : String = "",
    var pwd : String = "",
    var nickname : String = "",
    var mbti : String = ""
) : Serializable