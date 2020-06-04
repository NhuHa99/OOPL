package RBTree;

import Comom.Param;
import Comom.Tree;



// class RedBlackTree implements the operations in Red Black Tree
public class RedBlackTree {
	private static Tree root = null;
	private static Tree TNULL = null;
        private static int searchKey;
        public static Tree getTree(){
            return root;
        }
        public static void setSearchKey(int key){
            searchKey = key;
        }
        public static int getSearchKey(){
            return searchKey;
        }
        public void clear(){
            root = TNULL;
        }
	private void preOrderHelper(Tree node) {
		if (node != TNULL) {
			preOrderHelper(node.getLeftChild());
			preOrderHelper(node.getRightChild());
		} 
	}

	private void inOrderHelper(Tree node) {
		if (node != TNULL) {
			inOrderHelper(node.getLeftChild());
			inOrderHelper(node.getRightChild());
		} 
	}

	private void postOrderHelper(Tree node) {
		if (node != TNULL) {
			postOrderHelper(node.getLeftChild());
			postOrderHelper(node.getRightChild());
			
		} 
	}

	private Tree searchTreeHelper(Tree node, int key) {
		if (node == TNULL || key == node.getValue()) {
			return node;
		}

		if (key < node.getValue()) {
			return searchTreeHelper(node.getLeftChild(), key);
		} 
		return searchTreeHelper(node.getRightChild(), key);
	}

	// fix the rb tree modified by the delete operation
	private void fixDelete(Tree x) {
		Tree s;
		while (x != root && x.getColor() == Param.BLACK) {
			if (x == x.getParent().getLeftChild()) {
				s = x.getParent().getRightChild();
				if (s.getColor() == 1) {
					// case 3.1
					s.setColor(Param.BLACK);
					x.getParent().setColor(Param.RED);
					leftRotate(x.getParent());
					s = x.getParent().getRightChild();
				}

				if (s.getLeftChild().getColor() == 0 && s.getRightChild().getColor() == 0) {
					// case 3.2
					s.setColor(1);
					x = x.getParent();
				} else {
					if (s.getRightChild().getColor() == 0) {
						// case 3.3
						s.getLeftChild().setColor(0);
						s.setColor(1);
						rightRotate(s);
						s = x.getParent().getRightChild();
					} 

					// case 3.4
					s.setColor(x.getParent().getColor());
					x.getParent().setColor(0);
					s.getRightChild().setColor(0);
					leftRotate(x.getParent());
					x = root;
				}
			} else {
				s = x.getParent().getLeftChild();
				if (s.getColor() == 1) {
					// case 3.1
					s.setColor(0);
					x.getParent().setColor(1);
					rightRotate(x.getParent());
					s = x.getParent().getLeftChild();
				}

				if (s.getRightChild().getColor() == 0) {
					// case 3.2
					s.setColor(1);
					x = x.getParent();
				} else {
					if (s.getLeftChild().getColor() == 0) {
						// case 3.3
						s.getRightChild().setColor(0);
						s.setColor(1);
						leftRotate(s);
						s = x.getParent().getLeftChild();
					} 

					// case 3.4
					s.setColor(x.getParent().getColor());
					x.getParent().setColor(0);
					s.getLeftChild().setColor(0);
					rightRotate(x.getParent());
					x = root;
				}
			} 
		}
		x.setColor(0);
	}


	private void rbTransplant(Tree u, Tree v){
		if (u.getParent() == null) {
			root = v;
		} else if (u == u.getParent().getLeftChild()){
			u.getParent().setLeftChild(v);
		} else {
			u.getParent().setRightChild(v);
		}
		v.setParent(u.getParent());
	}

	private void deleteNodeHelper(Tree node, int key) {
		// find the node containing key
		Tree z = TNULL;
		Tree x, y;
		while (node != TNULL){
			if (node.getValue() == key) {
				z = node;
			}

			if (node.getValue() <= key) {
				node = node.getRightChild();
			} else {
				node = node.getLeftChild();
			}
		}

		if (z == TNULL) {
			return;
		} 

		y = z;
		int yOriginalColor = y.getColor();
		if (z.getLeftChild() == TNULL) {
			x = z.getRightChild();
			rbTransplant(z, z.getRightChild());
		} else if (z.getRightChild() == TNULL) {
			x = z.getLeftChild();
			rbTransplant(z, z.getLeftChild());
		} else {
			y = minimum(z.getRightChild());
			yOriginalColor = y.getColor();
			x = y.getRightChild();
			if (y.getParent() == z) {
				x.setParent(y);
			} else {
				rbTransplant(y, y.getRightChild());
				y.setRightChild(z.getRightChild());
				y.getRightChild().setParent(y);
			}

			rbTransplant(z, y);
			y.setLeftChild(z.getLeftChild());
			y.getLeftChild().setParent(y);
			y.setColor(z.getColor());
		}
		if (yOriginalColor == 0){
			fixDelete(x);
		}
	}
//	
	// fix the red-black tree
	private void fixInsert(Tree k){
		Tree u;
		while (k.getParent().getColor() == 1) {
			if (k.getParent() == k.getParent().getParent().getRightChild()) {
				u = k.getParent().getParent().getLeftChild(); // uncle
				if (u.getColor() == 1) {
					// case 3.1
					u.setColor(0);
					k.getParent().setColor(0);
					k.getParent().getParent().setColor(1);
					k = k.getParent().getParent();
				} else {
					if (k == k.getParent().getLeftChild()) {
						// case 3.2.2
						k = k.getParent();
						rightRotate(k);
					}
					// case 3.2.1
					k.getParent().setColor(0);
					k.getParent().getParent().setColor(1);
					leftRotate(k.getParent().getParent());
				}
			} else {
				u = k.getParent().getParent().getRightChild(); // uncle

				if (u.getColor() == 1) {
					// mirror case 3.1
					u.setColor(0);
					k.getParent().setColor(0);
					k.getParent().getParent().setColor(1);
					k = k.getParent().getParent();	
				} else {
					if (k == k.getParent().getRightChild()) {
						// mirror case 3.2.2
						k = k.getParent();
						leftRotate(k);
					}
					// mirror case 3.2.1
					k.getParent().setColor(0);
					k.getParent().getParent().setColor(1);
					rightRotate(k.getParent().getParent());
				}
			}
			if (k == root) {
				break;
			}
		}
		root.setColor(0);
	}


	public RedBlackTree() {
		TNULL = new Tree();
		TNULL.setColor(0);
		TNULL.setLeftChild(null);
		TNULL.setRightChild(null);
		root = TNULL;
	}

	public void preorder() {
		preOrderHelper(root);
	}
//
//	// In-Order traversal
//	// Left Subtree . Node . Right Subtree
	public void inorder() {
		inOrderHelper(root);
	}
//
//	// Post-Order traversal
//	// Left Subtree . Right Subtree . Node
	public void postorder() {
		postOrderHelper(root);
	}
//
//	// search the tree for the key k
//	// and return the corresponding node
	public Tree searchTree(int k) {
		return searchTreeHelper(root, k);
	}
//
//	// find the node with the minimum key
	public Tree minimum(Tree node) {
		while (node.getLeftChild() != TNULL) {
			node = node.getLeftChild();
		}
		return node;
	}
//
//	// find the node with the maximum key
	public Tree maximum(Tree node) {
		while (node.getRightChild()!= TNULL) {
			node = node.getRightChild();
		}
		return node;
	}
//
//	// find the successor of a given node
	public Tree successor(Tree x) {
		// if the right subtree is not null,
		// the successor is the leftmost node in the
		// right subtree
		if (x.getRightChild() != TNULL) {
			return minimum(x.getRightChild());
		}

		// else it is the lowest ancestor of x whose
		// left child is also an ancestor of x.
		Tree y = x.getParent();
		while (y != TNULL && x == y.getRightChild()) {
			x = y;
			y = y.getParent();
		}
		return y;
	}
//
//	// find the predecessor of a given node
	public Tree predecessor(Tree x) {
		// if the left subtree is not null,
		// the predecessor is the rightmost node in the 
		// left subtree
		if (x.getLeftChild() != TNULL) {
			return maximum(x.getLeftChild());
		}

		Tree y = x.getParent();
		while (y != TNULL && x == y.getLeftChild()) {
			x = y;
			y = y.getParent();
		}

		return y;
	}
//
//	// rotate left at node x
	public void leftRotate(Tree x) {
		Tree y = x.getRightChild();
		x.setRightChild(y.getLeftChild());
		if (y.getLeftChild() != TNULL) {
			y.getLeftChild().setParent(x);
		}
		y.setParent(x.getParent());
		if (x.getParent() == null) {
			root = y;
		} else if (x == x.getParent().getLeftChild()) {
			x.getParent().setLeftChild(y);
		} else {
			x.getParent().setRightChild(y);
		}
		y.setLeftChild(x);
		x.setParent(y);
	}

	// rotate right at node x
	public void rightRotate(Tree x) {
		Tree y = x.getLeftChild();
		x.setLeftChild(y.getRightChild());
		if (y.getRightChild() != TNULL) {
			y.getRightChild().setParent(x);
		}
		y.setParent(x.getParent());
		if (x.getParent() == null) {
			root = y;
		} else if (x == x.getParent().getRightChild()) {
			x.getParent().setRightChild(y);
		} else {
			x.getParent().setLeftChild(y);
		}
		y.setRightChild(x);
		x.setParent(y);
	}
//
//	// insert the key to the tree in its appropriate position
//	// and fix the tree
	public Tree insert(int key) {
		// Ordinary Binary Search Insertion
		Tree node = new Tree();
		node.setParent(null);
		node.setValue(key);
		node.setLeftChild(TNULL);
		node.setRightChild(TNULL);
		node.setColor(1); // new node must be red

		Tree y = null;
		Tree x = root;

		while (x != TNULL) {
			y = x;
			if (node.getValue() < x.getValue()) {
				x = x.getLeftChild();
			} else {
                            if(node.getValue()>x.getValue()){
				x = x.getRightChild();
                            }else{
                                return null;
                            }
			}
		}

		// y is parent of x
		node.setParent(y);
		if (y == null) {
			root = node;
		} else if (node.getValue() < y.getValue()) {
			y.setLeftChild(node);
		} else {
			y.setRightChild(node);
		}

		// if new node is a root node, simply return
		if (node.getParent() == null){
			node.setColor(0);
			return node;
		}

		// if the grandparent is null, simply return
		if (node.getParent().getParent() == null) {
			return node;
		}

		// Fix he tree
                
		fixInsert(node);
                return node;
	}

//
//	// delete the node from the tree
	public void deleteNode(int data) {
		deleteNodeHelper(root, data);
	}
	
}