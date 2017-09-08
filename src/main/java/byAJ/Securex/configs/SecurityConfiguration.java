package byAJ.Securex.configs;

import byAJ.Securex.repositories.UserzRepo;
import byAJ.Securex.services.SSUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private SSUserDetailsService userDetailsService;

    @Autowired
    private UserzRepo uRepo;


    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new SSUserDetailsService(uRepo);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {




        http
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/js/**", "/img/**").permitAll()

//                .antMatchers("/books/list").access("hasRole('ROLE_USER')")
//                .antMatchers("/books/list").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
//                .antMatchers("/books/list").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
//                .antMatchers( "/books/add", "/books/edit/{id}", "/books/delete/{id}").access("hasAnyRole('ROLE_ADMIN')")
                //                .antMatchers( "/books/add", "/books/edit/{id}", "/books/delete/{id}").access("hasRole('ROLE_ADMIN')")
//                .antMatchers( "/books/add", "/books/edit/{id}", "/books/delete/{id}").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/books/list").access("hasAuthority('USER') or hasAuthority('ADMIN')")

                .antMatchers( "/books/add", "/books/edit/**", "/books/delete/**").access("hasAuthority('ADMIN')")

                .anyRequest().authenticated();
        http
                .formLogin().failureUrl("/login?error")
                .defaultSuccessUrl("/books/list")
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
                .permitAll()
        .and()
        .httpBasic();



        http
                .csrf().disable();

        http
                .headers().frameOptions().disable();



    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER")
//             .and()
//                .withUser("admin").password("password").roles("ADMIN");



        auth
                .userDetailsService(userDetailsServiceBean());
    }
}
