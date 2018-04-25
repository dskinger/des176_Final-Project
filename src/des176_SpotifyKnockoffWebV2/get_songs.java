package des176_SpotifyKnockoffWebV2;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import des176_SpotifyKnockoff_utils.DbUtilities;

/**
 * Servlet implementation class get_songs
 */
@WebServlet("/api/get_songs")
public class get_songs extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public get_songs() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String songs = "", artists = null, albums = null;
		String sql = "";
		JSONObject searchResults = new JSONObject();
		final int RESULTS_LIMIT = 100;
		
		HttpSession session = request.getSession(true);
		
		if (request.getParameter("songs") != null) {
			request.getParameter("songs");
			try {
				//***********************************************************
				// Let's deal with songs first
				//***********************************************************
				sql = "SELECT * FROM song WHERE title LIKE '%" + songs + "%' LIMIT " + RESULTS_LIMIT + ";";
				JSONArray songList = new JSONArray();

				DbUtilities db = new DbUtilities();
				System.out.println(sql);
				ResultSet rs = db.getResultSet(sql);
				while(rs.next()){
					JSONObject song = new JSONObject();
					song.put("song_id", rs.getString("song_id"));
					song.put("title", rs.getString("title"));
					song.put("release_date", rs.getString("release_date"));
					song.put("record_date", rs.getString("record_date"));
					song.put("length", rs.getDouble("length"));
					songList.put(song);
				}
				// Store song list in searchResults JSON object
				searchResults.put("songs", songList);
				response.getWriter().write(songList.toString());
				session.setAttribute("SEARCH_RESULTS", songList.toString());
				response.getWriter().write(songList.toString());
				db.closeDbConnection();
				db = null;
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		else if (request.getParameter("albums") != null) {
			request.getParameter("albums");
			try {	
				//***********************************************************
				// Now let's deal with albums
				//***********************************************************
					
				sql = "SELECT * FROM album WHERE title LIKE '%" + albums + "%' LIMIT " + RESULTS_LIMIT + ";";
				JSONArray albumList = new JSONArray();
					
				DbUtilities db = new DbUtilities();
				System.out.println(sql);
				ResultSet rs = db.getResultSet(sql);
				while(rs.next()){
					JSONObject album = new JSONObject();
					album.put("album_id", rs.getString("album_id"));
					album.put("title", rs.getString("title"));
					album.put("release_date", rs.getString("release_date"));
					album.put("cover_image_path", rs.getString("cover_image_path"));
					album.put("recording_company_name", rs.getString("recording_company_name"));
					album.put("number_of_tracks", rs.getInt("number_of_tracks"));
					album.put("PMRC_rating", rs.getString("PMRC_rating"));
					album.put("length", rs.getDouble("length"));
					albumList.put(album);
				}
				// Store album list in searchResults JSON object
				searchResults.put("albums", albumList);
				response.getWriter().write(albumList.toString());
				session.setAttribute("SEARCH_RESULTS", albumList.toString());
				response.getWriter().write(albumList.toString());
				
				db.closeDbConnection();
				db = null; 	
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
					
		//***********************************************************
		// Finally, let's deal with artists
		//***********************************************************
		else if (request.getParameter("artists") != null) {
			request.getParameter("artists");
				try {
					sql = "SELECT * FROM artist "
						+ "WHERE first_name LIKE '%" + artists + "%' "
						+ "OR last_name LIKE '%" + artists + "%' "
						+ "OR band_name LIKE '%" + artists + "%' "
						+ "LIMIT " + RESULTS_LIMIT + ";";
						
					JSONArray artistList = new JSONArray();
	
					DbUtilities db = new DbUtilities();
					System.out.println(sql);
					ResultSet rs = db.getResultSet(sql);
					while(rs.next()){
						JSONObject artist = new JSONObject();
						artist.put("artist_id", rs.getString("artist_id"));
						artist.put("first_name", rs.getString("first_name"));
						artist.put("last_name", rs.getString("last_name"));
						artist.put("band_name", rs.getString("band_name"));
						artist.put("bio", rs.getString("bio"));
						
						artistList.put(artist);
					}
					// Store album list in searchResults JSON object
					searchResults.put("artist", artistList);
					response.getWriter().write(artistList.toString());
					session.setAttribute("SEARCH_RESULTS", artistList.toString());
					response.getWriter().write(artistList.toString());
					db.closeDbConnection();
					db = null; 	
	
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}										
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}