package com.study.domain.resttime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@SessionAttributes("info")
@RequiredArgsConstructor
public class ResttimeController {

    private final ResttimeService resttimeService;

	
	 @PostMapping("/resttime/resttime/")
	 @ResponseBody
	 public String getRestAsMbti(final ResttimeRequest params, HttpServletRequest request) {
	 System.out.println("resttime/resttime 컨트롤러 진입. 넘어온 파라미터 : "+params);    
	 String method = resttimeService.getRestAsMbti(params); //유저의 mbti를 통해 휴식방법을
	 System.out.println(method);
	 return method;
	 }
	 
    
    @GetMapping("/resttime/resttime")
    public String hihi() {
    	return "resttime/resttime";
    }

}