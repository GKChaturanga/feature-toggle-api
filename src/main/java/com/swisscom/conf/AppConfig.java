package com.swisscom.conf;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration

public class AppConfig {

	 
    /**MessageSource , used to get error and validation messages  
     * 
     * @return
     */
	@Bean
	public MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource
	      = new ReloadableResourceBundleMessageSource();
	    
	    messageSource.setBasename("classpath:messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}
	
	/**Binds the messageSource to ValidatorFactoryBean
	 * 
	 * @param messageSource
	 * @return
	 */
	@Bean
	public LocalValidatorFactoryBean getValidator(MessageSource messageSource) {
	    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    bean.setValidationMessageSource(messageSource);
	    return bean;
	}
	
	/**objectMapper for request and response conversion 
	 * 
	 * @param builder
	 * @return
	 */
	@Bean(name = {"objectMapper"})
	@Primary
	ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		 return builder
		            .serializationInclusion(Include.NON_NULL)
		            .failOnEmptyBeans(false)
		            .failOnUnknownProperties(false)
		           
		.build();
	}
}
