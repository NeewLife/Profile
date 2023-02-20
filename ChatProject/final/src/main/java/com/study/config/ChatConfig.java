package com.study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//@Configuration이라고 하면 설정파일을 만들기 위한 어노테이션 or Bean을 등록하기 위한 어노테이션이다.
//@EnableWebSocketMessageBroker는 websocket 서버를 사용한다는 설정이다.
@Configuration
@EnableWebSocketMessageBroker
public class ChatConfig implements WebSocketMessageBrokerConfigurer{
	//WebSocketMessageBrokerConfigurer를 상속 받아 몇몇 메소드를 구현하여 websocket 연결 속성을 설정한다.
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		//registerStompEndpoints를 이용하여 클라이언트에서 websocket에 접속하는 endpoint를 등록한다.
        registry.addEndpoint("/ws/chatroom").setAllowedOriginPatterns("*").withSockJS(); 
        //addEndPoint():클라이언트에서 websocket에 접속하기 위한 endpoint를 등록
        //withSockJS()를 사용시에는 브라우져에서 websocket을 지원하지 않을 경우 fallback 옵션을 활성화 하는데 사용된다.
    }
	
	//configureMessageBroker는 한 클라이언트에서 다른 클라이언트로 메시지를 라우팅 할 때 사용하는 브로커를 구성한다.
	@Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.enableSimpleBroker("/queue", "/topic");
        // enableSimpleBroker(): 메모리 기반 메시지 브로커가 /topic접두사가 붙은 클라이언트로 메시지를 전달할 수 있도록 설정, 쉽게 이야기하면 클라이언트 A,B,C가 각각
        // /topic/master, /topic/sub, /topic/master를 구독하고 있을 때 /topic/master로 메시지를 전송하면 클라이언트 A,C만 메시지를 받는 구조이다.
        registry.setApplicationDestinationPrefixes("/app");
        // /app으로 접근하는 메시지만 핸들러로 라우팅
    }

}
