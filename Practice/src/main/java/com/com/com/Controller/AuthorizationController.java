package com.com.com.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.com.com.Authorization.Service.AuthorizationService;
import com.com.com.Authorization.VO.BoardRequest;
import com.com.com.Authorization.VO.BoardResponse;
import com.com.com.Authorization.VO.MemberVO;
import com.com.com.Authorization.VO.ProxyRequest;
import com.com.com.Authorization.VO.ProxyResponse;
import com.com.com.Authorization.VO.SearchVO;
import sun.font.Script;


@Controller
public class AuthorizationController {

	LocalDateTime now = LocalDateTime.now();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	String formatedNow = now.format(formatter);

	@Autowired
	public AuthorizationService authorizationService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpSession session) {
		session.invalidate();
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam String loginId,
						@RequestParam String loginPw,
						HttpSession session, Model model) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginId", loginId);
		paramMap.put("loginPw", loginPw);

		MemberVO member = authorizationService.login(paramMap);
		model.addAttribute("user", member);
		String msg = "";
		if (member == null){
			msg = "없는 회원입니다.";
			model.addAttribute("msg", msg);
			return "alert";
		}else if (member.getUserPw().equals(loginPw) == false) {
			msg = "비밀번호가 일치하지 않습니다";
			model.addAttribute("msg", msg);
			return "alert";
		}
		session.setAttribute("memberVO", member);
		return "redirect:/post";
	}
	
	
	@RequestMapping(value = "/post")
	public String post(Model model, HttpServletRequest request , HttpServletResponse response
					 , SearchVO searchVO) {
		HttpSession session = request.getSession(false);
		MemberVO sessionData = (MemberVO) session.getAttribute("memberVO");
		if(sessionData == null) {
			return "redirect:/";
		}
		Map<String, Object> params = new HashMap<String, Object>(); // 검색을 위해 넘겨줄 params 작업
		int id = sessionData.getId();

		params.put("id", id);
		params.put("now", formatedNow);

		ProxyResponse proxyVO = authorizationService.proxy(params); // 대리권한 있는지 id번호로 조회

		session.setAttribute("session", sessionData);
		session.setAttribute("proxyVO", proxyVO);

		params.put("userRank", sessionData.getUserRank());
		if(proxyVO != null) {
			params.replace("userRank", proxyVO.getProxyRank());	// 만약 대리권한이 있다면 직급을 대리직급으로 덮어씌우기
		}
		params.put("userName", sessionData.getUserName());
		params.put("searchVO", searchVO);

		List<BoardResponse> list = authorizationService.list(params); //게시글 검색
		model.addAttribute("user", sessionData);
		model.addAttribute("proxy", proxyVO);
		model.addAttribute("list", list);
		if (list == null){
			System.out.println("list 비었는가 = " + list.isEmpty());
		}
		return "post";
	}
	
	
	// /post 페이지에서 AJax로 검색했을 때 호출
	@RequestMapping(value = "/search")
	@ResponseBody													
	public List<BoardResponse> search(Model model, HttpServletRequest request , HttpServletResponse response
					   , SearchVO searchVO) {
		HttpSession session = request.getSession();
		MemberVO sessionData = (MemberVO) session.getAttribute("memberVO"); // 세션값 가져오기
		if(sessionData == null) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		int id = sessionData.getId();
		params.put("id", id);
		params.put("now", formatedNow);
		ProxyResponse proxyVO = authorizationService.proxy(params);
		session.setAttribute("session", sessionData);


		params.put("userRank", sessionData.getUserRank());
		if(proxyVO != null) {
			params.replace("userRank", proxyVO.getProxyRank());
		}
		params.put("userName", sessionData.getUserName());
		params.put("searchVO", searchVO);

		List<BoardResponse> list = authorizationService.list(params);
		return list;
		
	}

	@RequestMapping(value = "/write")
	public String write(@RequestParam(
						value = "postId"
						, required = false, defaultValue = "0") int postId
						, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		MemberVO sessionData = (MemberVO) session.getAttribute("memberVO");
		if(sessionData == null) {
			return "redirect:/";
		}
		int lastSeq = authorizationService.lastSeq();
		model.addAttribute("lastSeq",lastSeq);
		if(postId != 0) {
			BoardResponse post = authorizationService.view(postId);
			model.addAttribute("post", post);
		}
		return "write";
	}
	
	// 새로운 글 생성
	@RequestMapping(value = "/save")
	public String save(BoardRequest boardRequest, HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberVO sessionData = (MemberVO) session.getAttribute("memberVO");
		if(sessionData == null) {
			return "redirect:/";
		}
		int writerPkNum = sessionData.getId();
		boardRequest.setWriterPkNum(writerPkNum);
		authorizationService.save(boardRequest);
		return "redirect:/post";
	}
	
	// 기존에 글이 있을 시 수정
	@RequestMapping(value = "/update")
	public String update(BoardRequest boardRequest, HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberVO sessionData = (MemberVO) session.getAttribute("memberVO");
		if(sessionData == null) {
			return "redirect:/";
		}

		int writerPkNum = sessionData.getId();
		boardRequest.setWriterPkNum(writerPkNum);
		authorizationService.update(boardRequest);
		
		return "redirect:/post";
	}
	
	
	@RequestMapping(value = "/reject")
	public String reject(@RequestParam int postId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberVO sessionData = (MemberVO) session.getAttribute("memberVO");
		if(sessionData == null) {
			return "redirect:/";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		int id = sessionData.getId();
		params.put("id", id);
		params.put("now", formatedNow);
		ProxyResponse proxyVO = authorizationService.proxy(params);

		params.put("postId",postId);
		params.put("confirmPerson", id);
		params.put("confirmStatus", "REJ");
		if (proxyVO != null){
			params.put("proxyConfirmPerson", proxyVO.getProxyId());
		}
		authorizationService.reject(params);
		authorizationService.creHistory(params);
		return "redirect:/post";
	}
	
	
	@RequestMapping(value = "/confirm")
	public String confirm(@RequestParam int postId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberVO sessionData = (MemberVO) session.getAttribute("memberVO");
		if(sessionData == null) {
			return "redirect:/";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		int id = sessionData.getId();
		params.put("id", id);
		params.put("now", formatedNow);

		ProxyResponse proxyVO = authorizationService.proxy(params);
		String userRank = sessionData.getUserRank();

		if (proxyVO != null){
			userRank = proxyVO.getProxyRank();
			params.put("proxyConfirmPerson", proxyVO.getProxyId());
		}

		params.put("postId",postId);
		params.put("confirmPerson", id);


		if(userRank.equals("EXA")) {
			params.put("confirmStatus", "ING");
		}
		else if(userRank.equals("DH")) {
			params.put("confirmStatus", "FIN");
		}
		else {
			return "redirect:/post";
		}
		authorizationService.confirm(params);
		authorizationService.creHistory(params);
		return "redirect:/post";
	}
	
	// 상세 페이지
	@RequestMapping(value = "/view")
	public String view(@RequestParam int postId, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		MemberVO sessionData = (MemberVO) session.getAttribute("memberVO");
		if(sessionData == null) {
			return "redirect:/";
		}
		int id = sessionData.getId();
		BoardResponse post = authorizationService.view(postId);
		if(post.getConfirmStatus().equals("TEM")  && post.getWriterPkNum() == id) {
			model.addAttribute("post", post);
			return "write";
		}
		List<Map<String, Object>> postHistory = authorizationService.history(postId);
		model.addAttribute("post", post);
		model.addAttribute("history", postHistory);
		return "view";
	}
	
	// post페이지의 대리결재버튼 누르면 AJax 로 호출되는 함수
	@RequestMapping(value = "/viewProxy")
	@ResponseBody
	public List<Map<String, Object>> proxyList(@RequestParam String userRank) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String nowTime = sdf1.format(now);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userRank", userRank);
		params.put("nowTime", nowTime);
		List<Map<String, Object>> pxList = authorizationService.proxyList(params);
		return pxList;
	}
	
	// 대리권한 부여할 때 호출하는 함수
	@RequestMapping(value = "/giveProxy")
	public String giveProxy(@RequestParam int recipId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberVO sessionData = (MemberVO) session.getAttribute("memberVO");
		ProxyRequest params = new ProxyRequest();
		params.setId(recipId);
		params.setProxyId(sessionData.getId());
		params.setProxyName(sessionData.getUserName());
		authorizationService.giveProxy(params);
		return "redirect:/post";
	}
	
}
 