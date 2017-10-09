package com.lovesher.tapechat.conf;

import com.lovesher.tapechat.common.MsgEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tpx on 2017/7/13.
 */
@ControllerAdvice()
public class ControllerAdviceConf {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    @ResponseBody()
    Map<String, Object> exception(HttpServletRequest request, Exception ex) {
        HttpStatus status = getStatus(request);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("msg", ex.getMessage());
        map.put("code", MsgEnum.Fail.getCode());
        return map;
    }

    @ExceptionHandler(AuthenticationException.class)
    void authenticationException(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) {
        try {
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    @ExceptionHandler(AccessDeniedException.class)
    void authenticationException(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) {
        try {
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    void usernameNotFoundException(HttpServletRequest request, HttpServletResponse response, UsernameNotFoundException ex) {
        try {
            response.sendRedirect(request.getContextPath() + "/login?error=true");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            //@see org.springframework.security.web.WebAttributes
            //response.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
