package rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Item;
import external.ExternalAPI;
import external.ExternalAPIFactory;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/**
		 * we first try to return the username;
		 * response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		String username = "";
		PrintWriter out = response.getWriter();
		if (request.getParameter("username") != null) {
			username = request.getParameter("username");
			out.println("Hello " + username);
			out.print("goodbye " + username);
		}
		out.flush();
		out.close();
		*/

		/**
		 * we try to return a HTML page
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("<h1>This is a HTML page</h1>");
		out.println("</body></html>");
		out.flush();
		out.close();
		*/
		
		/**
		 * 
		 * response.setContentType("application/json");
		 
		response.addHeader("Access-Control-Allow-Origin", "*");

		String username = "";
		if (request.getParameter("username") != null) {
			username = request.getParameter("username");
		}
		
		JSONObject obj = new JSONObject();
		try {
			obj.put("username", username);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();
		out.print(obj);
		out.flush();
		out.close(); 
		*/
		
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));
		// Term can be empty or null.
		String term = request.getParameter("term");
		ExternalAPI externalAPI = ExternalAPIFactory.getExternalAPI();
		List<Item> items = externalAPI.search(lat, lon, term);
		List<JSONObject> list = new ArrayList<>();
		
		try {
			for (Item item : items) {
				// Add a thin version of item object
				JSONObject obj = item.toJSONObject();
				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray array = new JSONArray(list);
		RpcHelper.writeJsonArray(response, array);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
