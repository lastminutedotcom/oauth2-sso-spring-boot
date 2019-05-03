package com.lastminute.federatedsso.webbootiful15xapp

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@SpringBootApplication
class WebBootiful15xAppApplication

fun main(args: Array<String>) {
    SpringApplication.run(WebBootiful15xAppApplication::class.java, *args)
}

@Configuration
@EnableOAuth2Sso
class OAuth2SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().anyRequest().authenticated()
    }
}

@Controller
class IndexController {

    @GetMapping("/index")
    fun index(model: Model): String {
        val authentication = SecurityContextHolder.getContext().authentication
        model.addAttribute("user", authentication)
        return "index"
    }
}
