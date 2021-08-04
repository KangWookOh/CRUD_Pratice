package com.example.webstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.swing.text.html.HTMLDocument;

@EnableJpaAuditing
@SpringBootApplication
public class WebStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebStudyApplication.class, args);
    }


}
