package com.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.board.constant.Method;
import com.board.domain.BoardDTO;
import com.board.paging.Criteria;
import com.board.service.BoardService;
import com.board.util.UiUtils;

@Controller
public class BoardController extends UiUtils {

	@Autowired
	private BoardService boardService;

	@GetMapping(value = "/board/write.do")
	public String openBoardWrite(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
		if (idx == null) {
			model.addAttribute("board", new BoardDTO());
		} else {
			BoardDTO board = boardService.getBoardDetail(idx);
			if (board == null || "Y".equals(board.getDeleteYn())) {
				return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.", "/board/list.do", Method.GET, null, model);
			}
			model.addAttribute("board", board); // addAttribute : 이 메서드를 통해 html로 데이터를 전달한다. 인자는 이름,값 형태
		}

		return "board/write";
	}

	@PostMapping(value = "/board/register.do")
	public String registerBoard(@ModelAttribute("params") final BoardDTO params, Model model) {
		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			boolean isRegistered = boardService.registerBoard(params);
			if (isRegistered == false) {
				return showMessageWithRedirect("게시글 등록에 실패하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
		}

		return showMessageWithRedirect("게시글 등록이 완료되었습니다.", "/board/list.do", Method.GET, pagingParams, model);
	}

	// ModelAttribute를 쓰면 파라미터로 받은 객체를 자동으로 뷰까지 전달 가능.
	@GetMapping(value = "/board/list.do")
	public String openBoardList(@ModelAttribute("params") BoardDTO params, Model model) {
		List<BoardDTO> boardList = boardService.getBoardList(params);
		model.addAttribute("boardList", boardList);

		return "board/list";
	}

	@GetMapping(value = "/board/view.do") // 파라미터로 전달받은 객체를 자동으로 뷰로 전달 Criteria클래스를 상속받는 BoardDTO커멘드 객체를 뷰로 전달함.
	public String openBoardDetail(@RequestParam(value = "idx", required = false) Long idx, Model model,
			@ModelAttribute("params") BoardDTO params) {
		if (idx == null) {
			System.out.println("올바르지 않은 접근 발생");
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/board/list.do", Method.GET, null, model);
		}
		BoardDTO board = boardService.getBoardDetail(idx);
		if (board == null || "Y".equals(board.getDeleteYn())) {
			System.out.println("존재하지 않거나 삭제된 게시글에 접근 시도");
			return showMessageWithRedirect("존재하지 않는 게시글입니다.", "/board/list.do", Method.GET, null, model);
		}
		model.addAttribute("board", board);

		return "board/view";
	}

	@PostMapping(value = "/board/delete.do")
	public String deleteBoard(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
		if (idx == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/board/list.do", Method.GET, null, model);
		}

		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			boolean isDeleted = boardService.deleteBoard(idx);
			if (isDeleted == false) {
				return showMessageWithRedirect("게시글 삭제에 실패하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
		}

		return showMessageWithRedirect("게시글 삭제가 완료되었습니다.", "/board/list.do", Method.GET, pagingParams, model);
	}
}