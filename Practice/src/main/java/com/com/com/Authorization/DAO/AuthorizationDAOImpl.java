package com.com.com.Authorization.DAO;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.com.com.Authorization.VO.BoardRequest;
import com.com.com.Authorization.VO.BoardResponse;
import com.com.com.Authorization.VO.MemberVO;
import com.com.com.Authorization.VO.ProxyRequest;
import com.com.com.Authorization.VO.ProxyResponse;


@Repository("authorizationDAO")
public class AuthorizationDAOImpl implements AuthorizationDAO{

	@Inject
	SqlSession sqlSession;
	
	@Override
	public MemberVO login(Map<String, Object> params) {
		return sqlSession.selectOne("mapper.login", params);
	}
	
	@Override
	public ProxyResponse proxy(Map<String, Object> params) {
		if(sqlSession.selectOne("mapper.hasProxy", params).equals(0)) {
			return null;
		}
		return sqlSession.selectOne("mapper.proxy", params);
	}
	
	@Override
	public List<Map<String, Object>> proxyList(Map<String, Object> params) {
		System.out.println(sqlSession.selectList("mapper.nonProxy", params));
		return sqlSession.selectList("mapper.nonProxy", params);
	}
	
	@Override
	public void giveProxy(ProxyRequest params) {
		sqlSession.insert("mapper.giveProxy", params);
	}
	
	@Override
	public List<BoardResponse> list(Map<String, Object> params) {
		System.out.println("DAO로 넘어온 데이터 = " + params);
		return sqlSession.selectList("mapper.list", params);
	}

	@Override
	public BoardResponse view(int postId) {
		System.out.println("DAO postId 전달, 넘겨준 데이터 = " + postId);
		System.out.println("가져온 데이터 = " + sqlSession.selectOne("mapper.view", postId));
		return sqlSession.selectOne("mapper.view", postId);
	}

	@Override
	public List<Map<String, Object>> history(int postId) {
		System.out.println("history에서 가져온 데이터 = " + sqlSession.selectList("mapper.history", postId));
		return sqlSession.selectList("mapper.history", postId);
	}

	@Override
	public int lastSeq() {
		System.out.println("return = " + sqlSession.selectOne("mapper.lastSeq"));
		return sqlSession.selectOne("mapper.lastSeq");
	}

	@Override
	public void save(BoardRequest params) {
		sqlSession.insert("mapper.save", params);
		if(!params.getConfirmStatus().equals("TEM")) {
			sqlSession.insert("mapper.creHistory", params);
		}
	}
	
	@Override
	public void update(BoardRequest params) {
		sqlSession.update("mapper.update", params);
		if(!params.getConfirmStatus().equals("TEM")) {
			sqlSession.insert("mapper.creHistory", params);
		}
	}

	@Override
	public void reject(Map<String, Object> params) {
		sqlSession.update("mapper.reject", params);
	}

	@Override
	public void confirm(Map<String, Object> params) {
		sqlSession.update("mapper.confirm", params);		
	}

	@Override
	public void creHistory(Map<String, Object> params) {
		sqlSession.insert("mapper.creHistory", params);
	}

	
}
