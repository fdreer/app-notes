package com.franco.appnotes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.franco.appnotes.dto.NoteDtoRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "note")
@Table(name = "notes")
@EntityListeners(AuditingEntityListener.class)
public class Note implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(nullable = false)
    private String title;
//    @Column(nullable = false)
    private String content;
//    @Column(nullable = false)
    private boolean completed;
//    @Column(nullable = false)
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
    public static Note assignUserToNote(NoteDtoRequest note, User user)
    {
        return Note.builder()
                .title(note.title())
                .content(note.content())
                .completed(note.completed())
                .important(note.important())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .user(user)
                .build();
    }

    /*
    * Only return the note with fields title, content and lastModifiedDate
    * updated. To update important and completed fields, use another method.
    * */
    public static Note getNewNoteUpdated(NoteDtoRequest newNote, Note oldNote)
    {
        return Note.builder()
                .id(oldNote.getId())
                .title(newNote.title() == null ? oldNote.getTitle() : newNote.title())
                .content(newNote.content() == null ? oldNote.getContent() : newNote.content())
                .important(oldNote.isImportant())
                .completed(oldNote.isCompleted())
                .createdDate(oldNote.getCreatedDate())
                .lastModifiedDate(LocalDateTime.now())
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
