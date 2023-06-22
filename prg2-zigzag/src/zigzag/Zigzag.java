package zigzag;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a Zigzag problem
 */
public class Zigzag {
  /**
   * Stores a set of nodes in a matrix layout
   */
  Node board[][];

  /**
   * Stores every unique number used in the Zigzag problem
   */
  ArrayList<Integer> zigzagNumbers = new ArrayList<Integer>();

  /**
   * Creates a Zigzag problem from a given board
   * 
   * @param board Board containing a set of nodes that define a Zigzag problem
   */
  public Zigzag(Node[][] board) {
    int rows = board.length;
    int columns = board[0].length;

    this.board = new Node[rows][columns];

    // Get numbers of the board and copy each node to our board
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        Node node = new Node(board[i][j].getValue(), i, j);

        if (!zigzagNumbers.contains(node.getValue())) {
          zigzagNumbers.add(node.getValue());
        }

        this.board[i][j] = node;
      }
    }

    // Sort unique numbers used in the board
    Collections.sort(zigzagNumbers);
  }

  /**
   * Solves the board of a Zigzag object
   * 
   * @return A list containing every possible board solution
   */
  public ArrayList<Node[][]> solveZigzag() {
    ArrayList<Node[][]> solutions = new ArrayList<Node[][]>();

    // Fix 1 x 1 case
    if (board.length == 1 && board[0].length == 1) {
      solutions.add(board);
      return solutions;
    }

    Node currentNode = board[0][0];
    int rows = board.length;
    int columns = board[0].length;

    while (true) {
      // Check if this is the last node
      if (board[rows - 1][columns - 1].equals(currentNode)) {
        if (isEveryNodeLinked()) {
          // Add board solution to solution ArrayList
          Node[][] solution = getCopyOfBoard();
          solutions.add(solution);
        }

        // Get back to previous node, unlink and restrict this one if it exists
        if (getNodeFromCoordinates(currentNode.getLinkCoordinates()) != null) {
          Node previousNode = getNodeFromCoordinates(currentNode.getLinkCoordinates());
          unlinkNodes(currentNode, previousNode);
          previousNode.addRestrictedNodeCoordinates(currentNode.getCoordinates()[0], currentNode.getCoordinates()[1]);
          // Get back to previous node and try to find another solution
          currentNode = previousNode;
        }
      } else {
        // Try to link this node to another one with known restrictions
        Node nextNode = setLinkToValidNode(currentNode);

        // Check if the link was not completed successfully
        if (nextNode == null) {
          // Clean current node restricted nodes before getting back to the previous one
          // only if it's not the first node
          if (!board[0][0].equals(currentNode)) {
            currentNode.cleanRestrictedNodesCoordinates();
          }
          // Get back to previous node, unlink and restrict this one if it exists
          if (getNodeFromCoordinates(currentNode.getLinkCoordinates()) != null) {
            Node previousNode = getNodeFromCoordinates(currentNode.getLinkCoordinates());
            unlinkNodes(currentNode, previousNode);
            previousNode.addRestrictedNodeCoordinates(currentNode.getCoordinates()[0], currentNode.getCoordinates()[1]);
            currentNode = previousNode;
          }
        } else {
          // Continue with next node
          currentNode = nextNode;
        }

        // If the first node is reached and there are no more links available, there are
        // no more solutions
        if (board[0][0].equals(currentNode) && getLinkTypeToValidNode(currentNode) == -1) {
          break;
        }
      }
    }

    return solutions;
  }

  /**
   * Finds the node sitting in the given coordinates
   * 
   * @param coordinates Coordinates of the searched node
   * @return
   */
  private Node getNodeFromCoordinates(int[] coordinates) {
    Node node = null;
    try {
      node = this.board[coordinates[0]][coordinates[1]];
    } catch (IndexOutOfBoundsException e) {
      // Coordinates are not valid
      node = null;
    }
    return node;
  }

  /**
   * Obtains a deeep copy of the current status of the board and its nodes
   * 
   * @return A deep copy of the current status of the board and its nodes
   */
  private Node[][] getCopyOfBoard() {
    Node[][] copy = new Node[this.board.length][this.board[0].length];

    for (int i = 0; i < this.board.length; i++) {
      for (int j = 0; j < this.board[0].length; j++) {
        // Gather source node information
        Node sourceNode = this.board[i][j];
        int value = sourceNode.getValue();
        int[] coordinates = sourceNode.getCoordinates();
        int[] linkNodeCoordinates = sourceNode.getLinkCoordinates();
        int linkType = sourceNode.getLinkType();
        ArrayList<int[]> restrictedNodesCoordinates = new ArrayList<int[]>();

        // Copy this information into a new node
        Node copyNode = new Node(value, coordinates[0], coordinates[1]);
        copyNode.setLinkNodeCoordinates(linkNodeCoordinates[0], linkNodeCoordinates[1]);
        copyNode.setLinkType(linkType);
        for (int[] restrictedCoordinates : restrictedNodesCoordinates) {
          copyNode.addRestrictedNodeCoordinates(restrictedCoordinates[0], restrictedCoordinates[1]);
        }

        // Add new node to copy board
        copy[i][j] = copyNode;
      }
    }

    return copy;
  }

  /**
   * Obstains a valid link type from which the given node can be linked
   * 
   * @param currentNode Node that will be checked to find a valid linkable node
   * @return A valid link type from which the given node can be linked or -1 if it
   *         doesn't exist
   */
  private int getLinkTypeToValidNode(Node currentNode) {
    int[] currentNodeCoordinates = currentNode.getCoordinates();
    int currentNodeRow = currentNodeCoordinates[0];
    int currentNodeColumn = currentNodeCoordinates[1];

    Node nextNode = null;
    int nextNodeLinkType = 5;

    // Try 8 possible combinations following lexicographic order
    for (int i = 1; i <= 9; i++) {
      switch (i) {
        // Using the perspective of currentNode
        case 1:
          // Upper left
          try {
            nextNode = board[currentNodeRow - 1][currentNodeColumn - 1];
            // Set nextNode's linkType to lower right
            nextNodeLinkType = 9;
          } catch (IndexOutOfBoundsException e) {
            continue;
          }
          break;
        case 2:
          // Upper
          try {
            nextNode = board[currentNodeRow - 1][currentNodeColumn];
            // Set nextNode's linkType to lower
            nextNodeLinkType = 8;
          } catch (IndexOutOfBoundsException e) {
            continue;
          }
          break;
        case 3:
          // Upper right
          try {
            nextNode = board[currentNodeRow - 1][currentNodeColumn + 1];
            // Set nextNode's linkType to lower left
            nextNodeLinkType = 7;
          } catch (IndexOutOfBoundsException e) {
            continue;
          }
          break;
        case 4:
          // Left
          try {
            nextNode = board[currentNodeRow][currentNodeColumn - 1];
            // Set nextNode's linkType to right
            nextNodeLinkType = 6;
          } catch (IndexOutOfBoundsException e) {
            continue;
          }
          break;
        case 6:
          // Right
          try {
            nextNode = board[currentNodeRow][currentNodeColumn + 1];
            // Set nextNode's linkType to left
            nextNodeLinkType = 4;
          } catch (IndexOutOfBoundsException e) {
            continue;
          }
          break;
        case 7:
          // Lower left
          try {
            nextNode = board[currentNodeRow + 1][currentNodeColumn - 1];
            // Set nextNode's linkType to upper right
            nextNodeLinkType = 3;
          } catch (IndexOutOfBoundsException e) {
            continue;
          }
          break;
        case 8:
          // Lower
          try {
            nextNode = board[currentNodeRow + 1][currentNodeColumn];
            // Set nextNode's linkType to upper
            nextNodeLinkType = 2;
          } catch (IndexOutOfBoundsException e) {
            continue;
          }
          break;
        case 9:
          // Lower right
          try {
            nextNode = board[currentNodeRow + 1][currentNodeColumn + 1];
            // Set nextNode's linkType to upper left
            nextNodeLinkType = 1;
          } catch (IndexOutOfBoundsException e) {
            continue;
          }
          break;
        default:
          continue;
      }

      // Check if a valid link can be established between the nodes
      if (isNextNumber(currentNode, nextNode)
          && !currentNode.areNodeCoordinatesRestricted(nextNode.getCoordinates()[0], nextNode.getCoordinates()[1])
          && !nextNode.isChecked() && !areLinksCrossed(currentNode, nextNode, nextNodeLinkType)) {
        return nextNodeLinkType;
      }
    }

    return -1;

  }

  /**
   * Sets a link between the given node and a valid node (if it exists)
   * 
   * @param currentNode Node that will be linked to a suitable node (if it exists)
   * @return Found node that was linked to the given node or null if it wasn't
   *         found
   */
  private Node setLinkToValidNode(Node currentNode) {
    int[] currentNodeCoordinates = currentNode.getCoordinates();
    int currentNodeRow = currentNodeCoordinates[0];
    int currentNodeColumn = currentNodeCoordinates[1];
    int nextNodeLinkType = getLinkTypeToValidNode(currentNode);
    Node nextNode = null;
    if (nextNodeLinkType != -1) {
      switch (nextNodeLinkType) {
        // Using the perspective of currentNode
        case 9:
          // Upper left
          nextNode = board[currentNodeRow - 1][currentNodeColumn - 1];
          break;
        case 8:
          // Upper
          nextNode = board[currentNodeRow - 1][currentNodeColumn];
          break;
        case 7:
          // Upper right
          nextNode = board[currentNodeRow - 1][currentNodeColumn + 1];
          break;
        case 6:
          // Left
          nextNode = board[currentNodeRow][currentNodeColumn - 1];
          break;
        case 4:
          // Right
          nextNode = board[currentNodeRow][currentNodeColumn + 1];
          break;
        case 3:
          // Lower left
          nextNode = board[currentNodeRow + 1][currentNodeColumn - 1];
          break;
        case 2:
          // Lower
          nextNode = board[currentNodeRow + 1][currentNodeColumn];
          break;
        case 1:
          // Lower right
          nextNode = board[currentNodeRow + 1][currentNodeColumn + 1];
          break;
        default:
          break;
      }

      linkNodes(currentNode, nextNode, nextNodeLinkType);
    }

    return nextNode;
  }

  /**
   * Checks if two nodes can create a crossing scenario if the are linked
   * 
   * @param currentNode      First node in the linking process
   * @param nextNode         Second node in the linking process
   * @param nextNodeLinkType Link type of the given nodes (nextNode to
   *                         currentNode)
   * @return true if there is crossing, false if there isn't
   */
  private boolean areLinksCrossed(Node currentNode, Node nextNode, int nextNodeLinkType) {
    int[] currentNodeCoordinates = currentNode.getCoordinates();
    int currentNodeRow = currentNodeCoordinates[0];
    int currentNodeColumn = currentNodeCoordinates[1];
    boolean crossed = false;

    // Crossing can't happen for vertical and horizontal links, only for diagonal
    // links
    // Check crossing for cases 1, 3, 7 and 9
    // Using the perspective of currentNode
    switch (nextNodeLinkType) {
      case 9:
        // Check if left node has a type 3 link "/"
        try {
          Node leftNode = board[currentNodeRow][currentNodeColumn - 1];
          if (leftNode.getLinkType() == 3) {
            crossed = true;
          }
        } catch (IndexOutOfBoundsException e) {
          // Node doesn't exist
        } finally {
          // Check if upper node has a type 7 link "/"
          try {
            Node upperNode = board[currentNodeRow - 1][currentNodeColumn];
            if (upperNode.getLinkType() == 7) {
              crossed = true;
            }
          } catch (IndexOutOfBoundsException e) {
            // Node doesn't exist
          }
        }
        break;
      case 7:
        // Check if right node has a type 1 link "\"
        try {
          Node rightNode = board[currentNodeRow][currentNodeColumn + 1];
          if (rightNode.getLinkType() == 1) {
            crossed = true;
          }
        } catch (IndexOutOfBoundsException e) {
          // Node doesn't exist
        } finally {
          // Check if upper node has a type 9 link "\"
          try {
            Node upperNode = board[currentNodeRow - 1][currentNodeColumn];
            if (upperNode.getLinkType() == 9) {
              crossed = true;
            }
          } catch (IndexOutOfBoundsException e) {
            // Node doesn't exist
          }
        }
        break;
      case 1:
        // Check if right node has a type 8 link "/"
        try {
          Node rightNode = board[currentNodeRow][currentNodeColumn + 1];
          if (rightNode.getLinkType() == 8) {
            return true;
          }
        } catch (IndexOutOfBoundsException e) {
          // Node doesn't exist
        } finally {
          // Check if lower node has a type 3 link "/"
          try {
            Node lowerNode = board[currentNodeRow + 1][currentNodeColumn];
            if (lowerNode.getLinkType() == 3) {
              crossed = true;
            }
          } catch (IndexOutOfBoundsException e) {
            // Node doesn't exist
          }
        }
        break;
      case 3:
        // Check if left node has a type 9 link "\"
        try {
          Node leftNode = board[currentNodeRow][currentNodeColumn - 1];
          if (leftNode.getLinkType() == 9) {
            return true;
          }
        } catch (IndexOutOfBoundsException e) {
          // Node doesn't exist
        } finally {
          // Check if lower node has a type 1 link "\"
          try {
            Node lowerNode = board[currentNodeRow + 1][currentNodeColumn];
            if (lowerNode.getLinkType() == 1) {
              crossed = true;
            }
          } catch (IndexOutOfBoundsException e) {
            // Node doesn't exist
          }
        }
        break;

      default:
        break;
    }

    return crossed;
  }

  /**
   * Links two given nodes
   * 
   * @param currentNode First node involved in the linking process
   * @param nextNode    Second node involved in the linking process
   * @param linkType    Link type between the given nodes
   */
  private void linkNodes(Node currentNode, Node nextNode, int linkType) {
    currentNode.changeCheckedStatus(true);
    nextNode.setLinkNodeCoordinates(currentNode.getCoordinates()[0], currentNode.getCoordinates()[1]);
    nextNode.setLinkType(linkType);
  }

  private void unlinkNodes(Node currentNode, Node previousNode) {
    previousNode.changeCheckedStatus(false);
    currentNode.setLinkNodeCoordinates(-1, -1);
    currentNode.setLinkType(5);
  }

  private boolean isNextNumber(Node currentNode, Node nextNode) {
    int min = zigzagNumbers.get(0);
    int max = zigzagNumbers.get(zigzagNumbers.size() - 1);

    int currentNodeValue = currentNode.getValue();
    int nextNodeValue = nextNode.getValue();

    int nextCurrentNodeValue = currentNodeValue;

    // Get next value of currentNode
    if (currentNodeValue == max) {
      nextCurrentNodeValue = min;
    } else {
      nextCurrentNodeValue++;
    }

    if (nextCurrentNodeValue == nextNodeValue && currentNodeValue != nextNodeValue) {
      return true;
    }

    return false;
  }

  /**
   * Checks if every node except the last one has been linked
   * 
   * @return true if every node is linked (except last one), false if a node is
   *         not linked
   */
  private boolean isEveryNodeLinked() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        Node currentNode = board[i][j];
        // Skip last node of the board as it doesn't need to be checked
        if (board[board.length - 1][board[0].length - 1].equals(currentNode)) {
          continue;
        } else if (!currentNode.isChecked()) {
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        output.append(board[i][j].toString());

        if (j != board[0].length - 1) {
          output.append(" ");
        }
      }

      if (i != board.length - 1) {
        output.append("\n");
      }
    }

    return output.toString();
  }
}
