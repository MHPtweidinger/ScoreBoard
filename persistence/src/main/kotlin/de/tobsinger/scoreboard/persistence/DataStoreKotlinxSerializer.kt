package de.tobsinger.scoreboard.persistence

import androidx.datastore.core.Serializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

internal class DataStoreKotlinxSerializer<T>(
    override val defaultValue: T,
    private val serializer: KSerializer<T>,
) : Serializer<T> {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun readFrom(input: InputStream): T = runCatching {
        json.decodeFromString(serializer, String(input.readBytes()))
    }.getOrElse { defaultValue }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: T, output: OutputStream) =
        output.write(json.encodeToString(serializer, t).toByteArray())
}
