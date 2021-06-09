package com.board.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Infinispan;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.board.domain.AttachDTO;
import com.board.domain.BoardDTO;
import com.board.mapper.AttachMapper;
import com.board.mapper.BoardMapper;
import com.board.paging.PaginationInfo;
import com.board.util.FileUtils;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;

	@Autowired
	private AttachMapper attachMapper;

	@Autowired
	private FileUtils fileUtils;

	@Override
	public boolean registerBoard(BoardDTO params) {//파일 포함 x
		int queryResult = 0;

		if (params.getIdx() == null) {
			queryResult = boardMapper.insertBoard(params); //null인 경우 새로운 파일 추가 
		} else {
			queryResult = boardMapper.updateBoard(params); //존재하는 경우 수정 
			
			
			if("Y".equals(params.getChangeYn())) {          //파일이 추가, 삭제, 변경된 경우
				attachMapper.deleteAttach(params.getIdx());//idx 일치하는 파일의 delete_yn을 y로 변경하는 쿼리 실행
			}
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public boolean registerBoard(BoardDTO params, MultipartFile[] files) { //파일 존재하는 경우.. 
		int queryResult = 1;

		if (registerBoard(params) == false) {
			return false;
		}

		List<AttachDTO> fileList = fileUtils.uploadFiles(files, params.getIdx());
		if (CollectionUtils.isEmpty(fileList) == false) {
			queryResult = attachMapper.insertAttach(fileList);
			if (queryResult < 1) {
				queryResult = 0;
			}
		}

		return (queryResult > 0);
	}

	@Override
	public BoardDTO getBoardDetail(Long idx) {
		return boardMapper.selectBoardDetail(idx);
	}

	@Override
	public boolean deleteBoard(Long idx) {
		int queryResult = 0;

		BoardDTO board = boardMapper.selectBoardDetail(idx);

		if (board != null && "N".equals(board.getDeleteYn())) {
			queryResult = boardMapper.deleteBoard(idx);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public List<BoardDTO> getBoardList(BoardDTO params) {
		List<BoardDTO> boardList = Collections.emptyList();

		int boardTotalCount = boardMapper.selectBoardTotalCount(params);

		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(boardTotalCount);

		params.setPaginationInfo(paginationInfo);

		if (boardTotalCount > 0) { //게시글 1개 이상일 때 게시글 리스트 출력 
			boardList = boardMapper.selectBoardList(params);
		}

		return boardList;
	}
	
	@Override
	public List<AttachDTO> getAttachFileList(Long boardIdx){
		
		int fileTotalCount = attachMapper.selectAttachTotalCount(boardIdx);
		if (fileTotalCount <1) {
			return Collections.emptyList(); //파일 개수 조회 후, 파일 개수 1개 이상이면 boardidx에 해당하는 파일리스트 리턴 
		}
		return attachMapper.selectAttachList(boardIdx);
	}
	
	@Override
	public AttachDTO getAttachDetail(Long idx) {
		
		return attachMapper.selectAttachDetail(idx);
	}

}