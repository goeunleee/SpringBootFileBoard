package com.board.controller;

//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.HashMap;

@RestController
public class TestController {
     
	@GetMapping(value = "/members")
	//@ResponseBody // public @ResponseBody String testByResponseBody()와 같이 리턴 타입 좌측에 지정 가능
	public Map<Integer, Object> testByResponseBody() {

		Map<Integer, Object> members = new HashMap<>();

		for (int i = 1; i <= 20; i++) {
			Map<String, Object> member = new HashMap<>();
			member.put("idx", i);
			member.put("nickname", i + "길동");
			member.put("height", i + 20);
			member.put("weight", i + 30);
			members.put(i, member);
		}

		return members;
	}
	//메서드에 @ResponseBody가 붙게 되면 스프링의 메시지 컨버터에 의해 html(화면)이 아닌 리턴 타입에 해당하는 데이터 자체를 리턴하게 됨
	
	
}
