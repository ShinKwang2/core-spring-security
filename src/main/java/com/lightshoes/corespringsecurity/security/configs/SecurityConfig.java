package com.lightshoes.corespringsecurity.security.configs;

import com.lightshoes.corespringsecurity.security.factory.UrlResourcesMapFactoryBean;
import com.lightshoes.corespringsecurity.security.filter.PermitAllFilter;
import com.lightshoes.corespringsecurity.security.handler.CustomAccessDeniedHandler;
import com.lightshoes.corespringsecurity.security.handler.CustomAuthenticationFailureHandler;
import com.lightshoes.corespringsecurity.security.handler.CustomAuthenticationSuccessHandler;
import com.lightshoes.corespringsecurity.security.metadatasource.CustomFilterInvocationSecurityMetadataSource;
import com.lightshoes.corespringsecurity.security.provider.CustomAuthenticationProvider;
import com.lightshoes.corespringsecurity.security.voter.IpAddressVoter;
import com.lightshoes.corespringsecurity.service.SecurityResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    private final UserDetailsService userDetailsService;

    private final AuthenticationDetailsSource authenticationDetailsSource;

    private final UrlResourcesMapFactoryBean urlResourcesMapFactoryBean;

    private final SecurityResourceService securityResourceService;

    private final String[] permitAllResources = {"/", "/login", "/user/login/**"};

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/users", "/login*").permitAll()
                .antMatchers("/mypage").hasRole("USER")
                .antMatchers("/messages").hasRole("MANAGER")
                .antMatchers("/config").hasRole("ADMIN")
                .anyRequest().authenticated()

        .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login_proc")
                .authenticationDetailsSource(authenticationDetailsSource)
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll();

        http
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .addFilterBefore(cutomFilterSecurityInterceptor(), FilterSecurityInterceptor.class)
        ;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
        accessDeniedHandler.setErrorPage("/denied");
        return accessDeniedHandler;
    }

    @Bean
    public FilterSecurityInterceptor cutomFilterSecurityInterceptor() throws Exception {

        FilterSecurityInterceptor filterSecurityInterceptor = new PermitAllFilter(permitAllResources);
        filterSecurityInterceptor.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource());
        filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased());
        filterSecurityInterceptor.setAuthenticationManager(authenticationManagerBean());
        return filterSecurityInterceptor;
    }

    @Bean
    public FilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource() throws Exception {
        return new CustomFilterInvocationSecurityMetadataSource(urlResourcesMapFactoryBean.getObject(), securityResourceService);
    }

    private AccessDecisionManager affirmativeBased() {
        return new AffirmativeBased(getVoters());
    }

    private List<AccessDecisionVoter<? extends Object>> getVoters() {
        return List.of(ipAddressVoter(), roleHierarchyVoter());
    }

    private AccessDecisionVoter<?> ipAddressVoter() {
        return new IpAddressVoter(securityResourceService);
    }

    private AccessDecisionVoter<?> roleHierarchyVoter() {
        RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());
        return roleHierarchyVoter;
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        return new RoleHierarchyImpl();
    }

    private List<AccessDecisionVoter<?>> getRoleVoter() {
        return List.of(new RoleVoter());
    }
}
