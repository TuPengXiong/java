package com.tupengxiong.common.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.tupengxiong.common.beans.User;
import com.tupengxiong.common.common.MsgEnum;
import com.tupengxiong.common.dao.UserDao;
import com.tupengxiong.common.service.CommonService;
import com.tupengxiong.common.service.impl.DtSpringSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by tpx on 2017/7/10.
 */
@Controller("userController")
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private DtSpringSecurityService dtSpringSecurityService;
    @Resource
    private CommonService commonService;
    @Resource
    private UserDao userDao;

    @RequestMapping(value = "/info", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("isAuthenticated()")
    public String info(HttpSession session) {
        User user = dtSpringSecurityService.getUser();
        if (null != user) {
            session.setAttribute("user", user);
        }
        return "index";
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @PreAuthorize("isAuthenticated()")
    ModelAndView list(HttpServletRequest httpServletRequest, @RequestParam(defaultValue = "1") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, sort);
        Page<User> pageInfo = this.userDao.findAll(request);
        ModelAndView model = new ModelAndView("member-list");
        model.addObject("code", MsgEnum.SUCCESS.getCode());
        model.addObject("msg", MsgEnum.SUCCESS.getMsg());
        String path = commonService.getPath(httpServletRequest);
        model.addObject("path", path);
        //获得当前页
        model.addObject("pageNumber", pageNumber);
        //获得一页显示的条数
        model.addObject("pageSize", pageSize);
        //是否是第一页
        model.addObject("isFirstPage", pageInfo.isFirst());
        //获得总页数
        model.addObject("totalPages", pageInfo.getTotalPages());
        //是否是最后一页
        model.addObject("isLastPage", pageInfo.isLast());
        //是否是最后一页
        model.addObject("total", pageInfo.getTotalElements());
        //数据
        model.addObject("lists", pageInfo.getContent());
        logger.error(pageInfo.getContent().toString());
        return model;
    }

    @RequestMapping(value = "/show", method = {RequestMethod.GET})
    @PreAuthorize("isAuthenticated()")
    public ModelAndView info(Integer id) {
        ModelAndView model = new ModelAndView("member-show");
        User user = userDao.findOne(id);
        model.addObject("user", user);
        if (user != null) {
            model.addObject("code", MsgEnum.SUCCESS.getCode());
            model.addObject("msg", MsgEnum.SUCCESS.getMsg());
        } else {
            model.addObject("code", MsgEnum.Fail.getCode());
            model.addObject("msg", MsgEnum.Fail.getMsg());
        }
        return model;
    }

}
