package com.springboard.webboard.service;

import com.springboard.webboard.dto.BoardDto;
import com.springboard.webboard.entity.Board;
import com.springboard.webboard.entity.User;
import com.springboard.webboard.repository.BoardRepository;
import com.springboard.webboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

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

    @Transactional
    public Board boardView(Long id) {
        return boardRepository.findById(id).get();
    }

    @Transactional
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public int updateView(Long id) {
        return boardRepository.updateView(id);
    }

    @Transactional
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
                    .build();

            return boardDto;
        }

        return null;
    }

}