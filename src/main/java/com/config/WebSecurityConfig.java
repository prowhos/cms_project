package com.config;

import com.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    SessionRegistry sessionRegistry;

    @Bean
    public SessionRegistry getSessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }

    //使用注解在配置类中直接实例化对象
    @Bean
    public UserDetailsService customUserService() {
        return new CustomUserService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable(); //解决跨域的问题
        http
                .authorizeRequests()
                .antMatchers("/assets/**","/js/**","/public/**","/front/**").permitAll()
                .antMatchers("/f/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/a/user/login")
                .defaultSuccessUrl("/a/index",true)
                .failureUrl("/a/user/login?error")
                .permitAll()
                .and()
                .logout().permitAll().invalidateHttpSession(true)
                .and().sessionManagement().maximumSessions(10).expiredUrl("/a/user/login");
    }
    //AuthenticationManagerBuilder验证前台提交的数据
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //BCryptPasswordEncoder指定密码的验证规则
        auth.userDetailsService(customUserService()).passwordEncoder(new BCryptPasswordEncoder());
    }
}
