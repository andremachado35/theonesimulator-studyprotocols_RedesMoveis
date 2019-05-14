import com.sun.org.apache.xpath.internal.SourceTree;
import sun.reflect.generics.tree.Tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TestClass{

    public static void main(String[] args) throws IOException {

        ArrayList<Message> lista_de_mensagens = new ArrayList<>();
        ArrayList<Report> lista_de_reports = new ArrayList<>();

        
        String aux,aux2;
        TreeMap tm = new TreeMap<String, Float>();
        TreeMap ta = new TreeMap<String, Float>();
        TreeMap tq = new TreeMap<String, Float>();
        // getsimulationtime Action host1 host2 mensagem extra 
        String EventLogReport = "/home/andre/Documentos/Code/RedesMoveis/RedesMoveis/The one/reports/default_scenario_EventLogReport.txt";
        //tempo ID size hopcount deliverytime from to remainingTTL isResponse
        String CreateadMessages = "/home/andre/Documentos/Code/RedesMoveis/RedesMoveis/The one/reports/default_scenario_CreatedMessagesReport.txt";
        //time  ID  size  hopcount  deliveryTime  fromHost  toHost  remainingTtl  isResponse  path
        String DeliveredMessagesReport = "/home/andre/Documentos/Code/RedesMoveis/RedesMoveis/The one/reports/default_scenario_DeliveredMessagesReport.txt";

        BufferedReader bELR = new BufferedReader(new FileReader(EventLogReport));
        BufferedReader bCM = new BufferedReader(new FileReader(CreateadMessages));
        BufferedReader bDMR = new BufferedReader(new FileReader(DeliveredMessagesReport));
        
        //Parser do delivered messages report
         
        while((aux2=bDMR.readLine())!= null){
            String palavra[] = aux2.split("\\+s");
            if(palavra[0].equals('#')==false){
                System.out.println(Arrays.toString(palavra));
                MessagesReport mr = new MessagesReport();
                mr.SetTime(Float.parseFloat(palavra[0]));
                mr.setId(palavra[1]);
                mr.setHopcount(Integer.parseInt(palavra[3]));
                mr.setDeliveryTime(Float.parseFloat(palavra[4]));
                mr.setFromhost(palavra[5]);
                mr.setTohost(palavra[6]);
                mr.setRemainingTtl(Integer.parseInt(palavra[7]));
                mr.setPath(palavra[8]);
                String path = palavra[8];
                String path_split[] = path.split("->");
                
            }
        }

        String str = "";
        while ((aux = bCM.readLine()) != null) {
            String words[] = aux.split("\\s+");
            if (words[0].equals("#") == false) {
                Report r = new Report();
                r.setStartTime(Float.parseFloat(words[0]));
                r.setName(words[1]);
                lista_de_reports.add(r);
            }
        }

        while ((str = bELR.readLine()) != null) {
            String[] words = str.split("\\s+");
            if (words[1].equals("DE") == true) {
                Message m = new Message();
                m.setEndTime(Float.parseFloat(words[0]));
                m.setStatus(words[1]);
                m.setSource(words[2]);
                m.setDest(words[3]);
                m.setName(words[4]);

                for (Report r : lista_de_reports) {
                    if (m.getName().equals(r.getName())) {
                        m.setStartTime(r.getStartTime());
                    }
                }
                lista_de_mensagens.add(m);
            }
        }

       }
    }
