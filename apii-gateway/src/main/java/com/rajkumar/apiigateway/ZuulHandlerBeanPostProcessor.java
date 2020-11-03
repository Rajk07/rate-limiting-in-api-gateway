package com.rajkumar.apiigateway;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.rajkumar.apiigateway.ratelimit.interceptor.RateLimitInterceptor;

@Configuration
public class ZuulHandlerBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

	@Autowired
	@Lazy
	private RateLimitInterceptor rateLimitInterceptor;
	
	@Override
	public boolean postProcessAfterInstantiation(final Object bean, String beanName) throws BeansException {
		if(bean instanceof ZuulHandlerMapping) {
			ZuulHandlerMapping zuulHandlerMapping = (ZuulHandlerMapping)bean;
			zuulHandlerMapping.setInterceptors(rateLimitInterceptor);
		}
		return super.postProcessAfterInstantiation(bean, beanName);
	}
	
}
