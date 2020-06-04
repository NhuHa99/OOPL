package RBTree;

import Comom.Param;
import Comom.Tree;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class RBTreePanel extends JPanel implements ActionListener{
	
	private Graphics2D g2; 
        private int done = 0;
	Timer tm = new Timer(10, this);
	
        public RBTreePanel(){
            //tm.start();
	}
        public void setMove(Tree localTree, float x2, float y2){
           localTree.setMove_x((x2-localTree.getX())/Param.SPEED_ANIMATION);
           localTree.setMove_y((y2-localTree.getY())/Param.SPEED_ANIMATION);
           localTree.setNewX(x2);
           localTree.setNewY(y2);
        }
        
        public void setMoveAllNode(Tree localTree){
            if(localTree == null){
                return ;
            }
            Tree parent = localTree.getParent();
            if(parent == null){
                setMove(localTree, Param.WIDTH/2, Param.TOP_MARGIN);
            }else {
                if (parent.getParent() == null) {	
			if (localTree.getValue() < parent.getValue()){
				setMove(localTree,Param.WIDTH/4, Param.NODE_MARGIN + parent.getNewY());
			} else {
                                setMove(localTree,Param.WIDTH-Param.WIDTH/4, Param.NODE_MARGIN + parent.getNewY());	
			}
                }else{
                   if (localTree.getValue() < parent.getValue()){        
                        setMove(localTree, parent.getNewX() - Math.abs(parent.getNewX() - parent.getParent().getNewX())/2, parent.getNewY() + Param.NODE_MARGIN);   
		    } else {
                        setMove(localTree, parent.getNewX() + Math.abs(parent.getNewX() - parent.getParent().getNewX())/2, parent.getNewY() + Param.NODE_MARGIN);
                    }
                }    
            }
            setMoveAllNode(localTree.getLeftChild());
            setMoveAllNode(localTree.getRightChild());
        }
        
        public void drawTree(Tree localTree) {
            
		if (localTree == null || localTree.getValue() == 0) {
			return;
		}else{
                    localTree.setX(localTree.getX()+localTree.getMove_x());
                    localTree.setY(localTree.getY()+localTree.getMove_y());
                }
		Tree parent = localTree.getParent();
                if (parent != null) {
			g2.setColor(Param.COLOR_LINE);
			g2.drawLine((int)parent.getX()+Param.DIAMETR/2, (int)parent.getY()+Param.DIAMETR-1, (int)localTree.getX() + 17, (int)localTree.getY() + 17);
		}
		
		g2.setColor(Param.COLOR_NODE);
		if (localTree.getColor()== 0) {
			g2.setColor(Color.BLACK);
		} else {
                    g2.setColor(Color.RED);
                    
                }
                if(localTree.getValue() == RedBlackTree.getSearchKey()){
                        g2.setColor(Color.yellow);
                }
	
		g2.fillOval((int)localTree.getX(), (int)localTree.getY(), Param.DIAMETR, Param.DIAMETR);
		
		g2.setColor(Color.WHITE);
		String nodeString = localTree.getValue() + "";
		g2.drawString(nodeString, localTree.getX() + (Param.DIAMETR-6)/2, localTree.getY() + 22);
		
		drawTree(localTree.getLeftChild());
		drawTree(localTree.getRightChild());
	}

        @Override
	protected void paintComponent(Graphics g) {  
		super.paintComponent(g);  
		g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawTree(RedBlackTree.getTree());
	}  

        public void startAction(){
            this.done = 0;
            setMoveAllNode(RedBlackTree.getTree());
            tm.start();
        } 
    @Override
    public void actionPerformed(ActionEvent ae) {
        this.done++;
        repaint();
        if(this.done == (Param.SPEED_ANIMATION+1)){
            tm.stop();
        }
    }
}
