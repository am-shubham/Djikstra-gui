/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaproject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author leoeuler
 */
public class Graph 
{
    //stores the actual graph Mapping "From" key in first map
    //to "to" key in second map and the value in second map("Integer")
    //is the weight associated with edge
    public SortedMap<String, SortedMap<String,Integer> > graph;
    //stores the points map i.e Name to the x and y pair 
    public SortedMap<String, List<Integer>> pointsMap;
    
    public Map<String,Integer> cost;
    public Map<String,Boolean> finalised;
    public Map<String,String> parent;
    
    public int vertexCount;
    public int edgeCount;
    public Graph() 
    {
        graph=new TreeMap<>();
        pointsMap=new TreeMap<>();
        vertexCount=0;
        edgeCount=0;
    }
    
    public void addVertex(String name,int x,int y)
    {
        if(pointsMap.containsKey(name))
        {
            System.out.println("Vertex with same name already exists !!");
        }
        else
        {
            vertexCount++;
            pointsMap.put(name,Arrays.asList(x,y));
        }
    }     
    public void addEdge(String from,String to,int w)
    {
        edgeCount++;
        graph.putIfAbsent(from, new TreeMap<>());
        graph.get(from).put(to,w);
    }
    
    public void printPointsMap()
    {
        for(Map.Entry entry : pointsMap.entrySet())
        {
            System.out.println("Name " + entry.getKey() + " X " + entry.getValue());
        }
    }
    
    public void printEdgeMap() 
    {
        for (Map.Entry<String, SortedMap<String, Integer>> outerEntry : graph.entrySet()) 
        {

            String from = outerEntry.getKey();
            SortedMap<String, Integer> innerGraph = outerEntry.getValue();
            System.out.println(from + "=>=>=>");
            // System.out.println(innerGraph);
            for (Map.Entry<String, Integer> innerEntry : innerGraph.entrySet()) 
            {
                String to = innerEntry.getKey();
                int wt = innerEntry.getValue();
                System.out.println(to + " => " + "wt " + wt);
            }

        }
    }
    
    //returns the path string 
    String djik(String source, String destination) 
    {
        cost = new HashMap<>();
        finalised = new HashMap<>();
        parent = new HashMap<>();
        
        int n = pointsMap.size();
        
        for (Map.Entry<String, List<Integer>> entry : pointsMap.entrySet()) 
        {
            String ver = entry.getKey();
            cost.put(ver, 1000000);
            finalised.put(ver, false);
            parent.put(ver,"noParent");
        }
        
        cost.put(source, 0);          //set distance of source=0      
     
        //djik needs to be run n-1 times
        for (int i = 0; i < n - 1; i++) 
        {

            String minNode = "noNode";
            int minCost = Integer.MAX_VALUE;

            //iterate through the distances map
            for (Map.Entry<String, Integer> entry : cost.entrySet()) 
            {
                String node = entry.getKey();
                int nodeCost = entry.getValue();

                if (!finalised.get(node) && nodeCost < minCost) 
                {
                    minNode = node;
                    minCost = nodeCost;
                }
            }

            //continue after the node with least value is selected
            finalised.put(minNode, true);
            
            if(graph.get(minNode)!=null)
            {
                for (Map.Entry<String, Integer> entry : graph.get(minNode).entrySet()) 
                {
                    String to = entry.getKey();
                    int wt = entry.getValue();

                    if (!finalised.get(to)) 
                    {

                        //current cost of reaching "to"
                        int curCost = minCost + wt;
                        //earlier cost of reaching "to"
                        int prevCost = cost.get(to);

                        if (curCost < prevCost) 
                        {
                            parent.put(to, minNode);
                            cost.put(to, curCost);
                        }
                    }
                } 
            }
            
        }
        
        String pathString="";
       //print path from destination to source
        if(destination.equals(source))
        {
            pathString="Source already on destination";
        }
        else if(parent.get(destination).equals("noParent"))
        {
            pathString="No path exists between given vertices !!";
        }
        else 
        {
            
            System.out.println("Printing path");
            pathString=this.printPath(source, destination,pathString);
        }
        return pathString;
    }

    
   String printPath(String source,String str,String pathString)
   { 
      if(str.compareTo(source)==0) 
         return source;
      pathString=this.printPath(source,parent.get(str),pathString);
       System.out.println("Str " + str);
      return pathString=pathString+" "+str;
   }
}
