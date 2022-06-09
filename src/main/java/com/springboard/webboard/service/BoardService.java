package com.springboard.webboard.service;

import com.springboard.webboard.entity.Board;
import com.springboard.webboard.entity.User;
import com.springboard.webboard.repository.BoardRepository;
import com.springboard.webboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public void save(Board board, String username,
                      MultipartFile file) throws IOException {
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        boardRepository.save(board);
    }

    public Board boardView(Long id){
        return boardRepository.findById(id).get();
    }
}
