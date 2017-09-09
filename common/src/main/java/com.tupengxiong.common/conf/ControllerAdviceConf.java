package com.tupengxiong.common.conf;

import com.tupengxiong.common.common.MsgEnum;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tpx on 2017/7/13.
 */
@ControllerAdvice()
public class ControllerAdviceConf {

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
    @ResponseBody()
    Map<String, Object> authenticationException(HttpServletRequest request, AuthenticationException ex) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", MsgEnum.NOT_LOGIN_ERROR.getCode());
        map.put("msg", MsgEnum.NOT_LOGIN_ERROR.getMsg());
        return map;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody()
    Map<String, Object> authenticationException(HttpServletRequest request, AccessDeniedException ex) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("msg", MsgEnum.NOT_ACCESS_ERROR.getMsg());
        map.put("code", MsgEnum.NOT_ACCESS_ERROR.getCode());
        return map;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody()
    Map<String, Object> usernameNotFoundException(HttpServletRequest request, UsernameNotFoundException ex) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("msg", MsgEnum.USERNAME_NOT_FOUND_ERROR.getMsg());
        map.put("code", MsgEnum.USERNAME_NOT_FOUND_ERROR.getCode());
        return map;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            //@see org.springframework.security.web.WebAttributes
            //request.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
