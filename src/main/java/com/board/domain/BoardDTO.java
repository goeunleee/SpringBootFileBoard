package com.board.domain;



import lombok.Getter;
import lombok.Setter;
//import java.time.*;
import java.util.Date;
import java.util.List;
@Getter
@Setter
public class BoardDTO extends CommonDTO {

	/** 번호 (PK) */
	private Long idx;

	/** 제목 */
	private String title;

	/** 내용 */
	private String content;

	/** 작성자 */
	private String writer;

	/** 조회 수 */
	private int viewCnt;

	/** 공지 여부 */
	private String noticeYn;

	/** 비밀 여부 */
	private String secretYn;
	/**파일 변경 여부**/
	private String changeYnString;
	/**파일 인덱스 리스트**/
	private List<Long> fileIdxs;

 //commonDTO 상속받으므로 공통 멤버변수 삭제


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

	public String getChangeYnString() {
		return changeYnString;
	}

	public void setChangeYnString(String changeYnString) {
		this.changeYnString = changeYnString;
	}

	public List<Long> getFileIdxs() {
		return fileIdxs;
	}

	public void setFileIdxs(List<Long> fileIdxs) {
		this.fileIdxs = fileIdxs;
	}

	

}