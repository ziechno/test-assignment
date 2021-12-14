package servlets;

import entities.Ticker;
import services.TickerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@WebServlet(name = "TestServlet", value = "/TestServlet")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        String tickerSymbols = request.getParameter("symbols");

        TickerService tickerService = new TickerService();
        ArrayList<Ticker> tickerList = tickerService.fetchTickers(tickerSymbols);

        DataService dataService = new DataService();

        for(Ticker t : tickerList){
            dataService.fetchData(t, )
        }



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
