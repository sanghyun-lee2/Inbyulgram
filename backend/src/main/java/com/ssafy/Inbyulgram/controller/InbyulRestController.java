package com.ssafy.Inbyulgram.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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
		// uid 기준으로 내림차순 정렬한 결과 반환
		List<Content> clist = contentRepository.findAll(Sort.by(Sort.Direction.DESC, "uid"));
		
		logger.debug(Integer.toString(clist.size()));
		
		if(clist != null && !clist.isEmpty()) {
			return new ResponseEntity<List<Content>>(clist, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Content>>(clist, HttpStatus.NO_CONTENT);
		}
	}
	
//	@PostMapping
//	public ResponseEntity<String> posting(@RequestPart("picture") MultipartFile pic,
//			@RequestParam("title") String title,
//			@RequestParam("password") String password) throws IllegalStateException, IOException{
//		
//		String path = System.getProperty("user.dir"); // 현재 서버의 로컬경로 가져옴.. 
//		File file = new File(path + "/src/main/resources/static/img/" + pic.getOriginalFilename());
//		
//		// 상위 폴더가 존재하지 않으면 새로 폴더 생성
//		if(file.getParentFile().exists() == false) {
//			file.getParentFile().mkdir();
//		}
//		
//		// 서버로 이미지 업로드
//		pic.transferTo(file);
//		
//		// 인별 게시물 객체 생성
//		Content content = new Content(SereverURL + "img/" + pic.getOriginalFilename(), title, password);
//		
//		// 객체 DB에 저장
//		contentRepository.save(content);
//		
//		logger.debug(pic.getOriginalFilename());
//		
//		return new ResponseEntity<String>(pic.getOriginalFilename(), HttpStatus.OK);
//	}
	
	@PostMapping
	public Map<String, String> posting(@RequestPart("picture") MultipartFile pic,
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
		
		return Map.of("path", pic.getOriginalFilename());
	}
	
	@PutMapping("/{uid}")
	public Map<String, String> update(@PathVariable int uid, @RequestPart("picture") MultipartFile pic,
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
		
		// uid에 해당되는 객체 DB에서 삭제
		contentRepository.deleteById(uid);
		
		// 객체 DB에 저장
		contentRepository.save(content);
		
		return Map.of("path", pic.getOriginalFilename());
	}
	
	@DeleteMapping("/{uid}")
	public void delete(@PathVariable int uid, @RequestBody Map<String, Object> body) {
		// 패스워드 동일하면 DB에서 해당 게시물 삭제
		if(body.get("password").toString().equals(contentRepository.getById(uid).getPassword())) {
			contentRepository.deleteById(uid);
		}
	}
}
