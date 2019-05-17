/*
 * Copyright 2010 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details.
 */
package report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import core.Application;
import core.ApplicationListener;
import core.ConnectionListener;
import core.Coord;
import core.DTNHost;
import core.Message;
import core.MessageListener;
import core.MovementListener;
import core.UpdateListener;

/**
 * Report for generating different kind of total statistics about message
 * relaying performance. Messages that were created during the warm up period
 * are ignored.
 * <P><strong>Note:</strong> if some statistics could not be created (e.g.
 * overhead ratio if no messages were delivered) "NaN" is reported for
 * double values and zero for integer median(s).
 */
public class CustomReport extends Report implements MessageListener, MovementListener, UpdateListener, ConnectionListener, ApplicationListener {
    private Map<String, List<DataHolder>> data;
	

	/**
	 * Constructor.
	 */
	public CustomReport() {
		init();
	}

	@Override
	protected void init() {
		super.init();
		this.data = new HashMap<String, List<DataHolder>>();
	
	}


	public void messageDeleted(Message m, DTNHost where, boolean dropped) {
	
	}


	public void messageTransferAborted(Message m, DTNHost from, DTNHost to) {
	
	}


	public void messageTransferred(Message m, DTNHost from, DTNHost to,
			boolean finalTarget) {
                if(data.containsKey(m.getId())){
                    data.get(m.getId()).add(new DataHolder(m, from, to));
                    
                }else{
                    List<DataHolder> list= new ArrayList<>();
                    list.add(new DataHolder(m,from,to));
                    data.put(m.getId(), list);
                }
                
	}


	public void newMessage(Message m) {

	}


	public void messageTransferStarted(Message m, DTNHost from, DTNHost to) {

	}


	@Override
	public void done() {

        Set<String> keys = data.keySet();
        for(String key: keys){
            List<DataHolder> dat = data.get(key);
            
            System.out.println(Arrays.toString(dat.toArray()));
            write("123");
            

        }

		super.done();
	}

	@Override
	public void gotEvent(String event, Object params, Application app, DTNHost host) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hostsConnected(DTNHost host1, DTNHost host2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hostsDisconnected(DTNHost host1, DTNHost host2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updated(List<DTNHost> hosts) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newDestination(DTNHost host, Coord destination, double speed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialLocation(DTNHost host, Coord location) {
		// TODO Auto-generated method stub
		
	}
    public class DataHolder{
        Message m;
        DTNHost from;
        DTNHost to;
        public DataHolder(Message m, DTNHost from, DTNHost to){
            this.m = m;
            this.from=from;
            this.to=to;
        }
    
    }
    
}
