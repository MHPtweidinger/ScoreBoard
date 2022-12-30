package de.tobsinger.scoreboard.persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

private val Context.scoreBoardCache: DataStore<ScoreboardState> by dataStore(
    fileName = "TrackYourDreamOrders",
    serializer = DataStoreKotlinxSerializer(
        defaultValue = ScoreboardState(emptyMap()),
        serializer = ScoreboardState.serializer(),
    ),
)

internal class ScoreboardPersistenceImpl(context: Context) : ScoreboardPersistence {

    private val dataStore = context.scoreBoardCache

    override val data: Flow<ScoreboardState> =
        dataStore.safeData(ScoreboardState(emptyMap()))

    override suspend fun persistState(newState: ScoreboardState) {
        dataStore.updateData { newState }
    }
}

private inline fun <reified T> DataStore<T>.safeData(default: T): Flow<T> = data.catch { e ->
    if (e is IOException) {
        emit(default)
    } else {
        throw e
    }
}
