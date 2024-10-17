package ac.kopo.kr.realglowadmin.config;

import ac.kopo.kr.realglowadmin.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/Auth/login", "/Auth/logout").permitAll() // 회원가입 및 로그인 페이지 접근 허용
                        .requestMatchers("/admin/**").authenticated() // 기타 admin URL은 인증 필요
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/Auth/login") // 로그인 페이지 설정
                        .defaultSuccessUrl("/", true) // 로그인 성공 시 리다이렉트
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/Auth/logout") // 로그아웃 URL 설정
                        .logoutSuccessUrl("/") // 로그아웃 성공 후 리다이렉트
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션 관리 정책
                )
                .httpBasic(Customizer.withDefaults()); // HTTP 기본 인증 설정

        return http.build();
    }
}
