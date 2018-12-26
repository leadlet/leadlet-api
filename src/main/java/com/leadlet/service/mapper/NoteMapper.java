package com.leadlet.service.mapper;

import com.leadlet.domain.Note;
import com.leadlet.service.dto.NoteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ContactMapper.class, DealMapper.class})
public interface NoteMapper extends EntityMapper<NoteDTO, Note> {

    @Mapping(source = "contact.id", target = "contactId")
    @Mapping(source = "deal.id", target = "dealId")
    NoteDTO toDto(Note contact);

    @Mapping(source = "contactId", target = "contact")
    @Mapping(source = "dealId", target = "deal")
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
