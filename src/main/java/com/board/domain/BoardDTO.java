package com.board.domain;

import java.util.List;

public class BoardDTO extends CommonDTO {

	private Long idx;
	
	private String title;

	private String content;

	private String writer;

	private int viewCnt;

	private String noticeYn;

	private String secretYn;
	
	private String changeYn;
	
	private List<Long> fileIdxs; //파일목록

	public Long getIdx() {
		return idx;
	}

	public void setIdx(Long idx) {
		this.idx = idx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public String getNoticeYn() {
		return noticeYn;
	}

	public void setNoticeYn(String noticeYn) {
		this.noticeYn = noticeYn;
	}

	public String getSecretYn() {
		return secretYn;
	}

	public void setSecretYn(String secretYn) {
		this.secretYn = secretYn;
	}

	public String getChangeYn() {
		return changeYn;
	}

	public void setChangeYn(String changeYn) {
		this.changeYn = changeYn;
	}

	public List<Long> getFileIdxs() {
		return fileIdxs;
	}

	public void setFileIdxs(List<Long> fileIdxs) {
		this.fileIdxs = fileIdxs;
	}

	

}