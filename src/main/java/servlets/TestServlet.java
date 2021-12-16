package servlets;


import entities.DataEntry;
import entities.Ticker;
import services.DataEntryService;
import services.TickerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
        ArrayList<Ticker> tickerList = tickerService.fetchTickers(tickerSymbols);

        DataEntryService dataEntryService = new DataEntryService();

        Map<Ticker, ArrayList<DataEntry>> tickerDataMap = dataEntryService.fetchTickerData(tickerList, date);
        System.out.println("finished");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
