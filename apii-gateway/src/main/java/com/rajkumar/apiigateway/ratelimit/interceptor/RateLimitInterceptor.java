package com.rajkumar.apiigateway.ratelimit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.rajkumar.apiigateway.ratelimit.service.RateLimitService;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {
	private static final String HEADER_API_KEY = "X-API-KEY";
	private static final String HEADER_LIMIT_REMAINING = "X-RATE-LIMIT-REMAINING";
	private static final String HEADER_RETRY_AFTER = "X-RATE-LIMIT-RETRY-AFTER-SECONDS";

	
	@Autowired
	RateLimitService rateLimitService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String apiKey = request.getHeader(HEADER_API_KEY);
		if(apiKey == null || apiKey.isEmpty()) {
			response.sendError(HttpStatus.OK.value(),  HEADER_API_KEY+" request header is mandatory");
			return false;
		}
		
		Bucket tokenBucket = rateLimitService.resolveBucket(request.getHeader(HEADER_API_KEY));
		ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);
		
		if(probe.isConsumed()) {
			response.addHeader(HEADER_LIMIT_REMAINING, Long.toString(probe.getRemainingTokens()));
			return true;
		}		
		response.addHeader(HEADER_RETRY_AFTER, Long.toString(probe.getNanosToWaitForRefill()/1000000000));
		response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(),"You have exceeded your request limit");
		return false;
	}

}
