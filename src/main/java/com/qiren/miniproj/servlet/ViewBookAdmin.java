package com.qiren.miniproj.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.qiren.miniproj.bean.BookBean;
import com.qiren.miniproj.manager.ServletManager;
import com.qiren.miniproj.service.BookService;
import com.qiren.miniproj.tools.Constants;

/**
 * Servlet implementation class ViewBookAdmin
 */
public class ViewBookAdmin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewBookAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        if (!ServletManager.getInstance().refererCheck(request, response)) {
            return;
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String bookId = request.getParameter(Constants.PARAM_BOOK_ID);
        if (null == bookId || bookId.isBlank()) {
            request.setAttribute(Constants.PARAM_ERROR, "No book selected");
            request.getRequestDispatcher(Constants.PAGE_FAILED).forward(request, response);
            return;
        }

        BookBean bb = BookService.getInstance().getBook(bookId);

        request.setAttribute(Constants.PARAM_BOOK_BEAN, bb);
        request.getRequestDispatcher(Constants.PAGE_VIEW_BOOK_ADMIN).forward(request, response);
    }

}
