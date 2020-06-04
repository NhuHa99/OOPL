/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comom;

/**
 *
 * @author ddung
 */
public class Tree {
        private int value; // giá trị của node
	private int height; // chiều cao của cây tại vị trí mỗi node
	private float x;  // tọa độ của x của node vẽ UI
	private float y; // tọa độ y
        private float newX; // toa do moi sau khi xoay cay
        private float newY; // toa do moi sau khi xoay cay
	private Tree parent;    //trỏ tới node cha
	private Tree leftChild; // trỏ tới cây con bên trái
	private Tree rightChild;    // cây bên phải
	private int visited; // xac dinh node da duyet -1 0 1
        private float move_x; // so pixel di chuyen theo truc X
        private float move_y;  // so pixel di chuyen theo truc y
        private int color; // Rb_Tree
        
        
	public Tree() {
		//value = 0;
		height = 1;
		parent = null;
		leftChild = null;
		rightChild = null;
	}
        public void setColor(int color){
            this.color = color;
        }
        public int getColor(){
            return this.color;
        }
        public void setMove_x(float move_x){
            this.move_x = move_x;
        }
        
        public void setMove_y(float move_y){
            this.move_y = move_y;
        }
        
        public float getMove_x(){
            return this.move_x;
        }
        
        public float getMove_y(){
            return this.move_y;
        }
        
        
	public Tree(int value) {
		this();
		this.value = value;
	}
        public Tree(int value, int color) {
		this();
		this.value = value;
                this.color = color;
	}

        public int getVisited(){
            return visited;
        }
        
        public void setVisited(int visited){
            this.visited = visited;
        }
        
	public int getValue() {
		return value;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getX() {
		return x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getY() {
		return y;
	}
        
        public void setNewX(float x) {
		this.newX = x;
	}
	
	public float getNewX() {
		return newX;
	}
	
	public void setNewY(float y) {
		this.newY = y;
	}
	
	public float getNewY() {
		return newY;
	}
        
	public void setValue(int value) {
		this.value = value;
	}
	
	public void changeHeight(int number) {
		this.height += number;
	}
	

	public Tree getParent() {
		return parent;
	}

	public void setParent(Tree parent) {
		this.parent = parent;
	}

	public Tree getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Tree leftChild) {
		this.leftChild = leftChild;
	}

	public void addLeftChild(int value) {
		setLeftChild(new Tree(value));
	}

	public Tree getRightChild() {
		return rightChild;
	}

	public void setRightChild(Tree rightChild) {
		this.rightChild = rightChild;
	}

	public void addRightChild(int value){
		setRightChild(new Tree(value));
	}
}
