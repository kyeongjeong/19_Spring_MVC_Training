package com.spring.training.board.service;

import java.util.List;

import com.spring.training.board.dto.BoardDTO;

public interface BoardService {

	public void addBoard(BoardDTO boardDTO);	
	public List<BoardDTO> getBoardList();
	public BoardDTO getBoardDetail(long boardId);
}
