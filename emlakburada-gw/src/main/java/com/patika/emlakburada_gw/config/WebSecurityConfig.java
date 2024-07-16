package com.patika.emlakburada_gw.config;


import com.patika.emlakburada_gw.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@CrossOrigin(origins = "*")
public class WebSecurityConfig {

    @Autowired
    private JwtFilter filter;


    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        //// @formatter:off
        http
                .csrf().disable();
        return http.build();
        // @formatter:on

    }



    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()


                //login işlemi yapılmadan; kullanıcı oluşturulabilir, login işlemi yapılabilir. filtreden geçmez.

                .route("emlakburada-user",
                        r -> r.method(HttpMethod.POST)
                                .and()
                                .path(("/emlakburada/api/v1/auth/**"))
                                .uri("lb://EMLAKBURADA-USER"))
                .route("emlakburada-user",
                        r -> r.method(HttpMethod.GET)
                                .and()
                                .path(("/emlakburada/api/v1/user/**"))
                                .uri("lb://EMLAKBURADA-USER"))
                .route("emlakburada-advert-get",
                        r -> r.method(HttpMethod.GET)
                                .and()
                                .path("/emlakburada/api/v1/adverts/**")
                                .uri("lb://EMLAKBURADA-ADVERT"))
                .route("emlakburada-advert",
                        r -> r.method(HttpMethod.POST,HttpMethod.PUT,HttpMethod.DELETE)
                                .and()
                                .path("/emlakburada/api/v1/adverts/**")
                                .filters(f -> f.filter(filter)).uri("lb://EMLAKBURADA-ADVERT"))
                .route("emlakburada-order",
                        r -> r.method(HttpMethod.GET,HttpMethod.POST)
                                .and()
                                .path("/emlakburada/api/v1/orders/**")
                                .filters(f -> f.filter(filter)).uri("lb://EMLAKBURADA-ORDER"))
                .route("emlakburada-package",
                        r -> r.method(HttpMethod.GET,HttpMethod.POST)
                                .and()
                                .path("/emlakburada/api/v1/packages/**")
                                .filters(f -> f.filter(filter)).uri("lb://EMLAKBURADA-PACKAGE"))
                .build();

    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }

}