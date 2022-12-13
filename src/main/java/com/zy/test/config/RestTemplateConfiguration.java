package com.zy.test.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * @author {庄勇}
 * @since 2022/12/13 10:52
 */
@Configuration
public class RestTemplateConfiguration {
  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    MappingJackson2HttpMessageConverter
        converter = new MappingJackson2HttpMessageConverter();
    converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
    restTemplate.getMessageConverters().add(converter);
    return restTemplate;
  }
}
