package edu.brown.cs.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import edu.brown.cs.catan.Resource;

public class Tile {
  private Collection<Intersection> _intersections;
  private int _rollNum;
  private Resource _type;
  private HexCoordinate _coordinate;
  
  public Tile(int rollNum, HexCoordinate coordinate,
      Map<IntersectionCoordinate, Intersection> intersections,
      Map<PathCoordinate, Path> paths, TileType type) {
    _type = type.getType();
    _rollNum = rollNum;
    _coordinate = coordinate;
    fillEdges(intersections, paths);

  }

  private void fillEdges(
      Map<IntersectionCoordinate, Intersection> intersections, Map<PathCoordinate, Path> paths) {
    _intersections = new ArrayList<Intersection>();
    HexCoordinate upLeftTile = new HexCoordinate(_coordinate.getX(),
        _coordinate.getY(), _coordinate.getZ() + 1);
    HexCoordinate upRightTile = new HexCoordinate(_coordinate.getX(),
        _coordinate.getY() + 1, _coordinate.getZ() + 1);
    HexCoordinate rightTile = new HexCoordinate(_coordinate.getX(),
        _coordinate.getY() + 1, _coordinate.getZ());
    HexCoordinate lowerRightTile = new HexCoordinate(_coordinate.getX() + 1,
        _coordinate.getY() + 1, _coordinate.getZ());
    HexCoordinate lowerLeftTile = new HexCoordinate(_coordinate.getX() + 1,
        _coordinate.getY(), _coordinate.getZ());
    HexCoordinate leftTile = new HexCoordinate(_coordinate.getX() + 1,
        _coordinate.getY(), _coordinate.getZ() + 1);
    
    IntersectionCoordinate top = new IntersectionCoordinate(_coordinate,
        upLeftTile, upRightTile);
    IntersectionCoordinate upRight = new IntersectionCoordinate(_coordinate,
        upRightTile, rightTile);
    IntersectionCoordinate lowerRight = new IntersectionCoordinate(_coordinate,
        rightTile, lowerRightTile);
    IntersectionCoordinate bottom = new IntersectionCoordinate(_coordinate,
        lowerRightTile, lowerLeftTile);
    IntersectionCoordinate lowerLeft = new IntersectionCoordinate(_coordinate,
        lowerLeftTile, leftTile);
    IntersectionCoordinate upLeft = new IntersectionCoordinate(_coordinate,
        leftTile, upLeftTile);

    fillIntersections(intersections, top);
    fillIntersections(intersections, upRight);
    fillIntersections(intersections, lowerRight);
    fillIntersections(intersections, bottom);
    fillIntersections(intersections, lowerLeft);
    fillIntersections(intersections, upLeft);
    
    fillPaths(intersections.get(top), intersections.get(upRight), paths);
    fillPaths(intersections.get(upRight), intersections.get(lowerRight), paths);
    fillPaths(intersections.get(lowerRight), intersections.get(bottom), paths);
    fillPaths(intersections.get(bottom), intersections.get(lowerLeft), paths);
    fillPaths(intersections.get(lowerLeft), intersections.get(upLeft), paths);
    fillPaths(intersections.get(upLeft), intersections.get(top), paths);

  }

  private void fillIntersections(
      Map<IntersectionCoordinate, Intersection> intersections,
      IntersectionCoordinate coord) {
    if(intersections.containsKey(coord)) {
      _intersections.add(intersections.get(coord));
    } else {
      Intersection newIntersect = new Intersection(coord);
      intersections.put(coord, newIntersect);
      _intersections.add(newIntersect);
    }
  }

  private void fillPaths(Intersection start, Intersection end,
      Map<PathCoordinate, Path> paths) {
    PathCoordinate path = new PathCoordinate(start.getPosition(), end.getPosition());
    if(!paths.containsKey(path)) {
      paths.put(path, new Path(start, end));
    }
  }

  public int getRollNum() {
    return _rollNum;
  }

  public Resource getType() {
    return _type;
  }

  public void notifyIntersections() {
    for (Intersection i : _intersections) {
      i.notifyBuilding(_type);
    }
  }

  public HexCoordinate getCoordinate() {
    return _coordinate;
  }

  public Collection<Intersection> getIntersections() {
    return _intersections;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Tile) {
      Tile other = (Tile) obj;
      if (other.getCoordinate().equals(_coordinate)) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return _coordinate.hashCode();
  }

}
