package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.TickerTransferObject;
import entities.Ticker;
import services.DataEntryService;
import services.TickerService;
import utils.JSONObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "TestServlet", value = "/TestServlet")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        String tickerSymbols = request.getParameter("symbols");
        String date = request.getParameter("date");

        //Get tickers to be presented
        TickerService tickerService = new TickerService();
        ArrayList<Ticker> tickerList = tickerService.fetchTickers(tickerSymbols);

        //Get data about tickers
        DataEntryService dataEntryService = new DataEntryService();
        ArrayList<TickerTransferObject> tickerTransferObjects = dataEntryService.fetchTickerData(tickerList, date);

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        //String jsonString = JSONObjectMapper.objectMapper.writeValueAsString(tickerTransferObjects);
        String jsonString = gson.toJson(tickerTransferObjects);
        System.out.println(jsonString);

        response.getWriter().print(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
