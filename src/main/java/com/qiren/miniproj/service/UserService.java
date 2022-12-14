package com.qiren.miniproj.service;

import java.util.List;

import com.qiren.miniproj.bean.UserBean;
import com.qiren.miniproj.bean.UserRegistrationBean;
import com.qiren.miniproj.dao.UserDAO;
import com.qiren.miniproj.manager.SessionManager;
import com.qiren.miniproj.tools.Constants;
import com.qiren.miniproj.tools.RegistrationValidater;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserService {

    private UserDAO userdao = new UserDAO();

    private UserService() {

    }

    public boolean updateUserInfo(HttpServletRequest request,
            HttpServletResponse response) {
        UserBean userBean = getUserInfoByID(request.getParameter("userid"));
        if (null == userBean) {
            request.setAttribute(Constants.PARAM_ERROR, "Update User Failed, Cannot Find User");
            return false;
        }
        UserRegistrationBean registrationInfo = new UserRegistrationBean();
        registrationInfo.setFname(request.getParameter("fname"));
        registrationInfo.setLname(request.getParameter("lname"));
        registrationInfo.setAddress1(request.getParameter("address1"));
        registrationInfo.setCity(request.getParameter("city"));
        registrationInfo.setState(request.getParameter("state"));
        registrationInfo.setPostalCode(request.getParameter("postcode"));
        registrationInfo.setMobileNumber(request.getParameter("mobile"));
        registrationInfo.setEmail(request.getParameter("email"));
        registrationInfo.setGender(request.getParameter("gender"));
        registrationInfo.setBirthday(request.getParameter("birthday"));
        registrationInfo.setUsername(request.getParameter("username"));
        registrationInfo.setRole(userBean.getUserBean().getRole());
        String password = request.getParameter("password");
        boolean checkPassword = true;
        if (null == password || password.isBlank()) {
            // if no password change, do like this:
            checkPassword = false;
        }  else {
            registrationInfo.setPassword(request.getParameter("password"));
            registrationInfo.setConfirmPassword(request.getParameter("confirmpwd"));
        }
        RegistrationValidater validator = new RegistrationValidater(registrationInfo);
        if (!validator.isValid(checkPassword)) {
            request.setAttribute(Constants.PARAM_ERROR, validator.getErrorInfo());
            return false;
        }
        userBean.setUserBean(registrationInfo);
        boolean res = userdao.updateUserData(userBean);
        if (!res) {
            request.setAttribute(Constants.PARAM_ERROR, "Update User Failed");
        }
        return res;
    }
    
    public boolean submitUserInfo(HttpServletRequest request,
            HttpServletResponse response) {
        // UserBean userBean = new UserBean();
        UserRegistrationBean registrationInfo = new UserRegistrationBean();
        registrationInfo.setFname(request.getParameter("fname"));
        registrationInfo.setLname(request.getParameter("lname"));
        registrationInfo.setAddress1(request.getParameter("address1"));
        registrationInfo.setCity(request.getParameter("city"));
        registrationInfo.setState(request.getParameter("state"));
        registrationInfo.setPostalCode(request.getParameter("postcode"));
        registrationInfo.setMobileNumber(request.getParameter("mobile"));
        registrationInfo.setEmail(request.getParameter("email"));
        registrationInfo.setGender(request.getParameter("gender"));
        registrationInfo.setBirthday(request.getParameter("birthday"));
        registrationInfo.setUsername(request.getParameter("username"));
        registrationInfo.setPassword(request.getParameter("password"));
        registrationInfo.setConfirmPassword(request.getParameter("confirmpwd"));
        RegistrationValidater validator = new RegistrationValidater(registrationInfo);
        if (!validator.isValid()) {
            request.setAttribute(Constants.PARAM_ERROR, validator.getErrorInfo());
            return false;
        }
        
        // check exist
        if (null != getUserInfo(registrationInfo.getUsername())) {
            request.setAttribute(Constants.PARAM_ERROR, "Username already exists");
            return false;
        }
        
        boolean res = createUserInfo(registrationInfo);
        if (!res) {
            request.setAttribute(Constants.PARAM_ERROR, "Create User Failed");
        }
        return res;
    }

    public boolean createUserInfo(UserRegistrationBean bean) {
        return userdao.createUserInfo(bean);
    }

    public UserBean getUserInfo(String username) {
        return userdao.getUserInfo(username);
    }

    public UserBean getUserInfoByID(String userId) {
        return userdao.getUserInfoByID(userId);
    }

    public UserBean getUserInfo(String username, String password) {
        return userdao.getUserInfo(username, password);
    }

    public List<UserBean> getAllUser() {
        return userdao.getAllUserInfo();
    }

    public boolean checkLoginInfo(String username, String password) {
        return null != userdao.getUserInfo(username, password);
    }
    
    public void deleteUser(String userId) {
        userdao.deleteUser(userId);
    }

    public static UserService getInstance() {
        return Inner.instance;
    }

    private static class Inner {
        private static UserService instance = new UserService();
    }
}
