package com.blm.taskme.api.v1.controller;

import com.blm.taskme.api.v1.response.BoardResponse;
import com.blm.taskme.services.exception.EntityNotFoundException;
import com.blm.taskme.services.exception.UserNotFoundException;
import com.blm.taskme.services.implementation.DefaultBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    private final DefaultBoardService boardService;

    @Autowired
    public BoardController(DefaultBoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/boards")
    public ResponseEntity<?> getBoards(
            @AuthenticationPrincipal String username,
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(name = "per_page", defaultValue = "50", required = false) Integer perPage
    ) throws EntityNotFoundException {
        logger.debug("Get boards request: page={} per_page={}", page, perPage);

        List<BoardResponse> response = boardService.getBoards(username, page, perPage);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
