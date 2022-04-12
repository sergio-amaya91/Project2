package com.dronerecon.ws;

        import javax.servlet.*;
        import javax.servlet.http.*;
        import java.io.*;
        import java.net.URL;
        import java.util.*;
        import java.security.SecureRandom;

/**
 *
 * @author Sergio Amaya
 */
public class DroneDataService extends HttpServlet{


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();

        // ##############################
        // 1. Get params passed in.

        // Get the following parameters from the request object and put them into strings:
        // area_id
        // tilex
        // tiley
        // totalcols
        // totalrows
        //r
        //g
        // ##############################

        String sAreaId = request.getParameter("area_id");
        String sTileX = request.getParameter("tilex");
        String sTileY = request.getParameter("tiley");
        String sTotalCols = request.getParameter("totalcols");
        String sTotalRows = request.getParameter("totalrows");
        String sR = request.getParameter("r");
        String sG = request.getParameter("g");

        //call cloud and pass data to DB
        try{
            //call PortalDBService
            URL url = new URL("http://127.0.0.1:8080/dronereconportal/PortalDBService?area_id=" + sAreaId + "&tilex=" + sTileX +"&tiley=" + sTileY + "&r=" + sR + "&g=" + sG);

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        } catch (Exception ex){
            ex.printStackTrace();

        }



        // ##############################
        // 2. Default value of beginning direction.

        // Set a string called sDirection to "right".
        // ##############################
        String sDirection = "right";


        // ##############################
        // 3. Calculate next drone move.

        // Determine next tile to move to.
        // Base this on current x and y.
        // Change sDirection if necessary.
        // Drone must serpentine from top left of grid back and forth down.
        // If rows are done, change sDirection to "stop".
        // ##############################
        int x = Integer.parseInt(sTileX);
        int y = Integer.parseInt(sTileY);
        int iTotalRows = Integer.parseInt(sTotalRows);
        int iTotalCols = Integer.parseInt(sTotalCols);

        if((y%2)==0 && y== (iTotalRows-1) && x == (iTotalCols-1)){
            sDirection = "stop";

        }else if((y%2)!= 0 && y == (iTotalRows-1) && x == 0){
            sDirection = "stop";
        }else if((y%2) != 0 && x > 0 ){
            sDirection ="left";
            x--;
        }
        else if(x ==(iTotalCols-1)){
            sDirection = "left";
            y++;
        }else if(x == 0 && (y%2) != 0 && y > 0){
            sDirection = "right";
            y++;
        }else{
            x++;
        }
        String sNextTileX = Integer.toString(x);
        String sNextTileY = Integer.toString(y);



        // ##############################
        // 4. Format & Return JSON string to caller.

        /* Return via out.println() a JSON string like this:
        {"area_id":"[area id from above]", "nextTileX":"[next tile x]", "nextTileY":"[next tile y]", "direction":"[direction string from above]"}
        */
        // ##############################
        out.println("{\"area_id\":\""+sAreaId+"\",  \"nextTileX\":\""+sNextTileX+"\",  \"nextTileY\":\""+sNextTileY+"\",  \"direction\":\""+sDirection+"\"}");

    }
}

