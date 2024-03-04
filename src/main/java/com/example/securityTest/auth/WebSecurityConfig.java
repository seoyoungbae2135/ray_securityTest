package com.example.securityTest.auth;

import com.example.securityTest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

//20240222-3
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    UserService userService;

    @Autowired  //20240222-6
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ // http:매개변수
          //http.formLogin().disable(); //시큐리티 로그인폼 비활성화

        /*http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll().and()
                .logout().permitAll(); */  //커스텀 로그인 페이지 사용

        http.authorizeRequests() // 20240222-4
                /*.antMatchers("/").permitAll()*/ // .permitAll() - 모든것을 허용한다 . 1개씩 경로설정
                /*.antMatchers("/image/**").permitAll()*/  // 1개씩 경로 설정
                .mvcMatchers("/image/**","/css/**","/javascript/**").permitAll()  // 여러경로 설정
                .mvcMatchers("/","/guest/**", "/join").permitAll()
                .mvcMatchers("/member/**").hasAnyRole("USER","ADMIN") //규칙을 설정하여 인가 "USER","ADMIN"은 이름, 변경가능
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();  // 페이지에 대한 접근권한.

        http //20240222-5
                .formLogin()
                    .loginPage("/login") //커스텀 로그인 주소
                    .loginProcessingUrl("/login_chk") // 로그인 처리 주소
                    //.failureUrl("/login?error") //로그인 실패시 이동 주소 20240222-6에 주석처리
                    .failureHandler(authenticationFailureHandler) //20240222-6
                    .defaultSuccessUrl("/",true) //로그인성공시 이동할 페이지
                    .usernameParameter("id") //커스텀 html에 input 태그 name값
                    .passwordParameter("pw") //커스텀 html에 input 태그 name값
                    .permitAll()
                    .and()
                .logout().logoutUrl("/logout")
                    .logoutSuccessUrl("/") //로그아웃 성공시 이동할 페이지
                    .invalidateHttpSession(true) //세션제거
                    .permitAll();

        http.logout().permitAll();

        http.csrf().disable(); // 토큰인증, 클라이언트와 서버의 상호작용으로 사용, 정상적인 방법으로
                               // 접근하고 있는가 확인용. 여기서는 csrf 사용안함 선언
        // 1' or 1='1'
// select * from member where id='aaa' and pw='1' or 1='1';  과거 서버 공격방법 비밀번호 크랙, 현재는 사용불가

        return http.build();
    }

    /*@Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("1234"))
                .roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("1234"))
                .roles("ADMIN");
    }*/
    /*@Bean
    public UserDetailsService userDetailsService(){
        //UserDetailsManager를 사용하여 간단한 사용자 정보를 메모리에 저장
        return new InMemoryUserDetailsManager(
                User.withUsername("user").password("{noop}1234").roles("USER").build(),
                User.withUsername("admin").password("{noop}1234").roles("ADMIN").build()
        );*/
        //DB 조회작업도 여기서 할수있다

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    }



