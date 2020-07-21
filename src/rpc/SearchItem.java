package rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;
import external.ExternalAPI;
import external.ExternalAPIFactory;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBConnection conn = DBConnectionFactory.getDBConnection();
	private static final Logger LOGGER = Logger.getLogger(SearchItem.class.getName());


       
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		HttpSession session = request.getSession();
//		if (session.getAttribute("user") == null) {
//			response.setStatus(403);
//			return;
//		}

	//	String userId = session.getAttribute("user").toString();
		String userId = "1111";

		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));
		//Term can be empty or null
		String term = request.getParameter("term");
		
		LOGGER.log(Level.INFO, "lat:" + lat + ",lon:" + lon);
		
		List<Item> items = conn.searchItems(userId, lat, lon, term);
		List<JSONObject> list = new ArrayList<>();
		
		Set favorite = conn.getFavoriteItemIds(userId);
		try {
			for(Item item: items) {
				//Add a thin version of item object
				JSONObject obj = item.toJSONObject();
				
				// Check if this is a favorite one.
				// This field is required by frontend to correctly display favorite items.
				obj.put("favorite", favorite.contains(item.getItemId()));//还有这行
				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray array = new JSONArray(list);
		RpcHelper.writeJsonArray(response, array);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			response.setStatus(403);
			return;
		}

		String userId = session.getAttribute("user").toString();
		doGet(request, response);
	}

}
