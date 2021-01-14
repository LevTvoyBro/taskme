package com.blm.taskme.services.implementation;

import com.blm.taskme.api.v1.request.BoardRequest;
import com.blm.taskme.api.v1.response.BoardResponse;
import com.blm.taskme.domains.Board;
import com.blm.taskme.domains.User;
import com.blm.taskme.repository.BoardRepository;
import com.blm.taskme.repository.UserRepository;
import com.blm.taskme.services.exception.EntityNotFoundException;
import com.blm.taskme.services.exception.UserNotFoundException;
import com.blm.taskme.services.mappers.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultBoardService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;
    private final DefaultUserResolver userResolver;

    @Autowired
    public DefaultBoardService(UserRepository userRepository, BoardRepository boardRepository, BoardMapper boardMapper, DefaultUserResolver userResolver) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.boardMapper = boardMapper;
        this.userResolver = userResolver;
    }

    @Transactional
    public BoardResponse addBoard(String email, BoardRequest request) throws UserNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Board board = new Board();

        boardMapper.mergeIntoBoard(board, request);

        board.setId(null);
        board.setOwner(user);

        Board savedBoard = boardRepository.save(board);

        return boardMapper.toResponse(savedBoard);
    }

    @Transactional
    public void updateBoard(String email, Long boardId, BoardRequest request) throws EntityNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));

        if (!board.getOwner().getId().equals(user.getId())) {
            throw new EntityNotFoundException("Board not found");
        }

        boardMapper.mergeIntoBoard(board, request);

        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public List<BoardResponse> getBoards(String email, int page, int perPage) throws UserNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Pageable pageable = PageRequest.of(page, perPage);

        List<Board> boards = boardRepository.findByOwner(user, pageable);

        List<BoardResponse> responseList = boards
                .stream()
                .map(boardMapper::toResponse)
                .collect(Collectors.toList());

        return responseList;
    }

    @Transactional
    public void deleteBoard(String email, long boardId) throws EntityNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with this email not found"));

        boolean deleted = boardRepository.deleteByIdAndOwner(boardId, user);

        if (!deleted) {
            throw new EntityNotFoundException("Board not found");
        }
    }
}
