package com.com.com.Authorization.Service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.com.com.Authorization.DAO.AuthorizationDAO;
import com.com.com.Authorization.VO.BoardRequest;
import com.com.com.Authorization.VO.BoardResponse;
import com.com.com.Authorization.VO.MemberVO;
import com.com.com.Authorization.VO.ProxyRequest;
import com.com.com.Authorization.VO.ProxyResponse;


@Repository("authorizationService")
public class AuthorizationServiceImpl implements AuthorizationService{

	@Inject
	AuthorizationDAO authorizationDAO;

	/**
	 * 유저 정보 조회
	 * @return 유저 정보
	 */
	@Override
	public MemberVO login(Map<String, Object> params) {
		return authorizationDAO.login(params);
	};

	/**
	 * 대리권한 조회
	 * @return 대리권한 정보
	 */
	@Override
	public ProxyResponse proxy(Map<String, Object> params) {
		return authorizationDAO.proxy(params);
	}

	/**
	 * 대리권한 없는 인원 조회
	 * @return 대리권한 없는 인원 목록
	 */
	@Override
	public List<Map<String, Object>> proxyList(Map<String, Object> params) {
		return authorizationDAO.proxyList(params);
	}

	// 대리권한 부여
	@Override
	public void giveProxy(ProxyRequest params) {
		authorizationDAO.giveProxy(params);
	}

	/**
	 * 게시글 목록 조회
	 * @return 검색된 게시글 목록
	 */
	@Override
	public List<BoardResponse> list(Map<String, Object> params) {
		List<BoardResponse> list = authorizationDAO.list(params);
		if(list == null) {
			System.out.println("list가 비어있습니다");
		}
		return authorizationDAO.list(params);
	}

	/**
	 * 게시글 상세 조회
	 * @return 게시글
	 */
	@Override
	public BoardResponse view(int postId) {
		System.out.println("Service postId 전달");
		return authorizationDAO.view(postId);
	}

	/**
	 * 게시글 히스토리 목록
	 * @return 해당 게시글 히스토리 목록
	 */
	@Override
	public List<Map<String, Object>> history(int postId) {
		return authorizationDAO.history(postId);
	}

	/**
	 * 게시글 마지막 번호 조회
	 * @return 마지막 번호 + 1
	 */
	@Override
	public int lastSeq() {
		return authorizationDAO.lastSeq();
	}

	// 게시글 처음 생성
	@Override
	public void save(BoardRequest params) {
		authorizationDAO.save(params);
	}

	// 게시글 수정
	@Override
	public void update(BoardRequest params) {
		authorizationDAO.update(params);
	}

	// 결재 : 반려
	@Override
	public void reject(Map<String, Object> params) {
		authorizationDAO.reject(params);
	}

	// 결재 : 결재중 or 결재완료 (직급에 따라)
	@Override
	public void confirm(Map<String, Object> params) {
		authorizationDAO.confirm(params);
	}

	// 게시글 히스토리 생성
	@Override
	public void creHistory(Map<String, Object> params) {
		authorizationDAO.creHistory(params);
	}

	
	
}
