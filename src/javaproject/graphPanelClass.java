/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaproject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Map;
import java.util.SortedMap;
import javax.swing.JPanel;
import static javaproject.interGui.*;

/**
 *
 * @author leoeuler
 */

public class graphPanelClass extends JPanel implements Runnable
{
    //overriding run method
    public void run()
    {
        
        int xAnimator,yAnimator;
        int currentIndex=ThreadChooser;

        String curPathString=animatedPathString.get(currentIndex);
        String pointList[]=null;
        
        pointList=curPathString.split(" ");
        
        while(animationRunning)
        {   
            if(!curPathString.equals("-1"))
            {
                
                for(int i = 0; i < pointList.length - 1; i++) 
                {
                    double xSrc = g.pointsMap.get(pointList[i]).get(0);
                    double ySrc = g.pointsMap.get(pointList[i]).get(1);
                    double xDest = g.pointsMap.get(pointList[i+1]).get(0);
                    double yDest = g.pointsMap.get(pointList[i+1]).get(1);
                    
                    if(Math.abs(xSrc-xDest)<10)
                    {
                        System.out.println("xSrc "+xSrc+"xDest "+xDest);
                        System.out.println("ySrc "+ySrc+"yDest "+yDest);
                        int directionY;

                        if (yDest > ySrc) {
                            directionY = 1;
                        } else {
                            directionY = -1;
                        }
                        while (Math.abs(ySrc - yDest) > 3) 
                        {
                            System.out.println("y "+(int)ySrc);
                            ySrc = ySrc + directionY;

                            xAnimator = (int) xSrc;
                            yAnimator = (int) ySrc;
                            
                            if(!animationRunning)
                                break;
                            
                            xList.set(currentIndex,xAnimator);
                            yList.set(currentIndex,yAnimator);
                            
                            this.repaint();
                            try 
                            {
                                Thread.sleep(20);
                            }
                            catch (Exception e) 
                            {
                                System.out.println(e);
                            }
                        }
                    }
                    else if(Math.abs(ySrc-yDest)<10)
                    {
                        int directionX;

                        if (xDest > xSrc) {
                            directionX = 1;
                        } else {
                            directionX = -1;
                        }
                        while (Math.abs(xSrc - xDest) > 3) 
                        {
                            
                            xSrc = xSrc + directionX;

                            xAnimator = (int) xSrc;
                            yAnimator = (int) ySrc;
                            
                            if(!animationRunning)
                                break;
                            
                            xList.set(currentIndex,xAnimator);
                            yList.set(currentIndex,yAnimator);
                            this.repaint();
                            try 
                            {
                                Thread.sleep(20);
                            }
                            catch (Exception e) 
                            {
                                System.out.println(e);
                            }
                        }
                    }
                    else
                    {
                        double m = (ySrc - yDest) / (xSrc - xDest);

                        int directionX;

                        if (xDest > xSrc) {
                            directionX = 1;
                        } else {
                            directionX = -1;
                        }

                        while (Math.abs(xSrc - xDest) > 3 || Math.abs(ySrc - yDest) > 3) 
                        {
                            xSrc = xSrc + directionX;
                            ySrc = ySrc + m * directionX;

                            xAnimator = (int) xSrc;
                            yAnimator = (int) ySrc;
                            
                            if(!animationRunning)
                                break;
                            
                            xList.set(currentIndex,xAnimator);
                            yList.set(currentIndex,yAnimator);
                            
                            this.repaint();
                            try {
                                Thread.sleep(20);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    }
                    
                }
            }
        }
    }
    
    public void paintComponent(Graphics graphicsObj) 
    {
        super.paintComponent(graphicsObj);
        this.setBackground(Color.WHITE);
        Font newFont=new Font("Courier New", 1,22);
        graphicsObj.setFont(newFont);
        
        //draw the special point
        if(animationRunning)
        {
            for(int i=0;i<xList.size();i++)
            {
                int xAnimator=xList.get(i);
                int yAnimator=yList.get(i);
                
                if(xAnimator==0 || yAnimator==0) continue;
                int optionSelected=optionList.get(i);
                if(optionSelected==0)
                {
                     graphicsObj.setColor(Color.GREEN);
                    graphicsObj.fillOval(xAnimator-5, yAnimator-5, 10, 10);
                }
                else if(optionSelected==1)
                {
                     graphicsObj.setColor(Color.BLUE);
                    graphicsObj.fillRect(xAnimator-5, yAnimator-5, 10, 10);
                }
                else if(optionSelected==2)
                {
                     graphicsObj.setColor(Color.RED);
                    int[] xpoints = {xAnimator,xAnimator+10,xAnimator+5};
                    int[] ypoints = {yAnimator,yAnimator,yAnimator+10};
                    
                    graphicsObj.fillPolygon(xpoints, ypoints, 3);
                }
                else if(optionSelected==3)
                {
                     graphicsObj.setColor(Color.CYAN);
                    graphicsObj.drawString("x",xAnimator, yAnimator);
                }
                else if(optionSelected==4)
                {
                     graphicsObj.setColor(Color.MAGENTA);
                     graphicsObj.drawString("+",xAnimator, yAnimator);
                }
                
            }
            
        }
        
        
        
        pathString=pathString.trim();
        String pointList[]=null;
        if(!pathString.equals("-1"))
        {
            pointList=pathString.split(" ");
        }
        
        
        for(Map.Entry entry : g.pointsMap.entrySet())
        {
            String name=(String)entry.getKey();
            int x=g.pointsMap.get(name).get(0);
            int y=g.pointsMap.get(name).get(1);
            
            //default colour of vertex is BLUE 
            graphicsObj.setColor(Color.BLUE);
                        
            if (!pathString.equals("-1")) 
            {
                for (int i = 0; i< pointList.length; i++) 
                {
                    if (name.equals(pointList[i])) 
                    {
                        graphicsObj.setColor(Color.ORANGE);
                        break;
                    }
                }
            }
            if(name.equals(src))
                graphicsObj.setColor(Color.BLACK);
            
            if(name.equals(dest))
                graphicsObj.setColor(Color.GREEN);
                
            
            graphicsObj.fillOval(x-10, y-10, 20, 20);
            graphicsObj.setColor(Color.BLACK);
            graphicsObj.drawString(name, x+7, y+7);

        }
        
        
        for (Map.Entry<String, SortedMap<String, Integer>> outerEntry : g.graph.entrySet()) 
        {

            String from = outerEntry.getKey();
            SortedMap<String, Integer> innerGraph = outerEntry.getValue();
            
            for (Map.Entry<String, Integer> innerEntry : innerGraph.entrySet()) 
            {
                String to = innerEntry.getKey();
                int wt = innerEntry.getValue();
                if(g.pointsMap.get(from)!=null && g.pointsMap.get(to)!=null)
                {
                    int x1=g.pointsMap.get(from).get(0);
                    int y1=g.pointsMap.get(from).get(1);
                    int x2=g.pointsMap.get(to).get(0);
                    int y2=g.pointsMap.get(to).get(1);
                    
                    graphicsObj.setColor(Color.MAGENTA);
                    graphicsObj.drawString(""+wt,(x1+x2)/2 - 7 , (y1+y2)/2 - 7);
                    
                    boolean different=false;
                    if(!pathString.equals("-1"))
                    {
                        for(int i=0 ; i+1<pointList.length ; i++)
                        {
                            if((pointList[i].equals(from) && pointList[i+1].equals(to)) || (pointList[i].equals(to) && pointList[i+1].equals(from)))
                            {
                                different=true;
                                break;
                            }
                        }
                    }
                    
                    if(different)
                    {
                        graphicsObj.setColor(Color.RED);
                    }
                    else
                    {
                        graphicsObj.setColor(Color.BLACK);
                    }  
                    graphicsObj.drawLine(x1, y1, x2, y2);
                }
            }

        }

    }
    
    public void myThreadCreator()
    {
        Thread myThread=new Thread(this);
//      inovke the run function on the reference passed (i.e refernce of graphPanel class) 
        myThread.start();
    }
    
}

