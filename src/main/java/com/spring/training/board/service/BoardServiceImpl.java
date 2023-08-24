package com.spring.training.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.training.board.dao.BoardDAO;
import com.spring.training.board.dto.BoardDTO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDAO;

	@Override
	public void addBoard(BoardDTO boardDTO) {

		//System.out.println("Service : " + boardDTO);	
		boardDAO.insertBoard(boardDTO);
	}
}
