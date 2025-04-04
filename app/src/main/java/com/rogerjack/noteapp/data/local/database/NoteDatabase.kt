package com.rogerjack.noteapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rogerjack.noteapp.data.local.dao.NoteDao
import com.rogerjack.noteapp.data.local.models.Notes

@Database(version = 4, entities = [Notes::class], exportSchema = false)
abstract class NoteDatabase : RoomDatabase(){

    abstract val noteDao: NoteDao

}