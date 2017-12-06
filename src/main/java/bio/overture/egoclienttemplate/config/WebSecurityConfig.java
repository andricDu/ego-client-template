package bio.overture.egoclienttemplate.config;

import lombok.SneakyThrows;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@EnableWebSecurity
@EnableResourceServer
public class WebSecurityConfig extends ResourceServerConfigurerAdapter {

  @Override
  @SneakyThrows
  public void configure(HttpSecurity http) {
    http
        .authorizeRequests()
        .antMatchers("/health").permitAll()
        .antMatchers("/isAlive").permitAll()
        .antMatchers("/upload/**").permitAll()
        .antMatchers("/download/**").permitAll()
        .antMatchers("/entities/**").permitAll()
        .antMatchers("/swagger**", "/swagger-resources/**", "/v2/api**", "/webjars/**").permitAll()
        .antMatchers("/test").permitAll()
        .and()
        .authorizeRequests()
        .anyRequest().authenticated();
  }

}
