package hotkitchen.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String? = null,
    val userType: String? = null,
    val password: String? = null,
)


