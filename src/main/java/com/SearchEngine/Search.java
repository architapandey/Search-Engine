package com.SearchEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Search")
public class Search extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");

        Connection connection = DatabaseConnection.getConnection();

        try {
           PreparedStatement preparedStatement= connection.prepareStatement("Insert into history values (?,?);");
           preparedStatement.setString(1,keyword);
            preparedStatement.setString(2,"http://localhost:8080/Search-Engine/Search?keyword="+keyword);
            preparedStatement.executeUpdate();


            ResultSet resultSet = connection.createStatement().executeQuery("select pagetitle, pagelink, (length(lower(pageText))-length(replace(lower(pageText),'" + keyword.toLowerCase() + "','')))/length('" + keyword.toLowerCase() + "') as countoccurence from pages order by countoccurence desc limit 30;");
            ArrayList<SearchResult> results = new ArrayList<SearchResult>();

            while (resultSet.next()) {
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(resultSet.getString("pageTitle"));
                searchResult.setLink(resultSet.getString("pageLink"));
                results.add(searchResult);
            }

            for (SearchResult result : results) {
                System.out.println(result.getTitle() + "\n" + result.getLink() + "\n");
            }
            req.setAttribute("results", results);
            req.getRequestDispatcher("search.jsp").forward(req, resp);
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException servletException) {
            servletException.printStackTrace();

        }
    }
}
