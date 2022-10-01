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
	@Before("execution(*  com.swisscom.service.FeatureToggleService.*(..)) && args(request,..)")
	public void activityAvaialabilityStart(JoinPoint joinPoint , Object request) throws Exception {
		if("getFeatureToggles".equalsIgnoreCase(joinPoint.getSignature().getName())) {
			logger.debug("START getFeatureToggles with page ->  {}" , ((PaginatedRequest)request).getCurrentPage() );
		} 
		if("createFeatureToggles".equalsIgnoreCase(joinPoint.getSignature().getName())) { 
			logger.debug("START createFeatureToggles with FeatureToggles ->  {}" ,request   );
		} 
		if("getFeatureToggle".equalsIgnoreCase(joinPoint.getSignature().getName())) {
			logger.debug("START getFeatureToggle with id ->  {}" , (long)request );
		} 
		if("deleteFeatureToggles".equalsIgnoreCase(joinPoint.getSignature().getName())) {
			logger.debug("START deleteFeatureToggles with id ->  {}" , (long)request );
		} 
		
	}  
	//Logs all method returns  of FeatureToggleService
	@AfterReturning (pointcut = "execution(* com.swisscom.service.FeatureToggleService.*(..)) && args(request,..) " ,  returning = "response")
	public void activityAvaialabilityEnd(JoinPoint joinPoint , Object request , Object response) throws Exception {
		if("getFeatureToggles".equalsIgnoreCase(joinPoint.getSignature().getName())) {
			logger.debug("End getFeatureToggles with page ->  {} found {} results from total of {}",  ((PaginatedRequest)request).getCurrentPage() , ((PaginatedFeatureToggleResponse )response).getPayload().size() , ((PaginatedFeatureToggleResponse) response).getTotalCount() );
		}  
		if("createFeatureToggles".equalsIgnoreCase(joinPoint.getSignature().getName())) {
			logger.debug("End  createFeatureToggles with FeatureToggles  ->  {} with {} " ,  request  , response   );
		} 
		if("getFeatureToggle".equalsIgnoreCase(joinPoint.getSignature().getName())) {
			logger.debug("End getFeatureToggle with id ->  {} and found {}" , (long)request  , response  );
		} 
		if("deleteFeatureToggles".equalsIgnoreCase(joinPoint.getSignature().getName())) {
			logger.debug("End deleteFeatureToggles with id ->  {}" , (long)request );
		} 
		
	}
	
	//Logs all exceptions and convert non handled exceptions to FeatureToggleManagerException with 500 status 
	@AfterThrowing (pointcut = "execution(*  com.swisscom.service.FeatureToggleService.*(..))" ,  throwing = "exception")
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