package com.leadlet.service.mapper;

import com.leadlet.domain.Note;
import com.leadlet.service.dto.NoteDTO;
import com.leadlet.service.mapper.ContactMapper;
import com.leadlet.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ContactMapper.class})
public interface NoteMapper extends EntityMapper<NoteDTO, Note> {

    @Mapping(source = "contact.id", target = "contactId")

    NoteDTO toDto(Note contact);

    @Mapping(source = "contactId", target = "contact")

    Note toEntity(NoteDTO noteDTO);
    default Note fromId(Long id) {
        if (id == null) {
            return null;
        }
        Note note = new Note();
        note.setId(id);
        return note;
    }
}
