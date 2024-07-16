package com.beyond.basic.servletjsp;

import com.beyond.basic.domain.Hello;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// controller가 아닌 webServlet 을 통해서 라우팅
//메서드 단위가 이는 클래스 단위
@WebServlet("/hello/servlet/rest/get")
public class HelloServletRestGet extends HttpServlet {
    @Override
    // httpServletRequest 에는 사용자의 요청정보다 담겨있음
    // httpServletResponse 에는 사용자에게 응답정보를 담아줘야함
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Hello hello = new Hello();
        hello.setName("hongildong");
        hello.setEmail("hongildong@gmail.com");
        hello.setPassword("12345678");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        // 직접 직렬화 필요!!(컨트롤러에서는 responseBody을 통해서 직렬화)
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(hello);
        // 응답바디 생성
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);

        //버퍼를 통해서 데이터가 조립되므로 버퍼를 비우는 과정
        printWriter.flush();
    }
}
