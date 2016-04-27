package wasdev.sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.ArrayList;
/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet("/SimpleServlet")
public class SimpleServlet extends HttpServlet {
	
    private static final long serialVersionUID = 1L;
	public static int blClicks = 1;
	public static int brClicks = 1;
	public static int tlClicks = 1;
	public static int trClicks = 1;
	public static String tlImage = "";
	public static String trImage = "";
	public static String blImage = "";
	public static String brImage = "";
	public static String winner = "";
	public static String winner2 = "";
	public static ArrayList<String> a = new ArrayList<String>();
	public static String gameStatus = "0";
	public static int firstT = 0;
	public static long initialTime = 0;
	public static long startTime = 0;
	public static long endTime = 0;
	public static int numberTimesGot = 0;
	public static boolean stateChanged = false;
	public static boolean firstAlive = false;
	public static String defImg = "http://animalscamp.com/wp-content/uploads/2011/12/Pygmy-Hippopotamus-2.jpg";
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	/*
        response.setContentType("text/html");
        response.getWriter().print("Hello Roy!");
        */
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter writer = response.getWriter();
    	String click_topLeft = request.getParameter("topLeft");
		String click_topRight = request.getParameter("topRight");
		String click_bottomLeft = request.getParameter("bottomLeft");
		String click_bottomRight = request.getParameter("bottomRight");
		String click_update = request.getParameter("update");
		String click_image = request.getParameter("image");
		String click_game = request.getParameter("game");
		String click_winner = request.getParameter("winner");
		String click_winnerRequest = request.getParameter("winnerRequest");
		
		if (click_topLeft != null) {
			tlClicks++;
		}
		else if (click_topRight != null) {
			trClicks++;
		}
		else if (click_bottomLeft != null) {
			blClicks++;
		}
		else if (click_bottomRight != null) {
			brClicks++;
		}
		else if (click_update != null) {
		
		}
		else if (click_image != null) {
			a.add(click_image);
			return;
		}
		else if (click_game != null) {
			if (numberTimesGot == 0) {
				initialTime = System.currentTimeMillis();

				startTime = initialTime + 3000;
				endTime = startTime + 30000;
				numberTimesGot++;
			}
			if (System.currentTimeMillis() > startTime && System.currentTimeMillis() < endTime) {
				if(firstAlive) {
					firstT = 0;
					/*
					trClicks = 1;
					tlClicks = 1;
					brClicks = 1;
					blClicks = 1;
					*/
					firstAlive = false;
				}
				
				writer.println("1");	
				stateChanged = true;
			}
			else if (System.currentTimeMillis() > endTime) {
				startTime = endTime +30000;
				endTime = startTime +30000;
				trClicks = 1;
				tlClicks = 1;
				brClicks = 1;
				blClicks = 1;
				if (stateChanged) {
					if (!SimpleServlet.winner.equals("6")) {
						if (!SimpleServlet.winner.equals("1")) {
			    		// top left wasn't winner so change
			    			SimpleServlet.tlImage = SimpleServlet.getRandImage();
			    			if (SimpleServlet.tlImage == null) {
			    				SimpleServlet.tlImage = defImg;
			    			}
			    		}
			    		if (!SimpleServlet.winner.equals("2")) {
			    		// top right wasn't winner
			    			SimpleServlet.trImage = SimpleServlet.getRandImage();
			    			if (SimpleServlet.trImage == null) {
			    				SimpleServlet.trImage = defImg;
			    			}
			    		}
				    	if (!SimpleServlet.winner.equals("3")) {
				    		// bottom left wasn't winner
				    		SimpleServlet.blImage = SimpleServlet.getRandImage();
			    			if (SimpleServlet.blImage == null) {
			    				SimpleServlet.blImage = defImg;
			    			}
				    	}
				    	if (!SimpleServlet.winner.equals("4")) {
				    		// bottom right wasn't winner
				    		SimpleServlet.brImage = SimpleServlet.getRandImage();
			    			if (SimpleServlet.brImage == null) {
			    				SimpleServlet.brImage = defImg;
			    			}
				    	}
	//			    	SimpleServlet.a.clear();
				    	stateChanged = false;
		    		}
	    		}
				firstAlive = true;
				writer.println("0");
			}
			else if (System.currentTimeMillis() < startTime) {
				writer.println("0");
				SimpleServlet.tlImage = defImg;
				SimpleServlet.trImage = defImg;
				SimpleServlet.blImage = defImg;
				SimpleServlet.brImage = defImg;
			}
			return;
		}
		if (click_winner != null) {
			if (!click_winner.equals("6")) {
				if (firstT == 0) {
					winner2 = click_winner; 
				}
				else {
					if (winner2.equals("5") && !click_winner.equals("5")) {
						winner2 = click_winner;
					}
					else if (!winner2.equals("5")) {
						
					}
				}
				winner = winner2;
				firstT++;
			}

			String responding = "";
			responding += SimpleServlet.tlImage;
			responding += "\t";
			responding += SimpleServlet.trImage;
			responding += "\t";
			responding += SimpleServlet.blImage;
			responding += "\t";
			responding += SimpleServlet.brImage;
			responding += "\t";
			writer.println(responding);
		}
		if (click_winnerRequest != null) {
			if (winner2.equals("1")) {
				writer.println("Top Left Wins");
			}
			else if (winner2.equals("2")) {
				writer.println("Top Right Wins");
			}
			else if (winner2.equals("3")) {
				writer.println("Bottom Left Wins");
			}
			else if (winner2.equals("4")) {
				writer.println("Bottom Right Wins");
			}
			else {
				writer.println("Tie");
			}
			return;
		}
		writer.println(tlClicks + " " + trClicks + " " + blClicks + " " + brClicks);

    }
 
    protected static String getRandImage() {
    	Random r = new Random();
    	int randomInt = r.nextInt(a.size());
    	return a.get(randomInt);
    }
}