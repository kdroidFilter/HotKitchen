package hotkitchen.model

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Users : IntIdTable() {
    val email: Column<String> = varchar("email", 255)
    val userType: Column<String> = varchar("userType", 50)
    val password: Column<String> = varchar("password", 255)
}