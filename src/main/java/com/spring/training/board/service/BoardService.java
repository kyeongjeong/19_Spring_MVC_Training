package com.spring.training.board.service;

import java.util.List;

import com.spring.training.board.dto.BoardDTO;

public interface BoardService {

	public void addBoard(BoardDTO boardDTO) throws Exception;	
	public List<BoardDTO> getBoardList() throws Exception;
	public BoardDTO getBoardDetail(long boardId) throws Exception;
	public boolean checkAuthorizedUser(BoardDTO boardDTO) throws Exception;
	public void modifyBoard(BoardDTO boardDTO) throws Exception;
	public void removeBoard(long boardId) throws Exception;
}
