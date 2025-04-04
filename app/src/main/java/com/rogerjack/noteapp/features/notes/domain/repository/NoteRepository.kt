package com.rogerjack.noteapp.features.notes.domain.repository

import com.rogerjack.noteapp.data.local.models.Notes
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun addNote(notes: Notes)

    fun getAllNotes():Flow<List<Notes>>

    suspend fun deleteNote(note:Notes)

    suspend fun updateNote(note: Notes)

}