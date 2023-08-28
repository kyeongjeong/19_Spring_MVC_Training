package com.spring.training.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.training.board.dao.BoardDAO;
import com.spring.training.board.dto.BoardDTO;

/*
 
	# 패스워드 암호화
	
	1) pom.xml파일에 dependency를 추가한다.
	
		<dependency>
		    <groupId>org.springframework.security</groupId>
		    <artifactId>spring-security-web</artifactId>
		    <version>5.0.8.RELEASE</version>
		</dependency>
		
		<dependency>
		    <groupId>org.springframework.security</groupId>
		    <artifactId>spring-security-config</artifactId>
		    <version>5.0.8.RELEASE</version>
		</dependency>
	
	2) servlet-context.xml에 아래의 빈 선언을 추가한다.
	
		<beans:bean id="bcryptPasswordEncoder" class="org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder" /> 
	
	
	3) 서비스 로직에서 BCryptPasswordEncoder 객체를 생성한다.
	
		@Autowired
		private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	4) 사용법
	
		- bCryptPasswordEncoder.encode(암호화하고 싶은 패스워드)   				// encode(평문)메서드를 통하여 패스워드를 암호화한다.
		- bCryptPasswordEncoder.matches(입력받은 패스워드, 암호화된 패스워드) 	// matches(평문,암호문) 메서드를 통하여 입력받은 패스워드와 암호화된 패스워드를 비교한다.
		- 암호화된 패스워드를 복호화하는 메서드는 존재하지 않는다.		

*/

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDAO;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void addBoard(BoardDTO boardDTO) throws Exception {

		//System.out.println("Service : " + boardDTO);
		
		// 패스워드 암호화
		boardDTO.setPasswd(bCryptPasswordEncoder.encode(boardDTO.getPasswd()));
		
		boardDAO.insertBoard(boardDTO);
	}
	
	@Override
	public List<BoardDTO> getBoardList() throws Exception {
		
		//List<BoardDTO> boardList = boardDAO.selectListBoard();
		//return boardList;
		
		return boardDAO.selectListBoard();
	
	}

	@Override
	@Transactional
	@Scheduled(cron="초 분 시 일 월 요일 (년)")
	public BoardDTO getBoardDetail(long boardId) throws Exception {
		
		boardDAO.updateReadCnt(boardId);
		return boardDAO.selectOneBoard(boardId);
	}

	@Override
	public boolean checkAuthorizedUser(BoardDTO boardDTO) throws Exception {

		String rawPassword = boardDTO.getPasswd();
		String encodedPassword = boardDAO.selectOnePasswd(boardDTO.getBoardId());
		
		boolean isAuthorizedUser = false;
		if (bCryptPasswordEncoder.matches(rawPassword, encodedPassword)) {
			isAuthorizedUser = true;
		}
		
		return isAuthorizedUser;
	}

	@Override
	public void modifyBoard(BoardDTO boardDTO) throws Exception {

		boardDAO.updateBoard(boardDTO);
	}
  
	@Override
	public void removeBoard(long boardId) throws Exception {

		boardDAO.deleteBoard(boardId);
	}
}
