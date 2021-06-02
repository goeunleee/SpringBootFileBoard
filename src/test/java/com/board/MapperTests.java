package com.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
class MapperTests {

	@Autowired
	private BoardMapper boardMapper;

	@Test
	public void testOfInsert() { //게시물 insert
		BoardDTO params = new BoardDTO();
		params.setTitle("3번 게시글 제목");
		params.setContent("3번 게시글 내용");
		params.setWriter("테스터");

		int result = boardMapper.insertBoard(params);
		System.out.println("결과는 " + result + "입니다.");
	}
	
	
	@Test
	public void testOfSelectDetail() { //특정게시물 조회 
		BoardDTO board = boardMapper.selectBoardDetail((long) 3);
		try {
			String boardJson = new ObjectMapper().writeValueAsString(board);

			System.out.println("============insert=============");
			System.out.println(boardJson);
			System.out.println("=========================");

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
		
	

	
	@Test
	public void testOfUpdate() { //수정 테스트
		BoardDTO params = new BoardDTO();
		params.setTitle("1번 게시글 제목을 수정합니다.");
		params.setContent("1번 게시글 내용을 두 번째 수정합니다.");
		params.setWriter("이고은");
		params.setIdx((long) 1);

		int result = boardMapper.updateBoard(params);
		if (result == 1) {
			BoardDTO board = boardMapper.selectBoardDetail((long) 1);
			try {
				String boardJson = new ObjectMapper().writeValueAsString(board);

				System.out.println("==========update===============");
				System.out.println(boardJson);
				System.out.println("=========================");

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testOfDelete() { //delete_yn y로 변경..
		int result = boardMapper.deleteBoard((long) 2);
		if (result == 1) {
			BoardDTO board = boardMapper.selectBoardDetail((long) 2);
			try {
				String boardJson = new ObjectMapper().writeValueAsString(board);

				System.out.println("=========delete================");
				System.out.println(boardJson+"이 삭제됨..");
				System.out.println("=========================");

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@Test
	public void testSelectList() { //삭제되지 않은 게시물 조회 delete_yn = n인 것
		int boardTotalCount = boardMapper.selectBoardTotalCount();
		if (boardTotalCount > 0) {
			List<BoardDTO> boardList = boardMapper.selectBoardList();
			if (CollectionUtils.isEmpty(boardList) == false) {
				for (BoardDTO board : boardList) {
					System.out.println("============삭제 안 된 게시물 리스트=============");
					System.out.println("title:"+board.getTitle());
					System.out.println("writer:"+board.getWriter());
					System.out.println("content:"+board.getContent());
					System.out.println("=========================");
				}
			}
		}
	}

}