import java.util.ArrayList;

/**
 * Represents a cell in a Zigzag board
 * 
 * @author dmarts05
 */
public class Node {
  /**
   * Stores the value of the node (between 1 and 9)
   */
  private int value;

  /**
   * Stores a list of node coordinates that are restricted (node can't be linked
   * to these nodes)
   */
  private ArrayList<int[]> restrictedNodesCoordinates = new ArrayList<int[]>();

  /**
   * Stores this node's coordinates in the board
   */
  private int[] nodeCoordinates = { -1, -1 };

  /**
   * Stores linked node's coordinates in the board
   */
  private int[] linkNodeCoordinates = { -1, -1 };

  /**
   * Indicates the kind of link between this node and the linked node
   * 5: Not linked to any node
   * 1: \ (upper-left node)
   * 2: | (upper node)
   * 3: / (upper-right node)
   * 6: - (right node)
   * 9: \ (lower-right node)
   * 8: | (lower node)
   * 7: / (lower-left node)
   * 4: - (left node)
   * 
   * 1 2 3
   * 4 5 6
   * 7 8 9
   */
  private int linkType = 5;

  /**
   * Stores whether this Node has already been checked
   */
  private boolean checked = false;

  /**
   * Creates a board node
   * 
   * @param value            Node's value
   * @param rowCoordinate    Node's row coordinate in the board
   * @param columnCoordinate Node's column coordinate in the board
   * @throws IllegalArgumentException Thrown when value is not between 1 and 9
   */
  public Node(int value, int rowCoordinate, int columnCoordinate) {
    if (value < 1 || value > 9) {
      throw new IllegalArgumentException();
    }

    this.value = value;
    this.nodeCoordinates[0] = rowCoordinate;
    this.nodeCoordinates[1] = columnCoordinate;
  }

  /**
   * Returns the value of the node
   * 
   * @return Value of the node
   */
  public int getValue() {
    return this.value;
  }

  /**
   * Returns this node's coordinates
   * 
   * @return This node's coordinates
   */
  public int[] getCoordinates() {
    return this.nodeCoordinates;
  }

  /**
   * Returns linked node's coordinates
   * 
   * @return Linked node's coordinates
   */
  public int[] getLinkCoordinates() {
    return this.linkNodeCoordinates;
  }

  /**
   * Returns this node's type of link
   * 
   * @return This node's type of link
   */
  public int getLinkType() {
    return this.linkType;
  }

  /**
   * Returns whether a node's been checked
   * 
   * @return If a node has been checked
   */
  public boolean isChecked() {
    return this.checked;
  }

  /**
   * Changes whether a node has been checked or not depending on the given status
   * 
   * @param status New node checked status
   */
  public void changeCheckedStatus(boolean status) {
    this.checked = status;
  }

  /**
   * Sets linked node's coordinates for this node
   * 
   * @param rowCoordinate    Linked node's row coordinate in the board
   * @param columnCoordinate Linked node's column coordinate in the board
   */
  public void setLinkNodeCoordinates(int rowCoordinate, int columnCoordinate) {
    this.linkNodeCoordinates[0] = rowCoordinate;
    this.linkNodeCoordinates[1] = columnCoordinate;
  }

  /**
   * Sets the link type of this node with its linked node
   * 
   * @param linkType type of link established between this node and its linked
   *                 node
   */
  public void setLinkType(int linkType) {
    this.linkType = linkType;
  }

  /**
   * Restricts this node from linking itself to the node associated with the
   * introduced coordinates
   * 
   * @param rowCoordinate    Restricted node's row coordinate in the board
   * @param columnCoordinate Restricted node's column coordinate in the board
   */
  public void addRestrictedNodeCoordinates(int rowCoordinate, int columnCoordinate) {
    int[] coordinates = { rowCoordinate, columnCoordinate };
    this.restrictedNodesCoordinates.add(coordinates);
  }

  /**
   * Removes every restricted coordinate of this node
   */
  public void cleanRestrictedNodesCoordinates() {
    this.restrictedNodesCoordinates.clear();
  }

  /**
   * Creates a new ArrayList containing every restricted node coordinates and
   * returns that
   * 
   * @return ArrayList containing every restricted node coordinates
   */
  public ArrayList<int[]> getRestrictedNodesCoordinates() {
    ArrayList<int[]> copy = new ArrayList<int[]>();

    for (int[] coordinates : this.restrictedNodesCoordinates) {
      int[] copyCoordinates = coordinates.clone();
      copy.add(copyCoordinates);
      ;
    }

    return copy;
  }

  /**
   * Checks whether a node is restricted or not
   * 
   * @param rowCoordinate    Checked node's row coordinate in the board
   * @param columnCoordinate Checked node's column coordinate in the board
   * @return true if the node is restricted, false if it's not restricted
   */
  public boolean areNodeCoordinatesRestricted(int rowCoordinate, int columnCoordinate) {
    for (int[] restrictedCoordinates : restrictedNodesCoordinates) {
      if (restrictedCoordinates[0] == rowCoordinate && restrictedCoordinates[1] == columnCoordinate) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return String.valueOf(this.value);
  }
}
