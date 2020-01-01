import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

import javax.swing.*;

public class UImain extends JPanel {

	static JFrame frame;
	static JButton addBot;
	static JButton addPerson;
	static JButton addBlock; static JButton jInstruct;
	static int buttonClick = 0; // 0 is nothing, 1 is bot, 2 is person, 3 is block one point, 4 is the second
								// point
	static Bot[] allBots;
	static Person[] allPerson; static JButton reset;
	static Set<Block> setBlock; static Block nextBlock; static Queue<Person> qPer = new LinkedList<>();

	public UImain() {
		// TODO Auto-generated constructor stub
		super();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		UImain a = new UImain();
		frame = new JFrame("VAWM SIM V.1.0.01");

		allBots = new Bot[0];
		allPerson = new Person[0];
		setBlock = new HashSet<>();
		qPer = new LinkedList<>();
		reset = new JButton("Reset");

		frame.setVisible(true); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1500, 750);
		frame.add(a);
		a.setLayout(null);

		addBot = new JButton("Add a new VAWM");
		addPerson = new JButton("Add a new worker");
		addBlock = new JButton("Add a new Block"); jInstruct = new JButton("Instructions");

		a.add(addBot); a.add(addBlock); a.add(addPerson); a.add(jInstruct); a.add(reset);
		addBot.setBounds(0,0, 200, 100); addBlock.setBounds(200,0,200,100); addPerson.setBounds(400,0,200,100); jInstruct.setBounds(600,0,200,100);
		reset.setBounds(800,0,200,100);
		
		a.repaint(); JOptionPane.showMessageDialog(null, "Welcome to VAWM SIM!");

		addBot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				buttonClick = 1;
			}

		});

		addBlock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				buttonClick = 3;

			}

		});

		jInstruct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "Press the VAWM Button to add a new one. Click a spot to add it there");
				JOptionPane.showMessageDialog(null, "Press the Obstacle Button to add a new one. First click is one point of the rectangle, the other is the opposite end");
				JOptionPane.showMessageDialog(null, "Add a new worker, and press them to find the shortest path, and start the simulation");
			}
			
		});
		
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				allBots = new Bot[0];

				for(int i = 0; i <= allPerson.length-1; i++) {
					a.remove(allPerson[i]);
				}
				
				setBlock = new HashSet<>();
				qPer = new LinkedList<>();
				frame.repaint();
			}
			
		});
		
		addPerson.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				buttonClick = 2;
			}

		});

		a.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

				if (buttonClick == 1) {
					// int max = Integer.parseInt(JOptionPane.showInputDialog("Enter the max load of
					// bot"));
					boolean contains = false;
					for(int i = 0; i <= allBots.length-1; i++) {
						if(allBots[i].x == Math.round(arg0.getX()/50)*50 && allBots[i].y == Math.round(arg0.getY()/50)*50) {
							contains = true;
							break;
						}
					}
					
					if(!contains) {
					int max = 0;
					allBots = Arrays.copyOf(allBots, allBots.length + 1);
					allBots[allBots.length - 1] = new Bot(Math.round(arg0.getX()/50)*50, Math.round(arg0.getY()/50)*50, max);
					System.out.println(allBots.length);
					buttonClick = 0;
					a.repaint();
					a.revalidate();
					} else {
						JOptionPane.showMessageDialog(null, "A Bot is already there!");
					}
				}

				if (buttonClick == 2) {

					allPerson = Arrays.copyOf(allPerson, allPerson.length + 1);

					Person next = new Person(JOptionPane.showInputDialog("Enter the name/site : "));
					next.setVisible(true);
					a.add(next);
					next.setBounds(Math.round(arg0.getX()/50)*50, Math.round(arg0.getY()/50)*50, 100, 50);

					next.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							
							next.setEnabled(false);
							
							boolean freeBot = false;
							 for(int y = 0; y <= allBots.length-1; y++) {
								 if(allBots[y].avail) {
									 freeBot = true;
									 break;
								 }
							 }
							if(!freeBot) {
								qPer.add(next);
								return;
							}
							 Bot botClose = findPath.findBot(next.getX(), next.getY());
							 
							 for(int x = 0; x <= allBots.length-1; x++) {
								 if(botClose.x == allBots[x].x && botClose.y == allBots[x].y) {
									 
									 next.index = x;
									 allBots[x].avail = false;
									 allBots[x].color = new Color(0,0,255);
									 
								 }
							 }
							 
							 Stack<Point> path = findPath.findBFS(new Point(next.getX(), next.getY()), botClose, new Stack<>(), true);
							 next.nextPoint = path.pop();
							 Point currPos = new Point(botClose.x, botClose.y); Point finalPos = new Point(50+ next.index,375);
							 
							 System.out.println(path); next.okay = 0;
							 next.timer = new Timer(3, null);
							 next.timer.addActionListener(new ActionListener() {
							        public void actionPerformed(ActionEvent e) {
							           if(next.okay != 0) {
							        	next.okay += 1;
							        	if(next.okay == 3) {
							        	
							        		next.okay = 0;
							        		next.timer.setDelay(3);
							        		
							        	}
							           }
							        	if(!currPos.equals(next.nextPoint)) {
							        		
							        		if(currPos.x != next.nextPoint.x) {
							        		if(currPos.x < next.nextPoint.x) {
							        			
							        			currPos.x+=1;
							        			allBots[next.index].x += 1;
							        			
							        		} else if(currPos.x > next.nextPoint.x){
							        			
							        			currPos.x-=1;
							        			allBots[next.index].x -= 1;
							        		}
							        		} else if(currPos.y != next.nextPoint.y) {
							        			if(currPos.y < next.nextPoint.y) {
							        				currPos.y+=1;
								        			allBots[next.index].y += 1;
								        		} 
							        			
							        			else if(currPos.y > next.nextPoint.y){
								        			currPos.y-=1;
								        			allBots[next.index].y -= 1;
								        		}	
							        		}
							        		frame.repaint();
							        		
							        	} else if(currPos.equals(next.nextPoint) && !currPos.equals(finalPos) && !path.isEmpty()) {
							        		if(currPos.equals(new Point(next.getX(), next.getY()))) {
							        		
							        			next.timer.setDelay(1000);
							        			next.okay = 1;
							        			
							        		}
							        		next.nextPoint = path.pop();
							        		this.actionPerformed(e);
							        	}
							        	
							        	else {
							        		next.setEnabled(true);
							        		next.timer.stop();

							        		allBots[next.index].avail = true;
							        		allBots[next.index].color = new Color(0,255,0);
							        		
							        		frame.repaint();
							        		
							        		if(!qPer.isEmpty()) {
							        			Person nextPer = qPer.poll();
							        			nextPer.doClick();
												 // finalPos = new Point(next.getX(), next.getY());
							        		}
							        	}
							        	
							        }
							    });
							 
							 	next.timer.start();
						}

					});

					allPerson[allPerson.length - 1] = next;

				}

				if (buttonClick == 3) {
					nextBlock = new Block((int)Math.round(arg0.getX()/50)*50, (int)Math.round(arg0.getY()/50)*50);
					buttonClick = 4;

				}

				else if (buttonClick == 4) {
					nextBlock.setBlock((int)Math.round(arg0.getX()/50)*50,(int)Math.round(arg0.getY()/50)*50);
					
					if(nextBlock.x1 != nextBlock.x2 && nextBlock.y1 != nextBlock.y2) {
						int x1,x2,y1,y2;
						x1 = nextBlock.x1; x2 = nextBlock.x2; y1 = nextBlock.y1; y2 = nextBlock.y2;
						Block a = new Block(nextBlock.x2, nextBlock.y1);
						a.setBlock(x1, y1);
						Block b = new Block(nextBlock.x2, nextBlock.y1);
						b.setBlock(x2, y2);
						Block c = new Block(nextBlock.x1, nextBlock.y2);
						c.setBlock(x1, y1);
						Block d = new Block(nextBlock.x1, nextBlock.y2);
						d.setBlock(x2, y2);
						setBlock.add(a); setBlock.add(c);
						setBlock.add(b); setBlock.add(d);
					} else {
					
					setBlock.add(nextBlock);
					
					}
					
					a.repaint();
					buttonClick = 0;
					
				}

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g.fillArc(-50, 100, 100, 500, -90, 180);

		g2.setColor(new Color(0,255,0));
		for (int i = 0; i <= allBots.length - 1; i++) {
			g2.setColor(allBots[i].color);
			g2.fillOval(allBots[i].x - 25, allBots[i].y - 25, 50, 50);
		}

		Iterator<Block> x = setBlock.iterator();
		g2.setColor(new Color(255,0,0));
		while(x.hasNext()) {
			
			Block a = x.next();
			g2.drawLine(a.x1, a.y1, a.x2, a.y2);

		}
		
	}

}
