package com.spring.training.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.training.board.dto.BoardDTO;
import com.spring.training.board.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/addBoard")
	public ModelAndView addBoard() {
		
		return new ModelAndView("board/addBoard");
	}
	
	@PostMapping("/addBoard")
	@ResponseBody
	public String addBoard(@ModelAttribute BoardDTO boardDTO) throws Exception {
		
		//단위 테스트
		//System.out.println(boardDTO);
		
		boardService.addBoard(boardDTO);
		
		String jsScript = "<script>";
		jsScript += "alert('Post Added');";
		jsScript += "location.href='addBoard';";
		jsScript += "</script>";
		
		return jsScript;
	}
	
	@GetMapping("/boardList")
	public ModelAndView boardList() throws Exception {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/boardList");
		
		//List<BoardDTO> boardList = boardService.getBoardList();
		//mv.addObject("boardList" , boardList);
		
		mv.addObject("boardList" , boardService.getBoardList());
		
		return mv;	
	}
	
	
	@GetMapping("/boardDetail")
	public ModelAndView boardDetail(@RequestParam("boardId") long boardId) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/boardDetail");
		mv.addObject("boardDTO", boardService.getBoardDetail(boardId));
		
		return mv;
	}
	
	@GetMapping("/authentication")
	public ModelAndView authentication(@RequestParam("boardId") long boardId,
									   @RequestParam("menu") String menu) throws Exception {	
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/authentication");
		mv.addObject("boardDTO", boardService.getBoardDetail(boardId));
		mv.addObject("menu", menu);
		
		return mv;	
	}
	
	@PostMapping("/authentication")
	@ResponseBody
	public String authentication(@ModelAttribute BoardDTO boardDTO,
								 @RequestParam("menu") String menu) throws Exception {
		
		String jsScript = "";
		if (boardService.checkAuthorizedUser(boardDTO)) {
			if (menu.equals("update")) {
			   jsScript = "<script>";
			   jsScript += "location.href='modifyBoard?boardId=" + boardDTO.getBoardId() + "';";
			   jsScript += "</script>";
			}
			else if (menu.equals("delete")) {
			   jsScript = "<script>";
			   jsScript += "location.href='removeBoard?boardId=" + boardDTO.getBoardId() + "';";
			   jsScript += "</script>";
			}
			
		}
		else {
			   jsScript = "<script>";
			   jsScript += "alert('Check your Id or Password');";
			   jsScript += "history.go(-1);";
			   jsScript += "</script>";
		}
		
		return jsScript;		
	}
	
	@GetMapping("/modifyBoard")
	public ModelAndView modifyBoard(@RequestParam("boardId") long boardId) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/modifyBoard");
		mv.addObject("boardDTO", boardService.getBoardDetail(boardId));
		
		return mv;
	}
	
	@PostMapping("/modifyBoard")
	@ResponseBody
	public String modifyBoard(@ModelAttribute BoardDTO boardDTO) throws Exception {
		
		boardService.modifyBoard(boardDTO);
		
		String jsScript = "<script>";
		jsScript += "alert('It's Changed');";
		jsScript += "location.href='addBoard';";
		jsScript += "</script>";
		return jsScript;
	}
	
	@GetMapping("/removeBoard")
	public ModelAndView removeBoard(@RequestParam("boardId") long boardId) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/removeBoard");
		mv.addObject("boardId", boardId);
		
		return mv;	
	}
		
	@PostMapping("/removeBoard")
	@ResponseBody
	public String postRemoveBoard(@RequestParam("boardId") long boardId) throws Exception {
		
		boardService.removeBoard(boardId);
		
		String jsScript = "<script>";
			   jsScript += "alert('It has been deleted.');";
			   jsScript += "location.href='boardList';";
			   jsScript += "</script>";
	
		return jsScript;	
	}
}
