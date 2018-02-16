package webservlets;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/demoRW")
public class DemoJSON extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {


        String action = req.getParameter("action");

        if (action != null && action.equals("read"))
            read(req, resp);
        else if (action != null && action.equals("write"))
            write(req, resp);
        else if (action != null && action.equals("search"))
            search(req, resp);


    }

    private void read(HttpServletRequest req, HttpServletResponse resp) {
        SingleListPersons listOfNames = SingleListPersons.getInstance();

        JSONObject json = new JSONObject();
        json.put("pers", listOfNames.getListOfNames());
        returnJsonResponse(resp, json.toString());
    }

    private void write(HttpServletRequest req, HttpServletResponse resp) {

        String name = req.getParameter("newName");

        SingleListPersons listOfNames = SingleListPersons.getInstance();
        listOfNames.addInTheListOfNames(name);

    }

    private void search(HttpServletRequest req, HttpServletResponse resp) {

        String name = req.getParameter("searchName");

        List<String> l = new ArrayList();
        if (name != null && !name.isEmpty()) {
            SingleListPersons listOfNames = SingleListPersons.getInstance();
            l = listOfNames.searchListOfNames(name);
        }
        JSONObject json = new JSONObject();
        json.put("searchPers", l);
        returnJsonResponse(resp, json.toString());
    }




    /**/
    private void returnJsonResponse(HttpServletResponse response, String jsonResponse) {
        response.setContentType("application/json");
        PrintWriter pr = null;
        try {
            pr = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert pr != null;
        pr.write(jsonResponse);
        pr.close();
    }
}
