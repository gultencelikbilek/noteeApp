package com.example.noteeapp.repository

import com.example.noteeapp.db.NoteDao
import com.example.noteeapp.db.NoteDatabase
import com.example.noteeapp.model.Note

class NoteRepository(private val db : NoteDatabase) {

    suspend fun addNote(note: Note) = db.noteDao().addNote(note)

    suspend fun updateNote(note:Note) = db.noteDao().updateNote(note)

    suspend fun deleteNote(note: Note) = db.noteDao().deleteNote(note)

    fun getAllNotes() = db.noteDao().getAllNotes()

    fun serachNote(query: String?) = db.noteDao().searchNote(query)

}