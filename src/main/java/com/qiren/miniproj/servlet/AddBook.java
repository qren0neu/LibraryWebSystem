package com.qiren.miniproj.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.qiren.miniproj.bean.BookBean;
import com.qiren.miniproj.manager.ServletManager;
import com.qiren.miniproj.manager.SessionManager;
import com.qiren.miniproj.service.BookService;
import com.qiren.miniproj.service.UserService;
import com.qiren.miniproj.tools.Constants;

/**
 * Servlet implementation class AddBook
 */
public class AddBook extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBook() {
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
        // response.getWriter().append("Served at: ").append(request.getContextPath());
        if (!ServletManager.getInstance().refererCheck(request, response)) {
            return;
        }
        request.getRequestDispatcher(Constants.PAGE_ADD_BOOK).forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        // doGet(request, response);
        BookBean bookBean = new BookBean();
        bookBean.setAuthor(request.getParameter("author"));
        bookBean.setDescription(request.getParameter("description"));
        bookBean.setInstock(request.getParameter("inStock"));
        bookBean.setISBN(request.getParameter("ISBN"));
        bookBean.setCoverimg("images/book.png");
        bookBean.setName(request.getParameter("name"));
        bookBean.setPublisher(request.getParameter("publisher"));
        if (BookService.getInstance().addNewBook(bookBean)) {
            String name = UserService.getInstance()
                    .getUserInfo(SessionManager.getInstance().getUserName(request))
                    .getUserBean().getFname();
            request.setAttribute("fname", name);
            request.getRequestDispatcher(Constants.PAGE_SUCCESS).forward(request, response);

        } else {
            request.setAttribute(Constants.PARAM_ERROR, "Add book failed");
            request.getRequestDispatcher(Constants.PAGE_FAILED).forward(request, response);
        }
    }

}
