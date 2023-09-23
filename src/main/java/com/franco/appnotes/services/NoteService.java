package com.franco.appnotes.services;

import com.franco.appnotes.dto.NoteDtoRequest;
import com.franco.appnotes.entity.Note;
import com.franco.appnotes.entity.User;
import com.franco.appnotes.repository.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserService userService;

//    TODO: Add a method that change important and completed properties of a note
    public Note createNote (NoteDtoRequest newNote)
    {
        User user = userService.findById(newNote.userId());
        Note noteWithUserAssigned = Note.assignUserToNote(newNote, user);
        return noteRepository.save(noteWithUserAssigned);
    }

    public Note updateNote(Long id, NoteDtoRequest newNote)
    {
        Note noteInDB = this.findById(id);
        Note newNoteToUpdate = Note.getNewNoteUpdated(newNote, noteInDB);
        return noteRepository.save(newNoteToUpdate);
    }

    public Note findById (Long id)
    {
        return noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note with " + id + " not found"));
    }

    public List<Note> findNotesFromUser(Long userId)
    {
        return noteRepository.findNotesByUser(userId);
    }

    public void deleteNoteById(Long id)
    {
        noteRepository.deleteById(id);
    }
}
