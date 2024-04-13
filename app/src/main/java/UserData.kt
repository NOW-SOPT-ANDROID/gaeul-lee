import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class UserData (
    var id : String,
    var pwd : String,
    var nickname : String,
    var mbti : String
) : Parcelable