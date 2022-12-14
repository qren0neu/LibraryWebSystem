package com.qiren.miniproj.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.qiren.miniproj.bean.UserBean;
import com.qiren.miniproj.manager.ServletManager;
import com.qiren.miniproj.manager.SessionManager;
import com.qiren.miniproj.service.UserService;
import com.qiren.miniproj.tools.Constants;
import com.qiren.miniproj.tools.Utils;

/**
 * Servlet implementation class ViewSelfInfoAdmin
 */
public class ViewSelfInfoAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewSelfInfoAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    // if we have username sent by request, we use that instead of our own.
	    // ONLY ADMIN CAN DO LIKE THIS!
        if (!ServletManager.getInstance().refererCheck(request, response)) {
            return;
        }
        String userName = request.getParameter(Constants.PARAM_USER_NAME);
        Utils.log("View Username: " + userName);
        if (null == userName || userName.isBlank()) {
            userName = SessionManager.getInstance().getUserName(request);
        }
        if (null != userName) {
            UserBean userBean = UserService.getInstance().getUserInfo(userName);
            request.setAttribute(Constants.PARAM_USER_BEAN, userBean);
            request.getRequestDispatcher(Constants.PAGE_VIEW_USER).forward(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
