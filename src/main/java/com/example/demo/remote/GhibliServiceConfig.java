package com.example.demo.remote;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class GhibliServiceConfig {

    @Bean
    public GhibliApi ghibliService() {
    	System.out.println("Here");
    	
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ghibliapi.herokuapp.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit.create(GhibliApi.class);
    }

}
