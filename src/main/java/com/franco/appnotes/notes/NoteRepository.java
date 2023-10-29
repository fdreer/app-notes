package com.franco.appnotes.notes;

import com.franco.appnotes.notes.entities.Note;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
    @Query("select n from note n where n.user.id = ?1")
    List<Note> findNotesByUser(UUID id);
}