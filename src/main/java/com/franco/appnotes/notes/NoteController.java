package com.franco.appnotes.notes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.franco.appnotes.notes.dto.NoteCreateDto;
import com.franco.appnotes.notes.dto.NoteUpdateDto;
import com.franco.appnotes.notes.entities.Note;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {

    private final INoteService noteService;

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById (@PathVariable UUID id)
    {
        Note note = noteService.findById(id);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

//    TODO: verificar endpoint
    @GetMapping("/user")
    public ResponseEntity<List<Note>> getNotesFromUser (@RequestParam UUID id)
    {
        List<Note> notes = noteService.findNotesFromUser(id);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Note> createNote (@Valid @RequestBody NoteCreateDto newNote)
    {
        Note noteCreated = noteService.createNote(newNote);
        return new ResponseEntity<>(noteCreated, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateNote (@PathVariable UUID id,
                                         @RequestBody Map<String, Object> fields
    ) throws JsonProcessingException {
        noteService.updateNote(id, fields);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Note> deleteNote (@PathVariable UUID id)
    {
        noteService.deleteNoteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
