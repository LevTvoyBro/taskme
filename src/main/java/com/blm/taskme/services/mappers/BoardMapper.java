package com.blm.taskme.services.mappers;

import com.blm.taskme.api.v1.request.BoardRequest;
import com.blm.taskme.api.v1.response.BoardResponse;
import com.blm.taskme.domains.Board;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    void mergeIntoBoard(@MappingTarget Board board, BoardRequest request);

    BoardResponse toResponse(Board board);
}
