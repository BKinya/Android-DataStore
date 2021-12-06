package com.beatrice.datastore_example

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object UserSettingsSerializer: Serializer<UserSettingsOuterClass.UserSettings> {
    override val defaultValue: UserSettingsOuterClass.UserSettings
        get() = UserSettingsOuterClass.UserSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserSettingsOuterClass.UserSettings {
        try {
            return UserSettingsOuterClass.UserSettings.parseFrom(input)
        }catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserSettingsOuterClass.UserSettings, output: OutputStream) {
        t.writeTo(output)
    }
}