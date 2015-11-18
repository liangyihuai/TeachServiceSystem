package com.huai.controllers;

import com.huai.service.ScoreService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liangyihuai on 15-11-18.
 */
@WebServlet(urlPatterns = "/score")
public class ScoreServlet extends HttpServlet{

    private ScoreService scoreService = null;

    public void init() throws ServletException {
        ServletContext servletContext = this.getServletContext();
        WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        scoreService = webAppContext.getBean(ScoreService.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operate = request.getParameter("operate");

        System.out.println("operate = "+operate);

        if("".equals(operate)){


        }else if("".equals(operate)){

        }
    }




    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
