package com.franco.appnotes.repository;

import com.franco.appnotes.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query("select n from note n where n.user.id = ?1")
    List<Note> findNotesByUser(Long id);
}