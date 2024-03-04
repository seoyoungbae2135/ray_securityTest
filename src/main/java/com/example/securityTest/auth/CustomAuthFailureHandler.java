package com.example.securityTest.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

//20240222-6
@Configuration
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String loginid = request.getParameter("id");
        String errormsg="";

        if
        ( exception instanceof BadCredentialsException){
            errormsg="아이디나 비밀번호가 맞지않습니다. 다시확인해주세요";
        }else if( exception instanceof InternalAuthenticationServiceException){
            errormsg = "아이디나 비밀번호가 맞지않습니다. 다시확인해주세요";
        }else if( exception instanceof DisabledException){
            errormsg = "계정이 비활성화 되었습니다. 관리자에게 문의하세요";
        }else if( exception instanceof CredentialsExpiredException){
            errormsg = " 비밀번호 유효기간이 만료되었습니다. 관리자에게 문의하세요";
        }
        /*errormsg = URLEncoder.encode(errormsg, "UTF-8");
        setDefaultFailureUrl("login?error=true&error_mssage="+ errormsg);
        super.onAuthenticationFailure(request,response,exception);*/
        // 20240222-7 수정
        /*request.setAttribute("username",loginid);
        request.setAttribute("error_message",errormsg);*/
        request.getRequestDispatcher("/login?error=true&error_message="+errormsg)
                .forward(request,response);
    }
}
