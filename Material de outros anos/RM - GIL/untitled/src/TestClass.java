import sun.reflect.generics.tree.Tree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TreeMap;

public class TestClass {

    public static void main(String[] args) throws IOException {

        ArrayList<Message> messageList = new ArrayList<Message>();
        ArrayList<Report> reportList = new ArrayList<Report>();

        String file1 = "/home/sara/Documentos/RM/the-one/reports/default_scenario_EventLogReport.txt";
        String file2 = "/home/sara/Documentos/RM/the-one/reports/default_scenario_MessageReport.txt";

        BufferedReader br = new BufferedReader(new FileReader(file1));
        BufferedReader br2 = new BufferedReader(new FileReader(file2));

        String aux = "";

        TreeMap tm = new TreeMap<String, Float>();
        TreeMap ta = new TreeMap<String, Float>();

        while ((aux = br2.readLine()) != null) {
            String words[] = aux.split("\\s+");
            if (words[0].equals("#") == false) {
                Report r = new Report();
                r.setName(words[0]);
                r.setStartTime(Float.parseFloat(words[1]));
                reportList.add(r);
            }
        }

        String str = "";
        while ((str = br.readLine()) != null) {
            String[] words = str.split("\\s+");
            if (words[1].equals("DE") == true) {
                Message m = new Message();
                m.setEndTime(Float.parseFloat(words[0]));
                m.setStatus(words[1]);
                m.setSource(words[2]);
                m.setDest(words[3]);
                m.setName(words[4]);

                for (Report r : reportList) {
                    if (m.getName().equals(r.getName())) {
                        m.setStartTime(r.getStartTime());
                    }
                }
                messageList.add(m);
            }
        }

        tm = getMaxDelayByMessage(messageList);
        ta = getAvgDelayByMessage(messageList);

        for (Message m : messageList) System.out.println(m.toString());
        System.out.println("maximum delay: " + getMaxDelay(messageList));
        System.out.println("average delay: " + getAverageDelay(messageList));
        System.out.println("maximum delay by message: " + tm.toString());
        System.out.println("average delay by message: " + ta.toString());

    }


    public static float getMaxDelay(ArrayList<Message> list) {
        float max = 0;
        float tmp;
        for (Message m : list) {
            if ((tmp = m.getDelay()) > max)
                max = tmp;
        }

        return max;
    }

    public static float getAverageDelay(ArrayList<Message> list)
    {
        float total = 0;
        for(Message m : list)
        {
            total+= m.getDelay();
        }

        return total/list.size();
    }

    public static TreeMap<String, Float> getMaxDelayByMessage(ArrayList<Message> list)
    {
        TreeMap<String, Float> tm = new TreeMap<String, Float>();
        for(Message m : list)
        {
            if((tm.keySet().contains(m.getName())) == false)
            {
                tm.put(m.getName(), (float) 0);
            }
        }

        float max = 0;
        float tmp;
        for(String s : tm.keySet())
        {
            for(Message m : list)
            {

                if(m.getName().equals(s) == true)
                {
                    if((tmp = m.getDelay()) > max)
                    {
                        max = tmp;
                    }
                }
            }
           tm.put(s, max);
        }

        return tm;
    }

    public static TreeMap<String, Integer> getMsgQty(ArrayList<Message> list)
    {
        TreeMap<String, Integer> tm = new TreeMap<String, Integer>();
        for(Message m: list)
        {
            if((tm.keySet().contains(m.getName())) == false)
            {
                tm.put(m.getName(),  0);
            }
        }

        int total = 0;
        for(String s : tm.keySet())
        {
            for(Message m : list)
            {
                if(m.getName().equals(s) == true)
                {
                   total++;
                }
            }
            tm.put(s, total);
            total = 0;
        }

        return tm;
    }

    public static TreeMap<String, Float> getAvgDelayByMessage(ArrayList<Message> list)
    {
        TreeMap<String, Float> tm = new TreeMap<String, Float>();
        TreeMap<String, Integer> tq = new TreeMap<String, Integer>();
        tq = getMsgQty(list);

        for(Message m : list)
        {
            if((tm.keySet().contains(m.getName())) == false)
            {
                tm.put(m.getName(), (float) 0);
            }
        }

        float total = 0;
        for(String s : tm.keySet())
        {
            for(Message m : list)
            {

                if(m.getName().equals(s) == true)
                {
                        total+= m.getDelay();
                }
            }

            tm.put(s, total/(tq.get(s)));
            total = 0;
        }

        return tm;
    }
}