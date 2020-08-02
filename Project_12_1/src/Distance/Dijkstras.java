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
      File file  = new File("실험.txt");
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
      list = new ArrayList<String>(set);//이름저장하는거
      
      
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
          
          while(num>=1&&num<=40) {//////////////////범위조절
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
        
   public void dijkstras2(int startVertex) {//startVertex를 시작으로 모든 곳까지의 최단거리 구하는 함수
              int nVertices = distance[0].length; //vertex의 개수
        
              double[] shortestDistances = new double[nVertices]; //최단거리 저장하는 일차원 배열(갱신되는거)
        
              boolean[] pass = new boolean[nVertices]; //해당 vertex를 경유하면 true
        
              for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) 
              { 
                  shortestDistances[vertexIndex] = 5000.0; //모든 거리를 무한대값(5000)으로 초기화
                  pass[vertexIndex] = false; 
              } 
              shortestDistances[startVertex] = 0; //자기 자신까지의 거리 =0
        
              int[] parents = new int[nVertices]; //경로 트리를 저장하는 부모배열
        
              parents[startVertex] = NO_PARENT; //시작하는 지점은 부모 안가짐

              que.add(list.get(startVertex)+" -> ");//////////////////////////////////////////////

              
              int nearestVertex = -1;  
              
              //모든 경로에 대한 최단경로 검색
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
        
                  pass[nearestVertex] = true; //선택된 지점 true로 지정
         
                  for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)  
                  {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
                      double edgeDistance = distance[nearestVertex][vertexIndex]; 
                        
                      if (edgeDistance > 0.0 && ((shortestDistance + edgeDistance) <shortestDistances[vertexIndex]))  
                      { 

                         parents[vertexIndex] = nearestVertex;//갱신
                         //parents[nearestVertex]=vertexIndex; 
                    	 shortestDistances[vertexIndex] = shortestDistance+edgeDistance;//거리 갱신
                    	 que.add(list.get(vertexIndex));
//                    	 if(!que.contains(list.get(nearestVertex)+" -> ")) {
//                          
//                    		 que.removeAll(que);
//                    		 que.add(list.get(startVertex)+" -> ");
//                    		 que.add(list.get(nearestVertex)+" -> "); //경유하는 목적지
//                    	 }              
                          
                       } 
              } 
//              printSolution(startVertex, shortestDistances, parents);//최종적으로 출력하는 함수               
          } 
            printSolution(startVertex, shortestDistances, parents);//최종적으로 출력하는 함수               

   }   
   String save = "", present = "";
   
   private void printSolution(int startVertex,double[] distances,int[] parents) 
   { 

       int nVertices = distances.length;
       String temp=null;
       System.out.println("출발지 : "+list.get(startVertex)+", 도착지 : "+list.get(endVertex)); 
       
       for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)  
       {    
          //수정 
          //System.out.println(distances[vertexIndex]);
          if(vertexIndex == endVertex) {
            printPath(vertexIndex, parents);//경로출력

              que.add(list.get(endVertex));/////////////////////////////////////////////////////////////////////////
         
              System.out.println();
              if(distances[vertexIndex] != 5000.0) {

                 System.out.println("총 이동거리(km) : "+distances[vertexIndex]);
                 distanceresult = String.valueOf(distances[vertexIndex]);
                 while(!que.isEmpty()) {
                    present = que.poll();
                    //System.out.println(present);
                    if(!present.equals(list.get(startVertex))) {//시작노드가 아닐때
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
                     //System.out.print(present);//경유지 출력
                     save = present;
                 //    System.out.println(save);
                     
                 }System.out.println();
              }
               else {
                 System.out.println("연결 가능한 경로가 없습니다ㅠ^ㅠ");
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
             if(way.get(i).road.get(j).equals(str[0])) {//i번째 고속도로의 지역에 first를 포함한다면
                   temp1.add(way.get(i).getName());//고속도로 이름 저장
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
//        	 if((way.get(num).road.contains(start)&&way.get(num).road.contains(end))||way.get(num).road.contains(start)&&way.get(num).road.contains(end)) {//num을 경부선 번호, 남해선(영암~순천) 번호로 바꿔주기
//        		 return true;//고속도로를 갈 수 없다면 true 반환
//        	 }
//        	 else if((way.get(num).road.contains(start)&&way.get(num).road.contains(end))||way.get(num).road.contains(start)&&way.get(num).road.contains(end)) {//num을 경부선 번호, 서울~용인선 번호로 바꿔주기
//        		 return true;//고속도로를 갈 수 없다면 true 반환
//        	 }
//        	 else
//        		 return false;
//          }
          
        
          public void menu() {
                 int menu; boolean flag = true;

                 while(flag) {
                    System.out.println("==========메뉴==========");
                    System.out.println("1) 최단경로 찾기  2) 프로그램 종료");
                    System.out.print("메뉴를 선택하세요 : ");
                    Scanner sc = new Scanner(System.in);
                    menu = sc.nextInt();
                    switch(menu) {
                    case 1:
                       boolean check1 = true; boolean check2 = true; //출발지, 도착지 정보 있는지 확인하는 변수
                       Scanner s = new Scanner(System.in);
                       
                       while(check1) {
                          System.out.print(">>> 출발지 선택 : ");
                          start = s.nextLine().trim(); //입력받은 값 저장해서
                          if(list.contains(start)) {
                             startVertex = list.indexOf(start);
                             check1 = false;
                          }
                          else {
                             System.out.println("출발지 정보가 존재하지 않습니다. 다시 입력해주세요!");
                             start=null;
                          }
                       }
                          if(check1==false) {
                             while(check2) {
                                 System.out.print(">>> 도착지를 입력하세요 : ");
                                 end = s.next().trim();
                                 if(list.contains(end)) {
                                    endVertex = list.indexOf(end);
                                    //check2 = checkNot(start,end);
                                    check2=false;
                                 }
                                 else {
                                    System.out.println("도착지 정보가 존재하지 않습니다. 다시 입력해주세요!");
                                    end=null;
                                 }
                              }
                          }
                          
                          if(check2==false) {
                              dijkstras2(startVertex);
                          }
                          else
                          {
                        	  System.out.println(start+"와 "+end+"를 잇는 경로가 존재하지 않습니다^^!");
                          }
                          break;
                    case 2:
                       System.out.println("프로그램을 종료합니다.");
                       flag = false;
                       break;
                    default:
                       break;
                          
                    }
                 }
             }
      
}