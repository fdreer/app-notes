package com.franco.appnotes.controller;

import com.franco.appnotes.dto.NoteDtoRequest;
import com.franco.appnotes.entity.Note;
import com.franco.appnotes.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<Note> getNoteById (@RequestParam Long id)
    {
        Note note = noteService.findById(id);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Note>> getNotesFromUser (@RequestParam Long id)
    {
        List<Note> notes = noteService.findNotesFromUser(id);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Note> createNote (@RequestBody NoteDtoRequest newNote)
    {
        Note noteCreated = noteService.createNote(newNote);
        return new ResponseEntity<>(noteCreated, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Note> updateNote (@RequestParam Long id,
                                            @RequestBody NoteDtoRequest newNote)
    {
        Note noteUpdated = noteService.updateNote(id, newNote);
        return new ResponseEntity<>(noteUpdated, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Note> deleteNote (@RequestParam Long id)
    {
        noteService.deleteNoteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
