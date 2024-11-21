package hotkitchen.model

import kotlinx.serialization.Serializable

@Serializable
data class Status(
    val status: String,
)