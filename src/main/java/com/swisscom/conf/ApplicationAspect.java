package com.swisscom.conf;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.swisscom.model.payload.PaginatedFeatureToggleResponse;
import com.swisscom.model.payload.PaginatedRequest;
 


/**ApplicationAspect is responsible for log method calls and handle unhandled exceptions 
 * 
 * @author kasunc
 *
 */
@Service 
@Aspect
public class ApplicationAspect {
	
	Logger logger = LoggerFactory.getLogger(ApplicationAspect.class);
	//Logs all method attributes / Request body of FeatureToggleService 
	@Before("execution(*  com.swisscom.service.*.*(..)) && args(request,..)")
	public void activityAvaialabilityStart(JoinPoint joinPoint , Object request) throws Exception {
		logger.debug("START {} with page ->  {}" , joinPoint.getSignature().getName() ,   request);
		
	 
		
	}  
	//Logs all method returns  of FeatureToggleService
	@AfterReturning (pointcut = "execution(* com.swisscom.service.*.*(..)) && args(request,..) " ,  returning = "response")
	public void activityAvaialabilityEnd(JoinPoint joinPoint , Object request , Object response) throws Exception {
		logger.debug("END {} with page ->  {} found {}" , joinPoint.getSignature().getName() ,   request , response);
		 
		
	}
	
	//Logs all exceptions and convert non handled exceptions to FeatureToggleManagerException with 500 status 
	@AfterThrowing (pointcut = "execution(*  com.swisscom.service.*.*(..))" ,  throwing = "exception")
	public void activityAvaialabilityError(JoinPoint joinPoint ,  Exception exception) throws FeatureToggleException {
		logger.error( "ERROR  in -> {} -> {}" ,joinPoint.getSignature().getName()  ,  joinPoint.getTarget().getClass().getSimpleName(),exception  );  
		if(! (exception instanceof FeatureToggleException)) {
			throw new FeatureToggleException.Builder()
					.original(exception)
					.key(FeatureToggleException.MESSAGE_SYSTEM_ERROR)
					.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} 
		
	} 
	 
 
	

}