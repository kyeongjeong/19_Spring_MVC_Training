package com.spring.training.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.training.board.dto.BoardDTO;

@Repository
public class BoardDAOImpl implements BoardDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insertBoard(BoardDTO boardDTO) {

		//System.out.println("DAO : " + boardDTO);
		sqlSession.insert("boardMapper.insertBoard", boardDTO);
	}
	
	@Override
	public List<BoardDTO> selectListBoard() {
	
		//List<BoardDTO> boardList = sqlSession.selectList("boardMapper.selectListBoard");
		//return boardList;
		
		return sqlSession.selectList("boardMapper.selectListBoard");
		
	}

	@Override
	public BoardDTO selectOneBoard(long boardId) {
		return sqlSession.selectOne("boardMapper.selectOneBoard" , boardId);
	}
}
