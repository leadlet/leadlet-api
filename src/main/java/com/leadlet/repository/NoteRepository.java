package com.leadlet.repository;

import com.leadlet.domain.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface NoteRepository extends JpaRepository<Note,Long>, JpaSpecificationExecutor<Note> {

    Page<Note> findByAppAccount_Id(Long appAccountId, Pageable page);
    Note findOneByIdAndAppAccount_Id(Long id, Long appAccountId);

}
