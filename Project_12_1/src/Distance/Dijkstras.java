package Distance;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Dijkstras {

   Scanner scan;
   Set<String> set = new HashSet<String>();
   int size;
   Double[][] distance;
   ArrayList<String> list;
   int index1, index2;
   int endVertex, startVertex;
   private static final int NO_PARENT = -1; 
   String start, end;
   ArrayList<Highway> way=new ArrayList<Highway>();
   Queue<String> que = new LinkedList<String>();
   ArrayList<String> result = new ArrayList<>(); 
   String distanceresult;
   boolean check_distance = true;
    
   public void init() {
      File file  = new File("����.txt");
      try {
         scan = new Scanner(file);
      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      while(scan.hasNextLine()) {
         String line = scan.nextLine();
         String[] temp = line.split("\t");
         set.add(temp[0]);
         set.add(temp[1]);         
      }
      
      size = set.size();
      
      distance = new Double[size][size];
      list = new ArrayList<String>(set);//�̸������ϴ°�
      
      
      scan.close();
      try {
             scan=new Scanner(file);
             while(scan.hasNextLine()) {
                String str=scan.nextLine();
                String[] temp=str.split("\t");
                
                   
                   index1=list.indexOf(temp[0]);
                   index2=list.indexOf(temp[1]);
                   distance[index1][index2]=Double.parseDouble(temp[2]);
                   distance[index2][index1]=Double.parseDouble(temp[2]);
                   
             }
          } catch (FileNotFoundException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
          }scan.close();
     
          for(int i=0; i<size; i++) {
              for(int j=0; j<size; j++) {
                 if(distance[i][j]==null) {
                    if(i==j) {
                       distance[i][i] = 0.0;
                    }
                    else
                       distance[i][j]=5000.0;
                 }
              }
           }
          int num=1;
          
          while(num>=1&&num<=40) {//////////////////��������
              int j=1;
              String highname="";
              Highway high=new Highway();
              File file1=new File("highway\\"+num+".txt");
              try {
              scan=new Scanner(file1);
              while(scan.hasNextLine()) {
                 String str=scan.nextLine();
                 if(j==1) {
                    high.setName(str);
                    highname = str;
                    way.add(new Highway(str));
                    j++;
                    continue;
                 }
                 high.road.add(str);
                 int ind=0;
                 for(int k=0; k<way.size(); k++) {
                    if(way.get(k).getName().equals(highname)) {
                       ind=k;
                       break;
                    }
                 }
                 way.get(ind).road.add(str);
                 j++;
                 
              }
           } catch (FileNotFoundException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
           }scan.close();
           num++;

          }
          
//          for(int i=0; i<way.size(); i++) {
//        	  System.out.print(way.get(i).getName()+" : ");
//        	  for(int j=0; j<way.get(i).road.size(); j++) {
//        		  System.out.print(way.get(i).road.get(j));
//        	  }
//        	  System.out.println();
//          }
          

   }
        
   public void dijkstras2(int startVertex) {//startVertex�� �������� ��� �������� �ִܰŸ� ���ϴ� �Լ�
              int nVertices = distance[0].length; //vertex�� ����
        
              double[] shortestDistances = new double[nVertices]; //�ִܰŸ� �����ϴ� ������ �迭(���ŵǴ°�)
        
              boolean[] pass = new boolean[nVertices]; //�ش� vertex�� �����ϸ� true
        
              for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) 
              { 
                  shortestDistances[vertexIndex] = 5000.0; //��� �Ÿ��� ���Ѵ밪(5000)���� �ʱ�ȭ
                  pass[vertexIndex] = false; 
              } 
              shortestDistances[startVertex] = 0; //�ڱ� �ڽű����� �Ÿ� =0
        
              int[] parents = new int[nVertices]; //��� Ʈ���� �����ϴ� �θ�迭
        
              parents[startVertex] = NO_PARENT; //�����ϴ� ������ �θ� �Ȱ���

              que.add(list.get(startVertex)+" -> ");//////////////////////////////////////////////

              
              int nearestVertex = -1;  
              
              //��� ��ο� ���� �ִܰ�� �˻�
              for (int i = 1; i < nVertices; i++) 
              { 

                  double shortestDistance = 5000.0; 
                  for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) 
                  { 
                      if (!pass[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance)  
                      { 
                          nearestVertex = vertexIndex; 
                          shortestDistance = shortestDistances[vertexIndex]; 
                      } 
                  } 
        
                  pass[nearestVertex] = true; //���õ� ���� true�� ����
         
                  for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)  
                  {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
                      double edgeDistance = distance[nearestVertex][vertexIndex]; 
                        
                      if (edgeDistance > 0.0 && ((shortestDistance + edgeDistance) <shortestDistances[vertexIndex]))  
                      { 

                         parents[vertexIndex] = nearestVertex;//����
                         //parents[nearestVertex]=vertexIndex; 
                    	 shortestDistances[vertexIndex] = shortestDistance+edgeDistance;//�Ÿ� ����
                    	 que.add(list.get(vertexIndex));
//                    	 if(!que.contains(list.get(nearestVertex)+" -> ")) {
//                          
//                    		 que.removeAll(que);
//                    		 que.add(list.get(startVertex)+" -> ");
//                    		 que.add(list.get(nearestVertex)+" -> "); //�����ϴ� ������
//                    	 }              
                          
                       } 
              } 
//              printSolution(startVertex, shortestDistances, parents);//���������� ����ϴ� �Լ�               
          } 
            printSolution(startVertex, shortestDistances, parents);//���������� ����ϴ� �Լ�               

   }   
   String save = "", present = "";
   
   private void printSolution(int startVertex,double[] distances,int[] parents) 
   { 

       int nVertices = distances.length;
       String temp=null;
       System.out.println("����� : "+list.get(startVertex)+", ������ : "+list.get(endVertex)); 
       
       for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)  
       {    
          //���� 
          //System.out.println(distances[vertexIndex]);
          if(vertexIndex == endVertex) {
            printPath(vertexIndex, parents);//������

              que.add(list.get(endVertex));/////////////////////////////////////////////////////////////////////////
         
              System.out.println();
              if(distances[vertexIndex] != 5000.0) {

                 System.out.println("�� �̵��Ÿ�(km) : "+distances[vertexIndex]);
                 distanceresult = String.valueOf(distances[vertexIndex]);
                 while(!que.isEmpty()) {
                    present = que.poll();
                    //System.out.println(present);
                    if(!present.equals(list.get(startVertex))) {//���۳�尡 �ƴҶ�
                    	if(present.equals(list.get(endVertex)+" -> ")){
                    		result.add(list.get(endVertex));
                       	 	que.remove();
                       	 	return;
                        }
                       if(temp != getHighway(present, save)) {//////////////////////////////////////////////////
                          temp=getHighway(present,save);
                          if(!temp.equals("NoRoad")) {
                           System.out.print("(");
                           System.out.print(temp);
                           System.out.print(")");
                           result.add("("+temp+")");
                       }
                    //   System.out.println(temp);
                       
                    }
                       }
                    result.add(present);
                     //System.out.print(present);//������ ���
                     save = present;
                 //    System.out.println(save);
                     
                 }System.out.println();
              }
               else {
                 System.out.println("���� ������ ��ΰ� �����ϴ٤�^��");
                 boolean check_distance = false;
                 while(!que.isEmpty()) {
                    que.remove();
                 }
               }
                 return;
              }
          }
         
        
       }
           
        
     private void printPath(int currentVertex, int[] parents) 
          { 
             
              if (currentVertex == NO_PARENT) 
              { 
                  return; 
              }
              
              printPath(parents[currentVertex], parents); 
              if(currentVertex == endVertex) {
                 System.out.print(list.get(currentVertex));
                 result.add(list.get(currentVertex));
              }
              else {
                 System.out.print(list.get(currentVertex) + " -> ");
                 result.add(list.get(currentVertex));
              }
                  
          } 
   public String getHighway(String first, String second) {
       ArrayList<String> temp1 = new ArrayList<String>();
       ArrayList<String> temp2 = new ArrayList<String>();
       String[] str=first.split(" ");         
       String[] str1=second.split(" ");
       
       for(int i=0; i<way.size(); i++) {
          for(int j=0;j<way.get(i).road.size();j++) {
             if(way.get(i).road.get(j).equals(str[0])) {//i��° ��ӵ����� ������ first�� �����Ѵٸ�
                   temp1.add(way.get(i).getName());//��ӵ��� �̸� ����
             }
             if(way.get(i).road.get(j).equals(str1[0])){
                   temp2.add(way.get(i).getName());
             }
          }
       }
       for(int k=0; k<temp1.size(); k++) {
          for(int j=0; j<temp2.size(); j++) {
             if(temp1.get(k).equals(temp2.get(j))){
                return temp1.get(k);
             }
          }
       }
       return "NoRoad";
       
       
       
    }
          
//          public boolean checkNot(String start,String end) {
//        	        	  
//        	 if((way.get(num).road.contains(start)&&way.get(num).road.contains(end))||way.get(num).road.contains(start)&&way.get(num).road.contains(end)) {//num�� ��μ� ��ȣ, ���ؼ�(����~��õ) ��ȣ�� �ٲ��ֱ�
//        		 return true;//��ӵ��θ� �� �� ���ٸ� true ��ȯ
//        	 }
//        	 else if((way.get(num).road.contains(start)&&way.get(num).road.contains(end))||way.get(num).road.contains(start)&&way.get(num).road.contains(end)) {//num�� ��μ� ��ȣ, ����~���μ� ��ȣ�� �ٲ��ֱ�
//        		 return true;//��ӵ��θ� �� �� ���ٸ� true ��ȯ
//        	 }
//        	 else
//        		 return false;
//          }
          
        
          public void menu() {
                 int menu; boolean flag = true;

                 while(flag) {
                    System.out.println("==========�޴�==========");
                    System.out.println("1) �ִܰ�� ã��  2) ���α׷� ����");
                    System.out.print("�޴��� �����ϼ��� : ");
                    Scanner sc = new Scanner(System.in);
                    menu = sc.nextInt();
                    switch(menu) {
                    case 1:
                       boolean check1 = true; boolean check2 = true; //�����, ������ ���� �ִ��� Ȯ���ϴ� ����
                       Scanner s = new Scanner(System.in);
                       
                       while(check1) {
                          System.out.print(">>> ����� ���� : ");
                          start = s.nextLine().trim(); //�Է¹��� �� �����ؼ�
                          if(list.contains(start)) {
                             startVertex = list.indexOf(start);
                             check1 = false;
                          }
                          else {
                             System.out.println("����� ������ �������� �ʽ��ϴ�. �ٽ� �Է����ּ���!");
                             start=null;
                          }
                       }
                          if(check1==false) {
                             while(check2) {
                                 System.out.print(">>> �������� �Է��ϼ��� : ");
                                 end = s.next().trim();
                                 if(list.contains(end)) {
                                    endVertex = list.indexOf(end);
                                    //check2 = checkNot(start,end);
                                    check2=false;
                                 }
                                 else {
                                    System.out.println("������ ������ �������� �ʽ��ϴ�. �ٽ� �Է����ּ���!");
                                    end=null;
                                 }
                              }
                          }
                          
                          if(check2==false) {
                              dijkstras2(startVertex);
                          }
                          else
                          {
                        	  System.out.println(start+"�� "+end+"�� �մ� ��ΰ� �������� �ʽ��ϴ�^^!");
                          }
                          break;
                    case 2:
                       System.out.println("���α׷��� �����մϴ�.");
                       flag = false;
                       break;
                    default:
                       break;
                          
                    }
                 }
             }
      
}