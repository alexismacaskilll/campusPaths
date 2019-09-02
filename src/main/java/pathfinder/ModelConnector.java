/*
 * Copyright ©2019 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2019 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder;

import graph.DirectedGraph;
import graph.DirectedLabeledEdge;
import pathfinder.datastructures.Path;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.*;

import pathfinder.datastructures.Point;

/*
In the pathfinder homework, the text user interface calls these methods to talk
to your model. In the campuspaths homework, your graphical user interface
will ultimately make class to these methods (through a web server) to
talk to your model the same way.

This is the power of the Model-View-Controller pattern, two completely different
user interfaces can use the same model to display and interact with data in
different ways, without requiring a lot of work to change things over.
*/

/**
 * This class represents the connection between the view and controller and the model
 * for the pathfinder and campus paths applications.
 */
public class ModelConnector {

  private DirectedGraph<Point, Double> campusGraph;
  //maps short name to campus building
  private Map<String, CampusBuilding> shortNameMap;
  //maps short name to point
  private Map<String, Point> shortToPoint;
  //maps short name to long name
  private Map<String, String> shortToLongMap;
  //maps coordinates of the building to the building
  private Map<Point, CampusBuilding> pointToBuilding;
  private final static boolean CHECK_REP_ON = false;


  // Abstraction Function:
    // campusGraph is all of the possible paths on UW campus
    // shortNameMap is all the buildings on the UW campus
    // pointToBuilding is the coordinates of all of the buildings on the UW campus.

  // RepInvariant:
    // campusGraph != null && shortNameMap != null && shortToLongMap != null &&
    // pointToBuilding != null
    // Each key and value shortToLongMap != null &&
    // each key and value and the contents of the value in shortNameMap != null &&
    // each key and value and contents of the key and value in pointToBuilding != null


  /** Throws an exception if the representation invariant is violated. */
  private void checkRep() {
        if (CHECK_REP_ON) {
            assert (campusGraph != null);
            assert(shortNameMap != null);
            assert (shortToLongMap != null);
            assert (pointToBuilding != null);
            for (String shortName: shortNameMap.keySet()) {
                //checks each shortName != null
                assert (shortName != null);
                //checks each building != null and longname in building != null
                assert(shortNameMap.get(shortName) != null);
                assert(shortNameMap.get(shortName).getLongName() != null);
            }
            for (String shortName: shortToLongMap.keySet()) {
                //checks each shortName != null
                assert (shortName != null);
                //checks each longName != null
                assert(shortToLongMap.get(shortName) != null);
            }
            for (Point point: pointToBuilding.keySet()) {
                //checks each point != null
                assert (point != null );
                //checks each building != null
                assert(pointToBuilding.get(point) != null);
                // checks each buildings short and long name != null
                assert(pointToBuilding.get(point).getShortName() != null &&
                        pointToBuilding.get(point).getLongName() != null );
            }
        }
  }


  /**
   * Creates a new {@link ModelConnector} and initializes it to contain data about
   * pathways and buildings or locations of interest on the campus of the University
   * of Washington, Seattle. When this constructor completes, the dataset is loaded
   * and prepared, and any method may be called on this object to query the data.
   */
  public ModelConnector() {
    List<CampusBuilding> buildings = CampusPathsParser.parseCampusBuildings();
    List<CampusPath> paths = CampusPathsParser.parseCampusPaths();

    pointToBuilding = new HashMap<>();
    shortNameMap = new HashMap<>();
    shortToPoint = new HashMap<>();
    shortToLongMap = new HashMap<>();
    for (CampusBuilding building : buildings) {
      if (building != null) {
        shortToPoint.put(building.getShortName(),new Point(building.getX(), building.getY()) );
        pointToBuilding.put(new Point(building.getX(), building.getY()), building);
        shortNameMap.put(building.getShortName(), building);
        shortToLongMap.put(building.getShortName(), building.getLongName());
      }
    }

    campusGraph = Dijkstra.loadGraph(paths);
    checkRep();
  }

  /**
   * @param shortName The short name of a building to query.
   * @return {@literal true} iff the short name provided exists in this campus map.
   */
  public boolean shortNameExists(String shortName) {
      checkRep();
      return shortToLongMap.containsKey(shortName);
  }

  /**
   * @param shortName The short name of a building to look up.
   * @return The long name of the building corresponding to the provided short name.
   * @throws IllegalArgumentException if the short name provided does not exist.
   */
  public String longNameForShort(String shortName) {
    checkRep();
    if (!shortToLongMap.containsKey(shortName)) {
      throw new IllegalArgumentException("The short name provided does not exist.");
    }
    return shortToLongMap.get(shortName);
  }

  /**
   * @return The mapping from all the buildings' short names to their long names in this campus map.
   */
  public Map<String, String> buildingNames() {
    checkRep();
    return Collections.unmodifiableMap(shortToLongMap);
  }

  /**
   * Finds the shortest path, by distance, between the two provided buildings.
   *
   * @param startShortName The short name of the building at the beginning of this path.
   * @param endShortName   The short name of the building at the end of this path.
   * @return A path between {@code startBuilding} and {@code endBuilding}, or {@literal null}
   * if none exists.
   * @throws IllegalArgumentException if {@code startBuilding} or {@code endBuilding} are
   *                                  {@literal null}, or not valid short names of buildings in
   *                                  this campus map.
   */
  public Path<Point> findShortestPath(String startShortName, String endShortName) {
    checkRep();
    if(startShortName == null || endShortName == null) {
      throw new IllegalArgumentException("startShortName and endShortName cannot be null.");
    }
    if ( (!shortNameMap.containsKey(startShortName)) ||  (!shortNameMap.containsKey(endShortName))) {
      throw new IllegalArgumentException("startShortName and endShortName must be valid short names of buildings" +
              "in this campus map.");
    }
    Path<Point> path = Dijkstra.findPath(campusGraph, shortToPoint.get(startShortName),
            shortToPoint.get(endShortName));
    return path;
  }





}



