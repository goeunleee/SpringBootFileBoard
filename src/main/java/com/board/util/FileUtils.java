package com.board.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.board.domain.AttachDTO;
import com.board.exception.AttachFileException;

@Component
public class FileUtils {

	private final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")); //파일 업로드일자 
	
	private final String uploadPath = Paths.get("C:", "develop", "upload",today).toString(); //업로드할 파일 경로 c드라이브-develop-upload-업로드일자


	private final String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", ""); //랜덤 문자열 리턴 
	}

	public List<AttachDTO> uploadFiles(MultipartFile[] files, Long boardIdx) { //파일 배열, 게시글 번호

		List<AttachDTO> attachList = new ArrayList<>();

		File dir = new File(uploadPath); // 파일경로폴더 없으면 생성~
		if (dir.exists() == false) {
			dir.mkdirs();
		}

		for (MultipartFile file : files) { // 파일 개수만큼
			if (file.getSize() < 1) {
				continue;
			}
			try {

				final String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 확장자

				final String saveName = getRandomString() + "." + extension; // 파일저장명으로 랜덤 문자열 생성

				File target = new File(uploadPath, saveName); // 업로드경로에 savefile명으로 파일 생성
				file.transferTo(target);

				AttachDTO attach = new AttachDTO();
				attach.setBoardIdx(boardIdx);
				attach.setOriginalName(file.getOriginalFilename());
				attach.setSaveName(saveName);
				attach.setSize(file.getSize());

				attachList.add(attach);

			} catch (IOException e) {
				throw new AttachFileException("[" + file.getOriginalFilename() + "] failed to save file...");

			} catch (Exception e) {
				throw new AttachFileException("[" + file.getOriginalFilename() + "] failed to save file...");
			}
		} // for 끝

		return attachList; //업로드할 파일 목록 리턴 
	}

	
}
