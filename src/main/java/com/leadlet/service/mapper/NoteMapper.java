package com.leadlet.service.mapper;

import com.leadlet.domain.Note;
import com.leadlet.service.dto.NoteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PersonMapper.class,OrganizationMapper.class})
public interface NoteMapper extends EntityMapper<NoteDTO, Note> {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "organization.id", target = "organizationId")

    NoteDTO toDto(Note person);

    @Mapping(source = "personId", target = "person")
    @Mapping(source = "organizationId", target = "organization")

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
