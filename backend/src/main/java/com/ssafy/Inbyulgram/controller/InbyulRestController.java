package com.ssafy.Inbyulgram.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.Inbyulgram.entity.Content;
import com.ssafy.Inbyulgram.repository.ContentRepository;

@RestController
@RequestMapping("/content")
public class InbyulRestController {

	private static final Logger logger = LoggerFactory.getLogger(InbyulRestController.class);
	
	private static final String SereverURL = "http://localhost:8080/";
	
	@Autowired
	ContentRepository contentRepository;
	
	@GetMapping
	public ResponseEntity<List<Content>> list(){
		List<Content> clist = contentRepository.findAll();
		
		logger.debug(Integer.toString(clist.size()));
		
		if(clist != null && !clist.isEmpty()) {
			return new ResponseEntity<List<Content>>(clist, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Content>>(clist, HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping
	public ResponseEntity<String> posting(@RequestParam("picture") MultipartFile pic,
			@RequestParam("title") String title,
			@RequestParam("password") String password) throws IllegalStateException, IOException{
		
		String path = System.getProperty("user.dir"); // 현재 서버의 로컬경로 가져옴.. 
		File file = new File(path + "/src/main/resources/static/img/" + pic.getOriginalFilename());
		
		// 상위 폴더가 존재하지 않으면 새로 폴더 생성
		if(file.getParentFile().exists() == false) {
			file.getParentFile().mkdir();
		}
		
		// 서버로 이미지 업로드
		pic.transferTo(file);
		
		// 인별 게시물 객체 생성
		Content content = new Content(SereverURL + "img/" + pic.getOriginalFilename(), title, password);
		
		// 객체 DB에 저장
		contentRepository.save(content);
		
		logger.debug(pic.getOriginalFilename());
		
		return new ResponseEntity<String>(pic.getOriginalFilename(), HttpStatus.OK);
	}
}
