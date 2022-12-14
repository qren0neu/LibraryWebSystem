package com.qiren.miniproj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qiren.miniproj.bean.UserBean;
import com.qiren.miniproj.bean.UserRegistrationBean;
import com.qiren.miniproj.manager.ConnectionManager;
import com.qiren.miniproj.tools.Constants;

public class UserDAO {

    /**
     * Create a new registered user
     */
    public boolean createUserInfo(UserRegistrationBean bean) {
        Connection connection = ConnectionManager.getConnection();

        String sql = "INSERT INTO `mini_proj_web`.`user` (`pkUser`, `fname`, `lname`, `addr`, `city`, `state`, `postalcode`, `mobile`, `email`, `gender`, `birthday`, `role`, `username`, `password`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, MD5(?));";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, null);
            ps.setString(2, bean.getFname());
            ps.setString(3, bean.getLname());
            ps.setString(4, bean.getAddress1());
            ps.setString(5, bean.getCity());
            ps.setString(6, bean.getState());
            ps.setString(7, bean.getPostalCode());
            ps.setString(8, bean.getMobileNumber());
            ps.setString(9, bean.getEmail());
            ps.setString(10, bean.getGender());
            ps.setString(11, bean.getBirthday());
            ps.setString(12, Constants.ROLE_USER); // user is mean to be role '1'
            ps.setString(13, bean.getUsername());
            ps.setString(14, bean.getPassword());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return true;
    }

    /**
     * Get user info with user name, This is for search.
     */
    public UserBean getUserInfo(String username) {
        Connection connection = ConnectionManager.getConnection();

        String sql = "SELECT * FROM mini_proj_web.user where username=?;";
        UserBean bean = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            bean = parseData(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return bean;
    }
    
    public UserBean getUserInfoByID(String userId) {
        Connection connection = ConnectionManager.getConnection();

        String sql = "SELECT * FROM mini_proj_web.user where pkUser=?;";
        UserBean bean = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            bean = parseData(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return bean;
    }

    /**
     * Get user info with username and password, This is for login.
     */
    public UserBean getUserInfo(String username, String password) {
        Connection connection = ConnectionManager.getConnection();

        String sql = "SELECT * FROM mini_proj_web.user where username=? and password=MD5(?);";

        UserBean bean = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            bean = parseData(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return bean;
    }

    /**
     * Get all registered users
     */
    public List<UserBean> getAllUserInfo() {
        Connection connection = ConnectionManager.getConnection();

        String sql = "SELECT * FROM mini_proj_web.user where role != 0;";

        List<UserBean> bean = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            bean = parseAllData(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return bean;
    }

    private static UserBean parseData(ResultSet rs) {
        UserBean bean = null;

        try {
            if (rs.next()) {
                bean = new UserBean();
                UserRegistrationBean regisBean = new UserRegistrationBean();

                regisBean.setFname(rs.getString("fname"));
                regisBean.setLname(rs.getString("lname"));
                regisBean.setAddress1(rs.getString("addr"));
                regisBean.setCity(rs.getString("city"));
                regisBean.setState(rs.getString("state"));
                regisBean.setPostalCode(rs.getString("postalcode"));
                regisBean.setMobileNumber(rs.getString("mobile"));
                regisBean.setEmail(rs.getString("email"));
                regisBean.setGender(rs.getString("gender"));
                regisBean.setBirthday(rs.getString("birthday"));
                regisBean.setRole(rs.getString("role"));
                regisBean.setUsername(rs.getString("username"));
                regisBean.setPassword(rs.getString("password"));

                bean.setUserId(rs.getString("pkUser"));
                bean.setUserBean(regisBean);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bean;
    }

    private static List<UserBean> parseAllData(ResultSet rs) {
        List<UserBean> allBeans = new ArrayList<>();

        try {
            while (rs.next()) {
                UserBean bean = null;
                bean = new UserBean();
                UserRegistrationBean regisBean = new UserRegistrationBean();

                regisBean.setFname(rs.getString("fname"));
                regisBean.setLname(rs.getString("lname"));
                regisBean.setAddress1(rs.getString("addr"));
                regisBean.setCity(rs.getString("city"));
                regisBean.setState(rs.getString("state"));
                regisBean.setPostalCode(rs.getString("postalcode"));
                regisBean.setMobileNumber(rs.getString("mobile"));
                regisBean.setEmail(rs.getString("email"));
                regisBean.setGender(rs.getString("gender"));
                regisBean.setBirthday(rs.getString("birthday"));
                regisBean.setRole(rs.getString("role"));
                regisBean.setUsername(rs.getString("username"));
                regisBean.setPassword(rs.getString("password"));

                bean.setUserId(rs.getString("pkUser"));
                bean.setUserBean(regisBean);
                allBeans.add(bean);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return allBeans;
    }

    public boolean updateUserData(UserBean userBean) {
        Connection connection = ConnectionManager.getConnection();
        UserRegistrationBean bean = userBean.getUserBean();
        boolean hasPass = bean.getPassword() != null && !bean.getPassword().isBlank();
        String sql = "UPDATE `mini_proj_web`.`user`"
                + "SET"
                + "`fname` = ?,"
                + "`lname` = ?,"
                + "`addr` = ?,"
                + "`city` = ?,"
                + "`state` = ?,"
                + "`postalcode` = ?,"
                + "`mobile` = ?,"
                + "`email` = ?,"
                + "`gender` = ?,"
                + "`birthday` = ?,"
                + "`role` = ?,"
                + "`username` = ?"
                + (!hasPass ? "" : ",`password` = MD5(?)")
                + " WHERE `pkUser` = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, bean.getFname());
            ps.setString(2, bean.getLname());
            ps.setString(3, bean.getAddress1());
            ps.setString(4, bean.getCity());
            ps.setString(5, bean.getState());
            ps.setString(6, bean.getPostalCode());
            ps.setString(7, bean.getMobileNumber());
            ps.setString(8, bean.getEmail());
            ps.setString(9, bean.getGender());
            ps.setString(10, bean.getBirthday());
            ps.setString(11, bean.getRole());
            ps.setString(12, bean.getUsername());
            if (hasPass) {
                ps.setString(13, bean.getPassword());
                ps.setString(14, userBean.getUserId());
            } else {
                ps.setString(13, userBean.getUserId());
            }
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionManager.closeConnection(connection);
        }

        return true;
    }
    
    public void deleteUser(String pk) {
        Connection connection = ConnectionManager.getConnection();

        String sql = "delete from user where pkUser = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            // ps.setString(1, null);
            ps.setString(1, pk);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.closeConnection(connection);
        }
    }
}
