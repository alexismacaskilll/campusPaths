package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.ModelConnector;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import pathfinder.datastructures.Path;

public class SparkServer {

  private static Gson gson = new Gson();
  private static ModelConnector connector = new ModelConnector();

  public static void main(String[] args) {
    CORSFilter corsFilter = new CORSFilter();
    corsFilter.apply();
    // The above two lines help set up some settings that allow the
    // React application to make requests to the Spark server, even though it
    // comes from a different server.
    // You should leave these two lines at the very beginning of main().
    Spark.get("/getPaths", new Route() {
      @Override
      public Object handle(Request request, Response response) throws Exception {
        String startBuilding = request.queryParams("start");
        String destBuilding = request.queryParams("dest");
        if(startBuilding == null || destBuilding == null) {
          // You can also have a message in "halt" that is displayed in the page.
          Spark.halt(400, "must have start and end");
        }

        Path<Point> path = connector.findShortestPath(startBuilding, destBuilding);
        // Let's get a Gson object so we can convert our Path to a JSON String
        // before we send it back.
        // Gson is Google's library for dealing with JSON. It'll take any Java Object
        // and convert it to a JSON string that represents the data inside that object.

        String jsonResponse = gson.toJson(path);
        return jsonResponse;
      }
    });

    Spark.get("/getBuildings", new Route() {
      @Override
      public Object handle(Request request, Response response) throws Exception {
        // As a first example, let's just return a static string.
        Map<String, String> buildings = connector.buildingNames();

        String jsonResponse = gson.toJson(buildings);
        return jsonResponse;

      }
    });


  }


}
