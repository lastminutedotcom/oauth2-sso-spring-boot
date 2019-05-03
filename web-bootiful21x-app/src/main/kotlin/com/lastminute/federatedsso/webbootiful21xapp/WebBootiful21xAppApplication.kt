package com.lastminute.federatedsso.webbootiful21xapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@SpringBootApplication
class WebBootiful21xAppApplication

fun main(args: Array<String>) {
    runApplication<WebBootiful21xAppApplication>(*args)
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

@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
    }
}


