package rpc;

import java.io.IOException;
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

import rpc.Restaurant;

/**
 * Servlet implementation class RecommendationEvents
 */
@WebServlet("/recommendation")
public class RecommendationEvents extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommendationEvents() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * try {
		 
			JSONObject input = RpcHelper.readJsonObject(request);
			if (input.has("user_id") ) {
				String userId = (String) input.get("user_id");
				RpcHelper.writeJsonObject(response, new JSONObject().put("status", "OK"));
			} else {
				RpcHelper.writeJsonObject(response, new JSONObject().put("status", "InvalidParameter"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		*/
		
		Restaurant panda = new Restaurant();
		panda.setName("Panda Express");
		panda.setLocation("downtown");
		panda.setCountry("United States");
		
		Restaurant hongkong = new Restaurant();
		hongkong.setName("hongkong Express");
		hongkong.setLocation("uptown");
		hongkong.setCountry("United States");
		
		JSONArray array = new JSONArray();
		try {
			array.put(new JSONObject().put("username", hongkong) );

		} catch (JSONException e) {
			e.printStackTrace();
		}
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
