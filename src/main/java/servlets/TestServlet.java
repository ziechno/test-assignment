package servlets;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;

@WebServlet(name = "TestServlet", value = "/TestServlet")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        URL test = new URL("https://query1.finance.yahoo.com/v11/finance/quoteSummary/AAPL?modules=assetProfile");
        URLConnection testConnection = test.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(testConnection.getInputStream()));
        String i;
        while ((i = br.readLine()) != null)
        {
            response.getWriter().print(i);
        }

      /*  Gson gson = new Gson();
        String jsonData;

        jsonData = gson.toJson(quizzes);
        response.getWriter().print(jsonData);
        System.out.println(jsonData);*/
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
