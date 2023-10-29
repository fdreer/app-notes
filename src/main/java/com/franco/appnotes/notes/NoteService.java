package com.franco.appnotes.notes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.franco.appnotes.notes.dto.NoteCreateDto;
import com.franco.appnotes.notes.dto.NoteUpdateDto;
import com.franco.appnotes.notes.entities.Note;
import com.franco.appnotes.users.IUserService;
import com.franco.appnotes.users.entities.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteService implements INoteService{

    private final NoteRepository noteRepository;
    private final IUserService userService;
    private final ObjectMapper mapper;

//    TODO: Add a method that change important and completed properties of a note
    @Override
    public Note createNote(NoteCreateDto newNote)
    {
        User user = userService.findById(newNote.userId());
        Note noteWithUserAssigned = Note.assignUserToNote(newNote, user);
        return noteRepository.save(noteWithUserAssigned);
    }

    @Override
    public void updateNote(UUID id, Map<String, Object> fields) throws JsonProcessingException {
        Note noteInDB = this.findById(id);
        Note noteUpdated = mapper.updateValue(noteInDB, fields);
        noteRepository.save(noteUpdated);
    }

    @Override
    public Note findById(UUID id)
    {
        return noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note with " + id + " not found"));
    }

    @Override
    public List<Note> findNotesFromUser(UUID userId)
    {
        return noteRepository.findNotesByUser(userId);
    }

    @Override
    public void deleteNoteById(UUID id)
    {
        noteRepository.deleteById(id);
    }
}
