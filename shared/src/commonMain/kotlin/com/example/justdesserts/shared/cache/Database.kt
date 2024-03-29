import com.example.justdesserts.shared.cache.DatabaseDriverFactory
import com.example.justdesserts.shared.cache.Dessert
import com.example.justdesserts.shared.cache.JustDesserts
import com.example.justdesserts.shared.cache.UserState

class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = JustDesserts(databaseDriverFactory.createDriver())
    private val dbQuery = database.justDessertsQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllDesserts()
        }
    }

    internal fun getDessert(): List<Dessert> {
        return dbQuery.selectAllDesserts().executeAsList()
    }

    internal fun getDessertById(dessertId: String): Dessert? {
        return dbQuery.selectDessertById(dessertId).executeAsOneOrNull()
    }

    internal fun saveDessert(dessert: Dessert) {
        dbQuery.transaction {
            insertDessert(dessert)
        }
    }

    internal fun updateDessert(dessert: Dessert) {
        dbQuery.transaction {
            updateDessertById(dessert)
        }
    }

     internal fun deleteDessert(dessertId: String) {
        dbQuery.transaction {
            removeDessert(dessertId)
        }
    }

    internal fun getUserState(): UserState? {
        return dbQuery.selectUserState().executeAsOneOrNull()
    }

    internal fun saveUserState(userId: String,token: String){
        dbQuery.transaction {
            insertUserState(userId, token)
        }
    }

    internal fun deleteUserState(){
        dbQuery.transaction {
            removeUserState()
        }
    }

    private fun removeUserState() {
        dbQuery.removeUserState()
    }

    private fun insertUserState(userId: String, token: String) {
        dbQuery.insertUserState(userId, token)
    }

    private fun removeDessert(dessertId: String) {
        dbQuery.removeDessertById(dessertId)
    }


    private fun updateDessertById(dessert: Dessert) {
        dbQuery.updateDessertById(
            name = dessert.name,
            description = dessert.description,
            imageUrl = dessert.imageUrl,
            id = dessert.id
        )

    }

    private fun insertDessert(dessert: Dessert) {
        dbQuery.insertDessert(
            dessert.id,
            dessert.userId,
            dessert.name,
            dessert.description,
            dessert.imageUrl
        )
    }
}