/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avl;

import Comom.Tree;

/**
 *
 * @author ddung
 */
public class AvlTreeLogic {
        private static Tree tree = null;
	private static int added; // gia tri duoc them
	private static int removed; // gia tri dang bi xoa
	private static int degenerated; // ghi gia tri node dang bi mat can bang

	public static Tree getTree() {
		return tree;
	}
	
	public static int getAdded() {
		return added;
	}
	
	public static void setAdded(int val) {
		added = val;
	}
	
	public static int getRemoved() {
		return removed;
	}
	
	public static void setDegenerated(int val) {
                degenerated = val;
	}
	
	public static int getDegenerated() {
		return degenerated;
	}
	
	public static void setRemoved(int val) {
		removed = val;
	}
	

	public Tree addNode (int value) {
            Tree newNode;
		if (this.tree == null) {
			this.tree = new Tree(value);
                        newNode = this.tree;
		} else {
			newNode = addNode(this.tree, value);
		}
		
		this.added = value;
                return newNode;
	}

	public Tree addNode (Tree localTree, int value) {
            Tree newNode = new Tree();
            if (value < localTree.getValue()) {
			if (localTree.getLeftChild() == null) {
				//deepest dimension
				localTree.addLeftChild(value);
				localTree.getLeftChild().setParent(localTree);
                                newNode = localTree.getLeftChild();
			} else {
				newNode = addNode(localTree.getLeftChild(), value);
			}
		} else if (value > localTree.getValue()) {
			if (localTree.getRightChild() == null){
				//deepest dimension
				localTree.addRightChild(value);
				localTree.getRightChild().setParent(localTree);
                                newNode = localTree.getRightChild();
			} else {
				newNode = addNode(localTree.getRightChild(), value);
			}
		} else {
			return null;
		}
		recalcHeight(localTree); // tinh lai chieu cao cua cay
                return newNode;
        }

	public void recalcHeight (Tree localTree){
                // tinh chieu cao cua cay tai vi tri localtree
		int leftChildHeight = getLeftHeight(localTree);
		int righChildtHeight = getRightHeight(localTree);

		localTree.setHeight(Math.max(leftChildHeight, righChildtHeight) + 1);
	}
	
	public int getLeftHeight (Tree localTree){
                if(localTree.getLeftChild() != null){
                    return localTree.getLeftChild().getHeight(); 
                } else {
                    return 0;
                }
	}
	
	public int getRightHeight (Tree localTree){
		if(localTree.getRightChild()!= null){
                    return localTree.getRightChild().getHeight(); 
                } else {
                    return 0;
                }
	}
		
	
	public Tree searchNode(int value) {
		Tree result = searchNode(this.tree, value);
		if (result != null) {
			added = result.getValue();
		} else {
			added = -1000;
		}
		return result;
	}

	public Tree searchNode(Tree localTree, int value) {
		Tree searchResult = new Tree();
		if (localTree == null) {
			return null;
		}
		
		if (localTree.getValue() == value) {
			searchResult = localTree;
		} else {
			if (value < localTree.getValue()) {
                                localTree.setVisited(0);
				searchResult = searchNode(localTree.getLeftChild(), value);
			} else {
                                localTree.setVisited(0);
				searchResult = searchNode(localTree.getRightChild(), value);
			}
		}
		return searchResult;
	}

	public Tree chekDeg (Tree localTree){
		// kiem ra node nao bi lech trong cay
                
		int leftChildHeight = getLeftHeight(localTree);
		int righChildtHeight = getRightHeight(localTree);
		if (Math.abs(leftChildHeight - righChildtHeight) > 1){
                    // vi tri node mat can bang
			return localTree;
                        
		} else if (localTree.getParent() != null) {
                    // tim len node cha
                    return chekDeg(localTree.getParent());
		
                } else {
			return null;
		}
	}


	public String typeOfRotation(Tree degTree) {
		String rotate;
		int leftChildHeight = getLeftHeight(degTree);
		int righChildtHeight = getRightHeight(degTree);

		if (leftChildHeight > righChildtHeight) {
                    //case 1 cay ban dau lech trai sau khi chen cay con trai lech trai
			Tree tmpLeftChild = degTree.getLeftChild();

			leftChildHeight = getLeftHeight(tmpLeftChild);
			righChildtHeight = getRightHeight(tmpLeftChild);
			
			if (leftChildHeight > righChildtHeight) {
			
                                //case cay ban dau lech trai sau khi chen cay con trai lech trai
				rightRotate(degTree);
				rotate = "right rotate";
			} else {
	
                                 //case cay ban dau lech trai sau khi chen cay con trai lech phai
				doubleRightRotate(degTree);
				rotate = "double right rotate";
			}
		} else {
			
			Tree tmpRightChild = degTree.getRightChild();
			leftChildHeight = getLeftHeight(tmpRightChild);
			righChildtHeight = getRightHeight(tmpRightChild);
			if (righChildtHeight > leftChildHeight) {
				 //case cay ban dau lech trai sau khi chen cay con phai lech phai
				leftRotate(degTree);
				rotate = "left rotate";
			} else {
				 //case cay ban dau lech trai sau khi chen cay con phai lech trai
				doubleLeftRotate(degTree);
				rotate = "double left rotate";
			}
		}
		
		return rotate;
                              
	}
	
	private void rightRotate (Tree degTree) {
		// xoay phai 1 lan
                System.out.println("1");
		Tree leftChild = degTree.getLeftChild(),
			 parent = degTree.getParent(),
			 rightGrandson = leftChild.getRightChild();

		degTree.setParent(leftChild);
		degTree.setLeftChild(rightGrandson);
		
		if (rightGrandson != null) {
			rightGrandson.setParent(degTree);
		}
		
		leftChild.setParent(parent);
		leftChild.setRightChild(degTree);

		if (parent != null){
			if (parent.getValue() > leftChild.getValue()) {
				parent.setLeftChild(leftChild);
			} else {
				parent.setRightChild(leftChild);
			}
		}


                    // tinh lai chieu cao cua cay
		Tree tmpTree = degTree; 
		while (tmpTree != null) {
			recalcHeight(tmpTree);
			tmpTree = tmpTree.getParent();
		}
                // xac dinh lai nout goc
		if (parent == null) {
			tree = degTree.getParent();
		}
	}
	
	private void doubleRightRotate (Tree degTree) {
		// xoay phai 2 lan
                System.out.println("2");
		Tree leftChild = degTree.getLeftChild(),
			 parent = degTree.getParent(),
			 rightGrandson = leftChild.getRightChild();
		
		leftChild.setParent(rightGrandson);
		leftChild.setRightChild(rightGrandson.getLeftChild());
		
		if (rightGrandson.getLeftChild() != null) {
			rightGrandson.getLeftChild().setParent(leftChild);
		}
		
		if (rightGrandson.getRightChild() != null) {
			rightGrandson.getRightChild().setParent(degTree);
		}
		
		degTree.setParent(rightGrandson);
		degTree.setLeftChild(rightGrandson.getRightChild());
		
		rightGrandson.setParent(parent);
		rightGrandson.setLeftChild(leftChild);
		rightGrandson.setRightChild(degTree);
		
		if (parent != null){
			if (parent.getValue() > rightGrandson.getValue()) {
				parent.setLeftChild(rightGrandson);
			} else {
				parent.setRightChild(rightGrandson);
			}
		}
		
		recalcHeight(degTree);
		Tree tmpTree = leftChild;
		while (tmpTree != null) {
			recalcHeight(tmpTree);
			tmpTree = tmpTree.getParent();
		}
		
		if (parent == null) {
			tree = rightGrandson;
		}
	}
	
	private void leftRotate (Tree degTree) {
		// xoay trai 1 lan
                System.out.println("3");
		Tree rightChild = degTree.getRightChild(),
			 parent = degTree.getParent(),
			 leftGrandson = rightChild.getLeftChild();

		degTree.setParent(rightChild);
		degTree.setRightChild(rightChild.getLeftChild());
		
		if (leftGrandson != null) {
			leftGrandson.setParent(degTree);
		}

		rightChild.setParent(parent);
		rightChild.setLeftChild(degTree);

		if (parent != null){
			if (parent.getValue() > rightChild.getValue()) {
				parent.setLeftChild(rightChild);
			} else {
				parent.setRightChild(rightChild);
			}
		}


	
		Tree tmpTree = degTree; 
		while (tmpTree != null) {
			recalcHeight(tmpTree);
			tmpTree = tmpTree.getParent();
		}

		if (parent == null) {
			tree = degTree.getParent();
		}
	}
	
	private void doubleLeftRotate (Tree degTree) {
            
           //xoay trai 2 lan
           System.out.println("4");
		Tree rightChild = degTree.getRightChild(),
			 parent = degTree.getParent(),
			 leftGrandson = rightChild.getLeftChild();
		
		rightChild.setParent(leftGrandson);
		rightChild.setLeftChild(leftGrandson.getRightChild());
		
		if (leftGrandson.getRightChild() != null) {
			leftGrandson.getRightChild().setParent(rightChild);
		}
		
		if (leftGrandson.getLeftChild() != null) {
			leftGrandson.getLeftChild().setParent(degTree);
		}
		
		degTree.setParent(leftGrandson);
		degTree.setRightChild(leftGrandson.getLeftChild());
		
		leftGrandson.setParent(parent);
		leftGrandson.setRightChild(rightChild);
		leftGrandson.setLeftChild(degTree);
		
		if (parent != null){
			if (parent.getValue() > leftGrandson.getValue()) {
				parent.setLeftChild(leftGrandson);
			} else {
				parent.setRightChild(leftGrandson);
			}
		}
		
		recalcHeight(rightChild);
		Tree tmpTree = degTree;
		while (tmpTree != null) {
			recalcHeight(tmpTree);
			tmpTree = tmpTree.getParent();
		}
		
		if (parent == null) {
			tree = leftGrandson;
		}
	}
	
	public void clearTree () {
		this.tree = null;
	}
	
	
	public void removeNode (Tree locaTree) {
		Tree parent = locaTree.getParent();
		Tree leftChild = locaTree.getLeftChild();
		Tree rightChild = locaTree.getRightChild();
		
		// tim cay co chieu cao lon hon trong 2 cau con trai va cay con phai
		Tree highestTree = getLeftHeight(locaTree) > getRightHeight(locaTree) ? leftChild : rightChild;
		
		if (highestTree == null) {
			// la nut la
			if (parent.getLeftChild() != null && parent.getLeftChild().getValue() == locaTree.getValue()) {
				parent.setLeftChild(null);
			} else {
				parent.setRightChild(null);
			}
			
			Tree tmpTree = parent;
			while (tmpTree != null) {
				recalcHeight(tmpTree);
				tmpTree = tmpTree.getParent();
			}
			
			Tree degTree = chekDeg(parent);
			if (degTree != null){
                            // xoay cay
				typeOfRotation(degTree);
			}
			
			removed = -1000;
		} else {
                   
			Tree forReplace = highestTree;
			if (highestTree.getValue() > locaTree.getValue()) {
                             // tim nut la nnho nhat o cay con phai
				while (forReplace.getLeftChild() != null) {
					forReplace = forReplace.getLeftChild();
				}
			} else {
                             // tim nut la lon nhat o cay con trai
				while (forReplace.getRightChild() != null) {
					forReplace = forReplace.getRightChild();
				}
			}
			locaTree.setValue(forReplace.getValue());
			removeNode(forReplace);
		}
	}
}
