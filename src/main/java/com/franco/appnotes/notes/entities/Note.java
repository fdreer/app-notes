package com.franco.appnotes.notes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.franco.appnotes.notes.dto.NoteCreateDto;
import com.franco.appnotes.notes.dto.NoteUpdateDto;
import com.franco.appnotes.users.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "note")
@Table(name = "notes")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Note implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    private String title;

    @Column(nullable = false, length = 500)
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 500)
    private String content;

    @Column(nullable = false)
    @NotNull
    private boolean important;

    @ManyToOne
//    Si ponemos lazy no se traen los usuarios. En este caso es mas eficiente
    @JoinColumn(name = "user_id")
    @JsonIgnore
//    Si no ponemos el @JsonIgnore, la aplicacion fallaria, porque al momento de
    // traer las notas, se traen los usuarios, y al momento de traer los usuarios,
    // se traen las notas. (loop infinito)
    private User user;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    /**
     * Return a note with the user assigned to it, ready to be saved in the
     * database.
     */
    public static Note assignUserToNote(NoteCreateDto note, User user)
    {
        return Note.builder()
                .title(note.title())
                .content(note.content())
                .important(note.important())
                .user(user)
                .build();
    }

    /*
    * Only return the note with fields title, content and lastModifiedDate
    * updated.
    * */
    public static Note getNewNoteUpdated(NoteUpdateDto newNote, Note oldNote)
    {
        return Note.builder()
                .id(oldNote.getId())
                .title(newNote.title() == null ? oldNote.getTitle() : newNote.title())
                .content(newNote.content() == null ? oldNote.getContent() : newNote.content())
                .important(oldNote.isImportant())
                .user(oldNote.getUser())
                .build();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Note note = (Note) o;
        return getId() != null && Objects.equals(getId(), note.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
