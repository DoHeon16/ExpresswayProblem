package Distance;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class MyFrame extends JFrame implements ActionListener {
   
   int width, height;
   JPanel panel1;
   JLabel slabel, elabel, plabel, distance, distanceresult;
   JTextField stf, etf;
   JButton search;
   JTextArea area;
   Dijkstras dj;
   Boolean check;

   
   MyFrame(Dijkstras test) {
      super("고속도로 최단경로 구하기");
      this.dj = test;
      dj.init();
      check = dj.check_distance;
      Toolkit tool = Toolkit.getDefaultToolkit();
      Dimension screen = tool.getScreenSize();
      width = screen.width;
      height = screen.height;
      this.setSize(width/3, (height*4)/5);
      this.setLocation(width/3, height/10);
      this.setResizable(false);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      init();
      this.setVisible(true);
   }
   
   public void init() {
      panel1 = new JPanel(null);
      slabel = new JLabel("출발지", SwingConstants.CENTER);
      slabel.setBounds(100, 20, 100, 20);
      panel1.add(slabel);
      elabel = new JLabel("도착지", SwingConstants.CENTER);
      elabel.setBounds(300, 20, 100, 20);
      panel1.add(elabel);
      plabel = new JLabel(" >>> ", SwingConstants.CENTER);
      plabel.setBounds(235, 33, 30, 30);
      panel1.add(plabel);
      stf = new JTextField(10); 
      stf.setBounds(100, 40, 100, 20);
      panel1.add(stf);
      etf = new JTextField(10);
      etf.setBounds(300, 40, 100, 20);
      panel1.add(etf);
      area = new JTextArea();
      area.setBounds(0, 130, 480, 450);
      area.setLineWrap(true);
      //area.setWrapStyleWord(true);
      JScrollPane scroll = new JScrollPane(area, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      scroll.setBounds(477, 130, 20, 450);
      panel1.add(area);
      panel1.add(scroll);
      search = new JButton("검색");
      search.setBounds(200, 80, 100, 30);
      search.addActionListener(this);
      panel1.add(search);
      distance = new JLabel("총 이동거리(km)  :", SwingConstants.RIGHT);
      distance.setBounds(150, 590, 100, 20);
      panel1.add(distance);
      distanceresult = new JLabel(" ", SwingConstants.LEFT);
      distanceresult.setBounds(270, 590, 230, 20);
      panel1.add(distanceresult);
      
      this.add(panel1);
      
      
   }
   
   public void search() {
      boolean check1 = false; boolean check2 = false;
      String a = stf.getText().trim();
      String b = etf.getText().trim();
      for(int i=0; i<dj.list.size(); i++) {
         if(dj.list.get(i).equals(a)) {
            check1 = true;
         }
      }
      for(int i=0; i<dj.list.size(); i++) {
         if(dj.list.get(i).equals(b)) {
            check2 = true;
         }
      }
      if(check1) {
         if(check2) {
            dj.startVertex = dj.list.indexOf(a);
            dj.endVertex = dj.list.indexOf(b);
            dj.dijkstras2(dj.list.indexOf(a));
            area.append("  =================================================================  \n");
            area.append("                                                    출발지 : "+a +"          도착지 : "+b+"\n");
            area.append("   =================================================================  \n");
            for(int i=0; i<dj.result.size(); i++) {
               area.append(" " + dj.result.get(i));
            }
            if(dj.check_distance) {
               distanceresult.setText(dj.distanceresult);
            }
            else if(!dj.check_distance) {
               area.setText(" ");
               area.append("  =================================================================  \n");
               area.append("                                             출발지 : "+a +"          도착지 : "+b+"\n");
               area.append("   =================================================================  \n");
               area.append("                                                            연결된 경로가 없습니다.");
            }
            
            stf.setText(" ");
            etf.setText(" ");
         }
         else if(!check2) {
            JOptionPane.showMessageDialog(null, "입력하신 도착지가 존재하지 않습니다. ", "", JOptionPane.ERROR_MESSAGE);
         }
      }
      else if(!check1) {
         JOptionPane.showMessageDialog(null, "입력하신 출발지가 존재하지 않습니다. ", "", JOptionPane.ERROR_MESSAGE);
      }
   }
   

   @Override
   public void actionPerformed(ActionEvent arg0) {
      // TODO Auto-generated method stub
      if(arg0.getSource() == search) {
         area.setText(" ");
         distanceresult.setText(" ");
         dj.result.clear();
         search();
      }
   }

}