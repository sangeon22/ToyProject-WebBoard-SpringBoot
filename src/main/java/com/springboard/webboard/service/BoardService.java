package com.springboard.webboard.service;

import com.springboard.webboard.web.dto.BoardDto;
import com.springboard.webboard.domain.board.Board;
import com.springboard.webboard.domain.user.User;
import com.springboard.webboard.domain.board.BoardRepository;
import com.springboard.webboard.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public Page<BoardDto> getList(Pageable pageable, String searchKeyword){
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchKeyword, searchKeyword, pageable);
        Page<BoardDto> boardDtoList = new BoardDto().toDtoList(boards); // Page<Entity> -> Page<Dto> 변환.

        return boardDtoList;
    }

    @Transactional(readOnly = true)
    public Page<BoardDto> getMyBoardList(Pageable pageable, Authentication authentication){
        Page<Board> boards = boardRepository.userBoardList(authentication.getName(), pageable);
        Page<BoardDto> boardDtoList = new BoardDto().toDtoList(boards);

        return boardDtoList;
    }

    @Transactional
    public void save(BoardDto boardDto,
                     String username,
                     MultipartFile file,
                     Integer view) throws IOException {

        Board board = boardDto.toEntity();

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        if (!file.getOriginalFilename().equals("")) {
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();

            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);

            board.setFilename(fileName);
            board.setFilepath("/files/" + fileName);
        }
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        board.setView(view);
//        board.setView(board.getView());
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Integer boardView(Long id) {
        return boardRepository.findById(id).get().getView();
    }

    @Transactional
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public int updateView(Long id) {
        return boardRepository.updateView(id);
    }

    @Transactional(readOnly = true)
    public BoardDto getPost(Long id) {
        Optional<Board> boardWrapper = boardRepository.findById(id);
        if(boardWrapper.isPresent())
        {
            Board board = boardWrapper.get();

            BoardDto boardDto = BoardDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .filename(board.getFilename())
                    .filepath(board.getFilepath())
                    .createdDate(board.getCreatedDate())
                    .modifiedDate(board.getModifiedDate())
                    .user(board.getUser())
                    .build();

            return boardDto;
        }

        return null;
    }

}