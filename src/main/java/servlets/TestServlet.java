package servlets;

import entities.Data;
import entities.Ticker;
import services.DataService;
import services.TickerService;
import utils.Utilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

@WebServlet(name = "TestServlet", value = "/TestServlet")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        String tickerSymbols = request.getParameter("symbols");
        String date = request.getParameter("date");

        TickerService tickerService = new TickerService();
        ArrayList<String> tickerSymbolList = Utilities.parseSymbols(tickerSymbols);
        ArrayList<Ticker> tickerList = tickerService.fetchTickers(tickerSymbolList);

        DataService dataService = new DataService();

        for(Ticker t : tickerList){
            dataService.fetchData(t, date);
        }



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
