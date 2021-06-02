package hr.obai.commons.aop;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * Aspect for logging execution of service and repository Spring components.
 *
 * <p>By default, it only runs with the "dev" profile.
 */
@Slf4j
public abstract class BaseLogAspect {

  @Autowired protected Environment env;

  @Autowired protected HttpServletRequest request;

  /** Pointcut that matches all repositories, services and Web REST endpoints. */
  @Pointcut(
      "within(@org.springframework.stereotype.Repository *)"
          + " || within(@org.springframework.stereotype.Service *)"
          + " || within(@org.springframework.web.bind.annotation.RestController *)")
  protected void springBeanPointcut() {
    // Method is empty as this is just a Pointcut, the implementations are in the advices.
  }

  /** Pointcut that matches all Spring beans in the application's main packages. */
  protected abstract void applicationPackagePointcut();

  /**
   * Advice that logs methods throwing exceptions.
   *
   * @param joinPoint join point for advice
   * @param e exception
   */
  @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    if (env.acceptsProfiles("dev")) {
      val msgError = "Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'";
      log.error(
          msgError,
          joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName(),
          e.getCause() != null ? e.getCause() : "NULL",
          e.getMessage(),
          e);
    } else {
      val msgError = "Exception in {}.{}() with cause = {}";
      log.error(
          msgError,
          joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName(),
          e.getCause() != null ? e.getCause() : "NULL");
    }
  }

  /**
   * Advice that logs when a method is entered and exited.
   *
   * @param joinPoint join point for advice
   * @return result
   * @throws Throwable throws IllegalArgumentException
   */
  @Around("applicationPackagePointcut() && springBeanPointcut()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    Object result = null;

    if (!joinPoint.getSignature().getName().equals("authorize")) {
      val msgEnter = "Enter: {}.{}() with argument[s] = {}";
      log.info(
          msgEnter,
          joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName(),
          Arrays.toString(joinPoint.getArgs()));

      try {

        result = joinPoint.proceed();
        val msgExit = "Exit: {}.{}() with result = {}";
        log.info(
            msgExit,
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            result);

      } catch (IllegalArgumentException e) {
        val msgError = "Illegal argument: {} in {}.{}()";
        log.error(
            msgError,
            Arrays.toString(joinPoint.getArgs()),
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName());

        throw e;
      }
    } else {
      result = joinPoint.proceed();
    }
    return result;
  }
}
