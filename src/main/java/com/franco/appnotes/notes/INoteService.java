package com.franco.appnotes.notes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.franco.appnotes.notes.dto.NoteCreateDto;
import com.franco.appnotes.notes.entities.Note;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface INoteService {
    Note createNote(NoteCreateDto newNote);
    void updateNote(UUID id, Map<String, Object> fields) throws JsonProcessingException;
    Note findById(UUID id);
    List<Note> findNotesFromUser(UUID userId);
    void deleteNoteById(UUID id);
}
