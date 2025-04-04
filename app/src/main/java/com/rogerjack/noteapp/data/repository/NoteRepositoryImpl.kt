package com.rogerjack.noteapp.data.repository

import com.rogerjack.noteapp.data.local.dao.NoteDao
import com.rogerjack.noteapp.data.local.models.Notes
import com.rogerjack.noteapp.features.notes.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {


    override suspend fun addNote(notes: Notes) = withContext(Dispatchers.IO) {
        noteDao.addNote(notes)
    }

    override fun getAllNotes(): Flow<List<Notes>> = noteDao.getAllNotes()
    override suspend fun deleteNote(note: Notes) = noteDao.deleteNote(note)
    override suspend fun updateNote(note: Notes) = noteDao.updateNote(note)
}