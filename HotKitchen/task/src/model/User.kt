package hotkitchen.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    val userType: String? = null,
    val password: String? = null,
)


