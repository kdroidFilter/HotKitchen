package hotkitchen.model

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

interface SigningTasksRepository {
    fun userExist(user: User) : Boolean
    fun signup(user: User)
    fun signin(user: User) : Boolean
}

class SigningTasksRepositoryImpl : SigningTasksRepository {
    override fun userExist(user: User): Boolean {
        return transaction {
            Users
                .select(Users.email)
                .where { Users.email eq user.email }
                .limit(1)
                .any()
        }
    }

    override fun signup(user: User) {
        transaction {
            if (!userExist(user)) {
                Users.insert {
                    if (user.password != null && user.userType != null) {
                        it[email] = user.email
                        it[userType] = user.userType
                        it[password] = user.password
                    }
                }
            }
        }
    }

    override fun signin(user: User): Boolean {
       return transaction {
           if (user.password == null) return@transaction false
           Users
               .select(Users.email, Users.password)
               .where {
                   (Users.email eq user.email) and (Users.password eq user.password)
               }
               .limit(1)
               .any()
       }
    }
}

