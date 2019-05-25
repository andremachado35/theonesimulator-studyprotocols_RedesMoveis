package report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.Application;
import core.ApplicationListener;
import core.ConnectionListener;
import core.Coord;
import core.DTNHost;
import core.Message;
import core.MessageListener;
import core.MovementListener;
import core.UpdateListener;

public class CustomReport extends Report
		implements MessageListener, MovementListener, UpdateListener, ConnectionListener, ApplicationListener {
	private Map<String, List<DataHolder>> data;
	private String lastKey = "";

	//Statistics
	private List<Double> averages;
	private List<Double> medians;
	private List<Double> maxdelay;
	private List<Double> mindelay;
	private List<Double> deliveryPercs;
	
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
		this.averages = new ArrayList<Double>();
		this.medians = new ArrayList<Double>();
		this.maxdelay = new ArrayList<Double>();
		this.mindelay = new ArrayList<Double>();
		this.deliveryPercs = new ArrayList<Double>();
	}

	public void messageDeleted(Message m, DTNHost where, boolean dropped) {
	}

	public void messageTransferAborted(Message m, DTNHost from, DTNHost to) {
	}

	public void messageTransferred(Message m, DTNHost from, DTNHost to, boolean finalTarget) {
			lastKey = m.getId();
			if (data.containsKey(lastKey)) {
				data.get(lastKey).add(new DataHolder(m, from, to));
			} else {
				List<DataHolder> list = new ArrayList<DataHolder>();
				list.add(new DataHolder(m, from, to));
				data.put(lastKey, list);
			}
	}

	public void newMessage(Message m) {
	}

	public void messageTransferStarted(Message m, DTNHost from, DTNHost to) {
	}

	@Override
	public void done() {
		if(data.size() > 1) {
			data.remove(lastKey);
		}
		
		write("*********** Statistics Message ***********");
		for (String s : data.keySet()) {
			write(getByMessageReport(s, data.get(s)));
		}

		write("*********** Simulation Statistics ***********");
		write(getBySimulationReport());

		super.done();
	}

	private String getByMessageReport(String id, List<DataHolder> dhList) {

		StringBuilder sb = new StringBuilder();
		sb.append("\n# Message ID: " + id);

		Message lastReceptor = dhList.get(dhList.size() - 1).m;
		double coordX = lastReceptor.getPayload().getX();
		double coordY = lastReceptor.getPayload().getY();

		Double[] intervalValues = new Double[dhList.size()];
		double maxDel = 0.0d;
		double minDel = 9999999999.0d;
		int counter = 0;
		for (DataHolder dh : dhList) {
			double interval = dh.m.getReceiveTime() - dh.m.getCreationTime();

			intervalValues[counter] = interval;
			if(interval > maxDel) {
				maxDel = interval;
			}
			if(interval < minDel) {
				minDel = interval;
			}
			counter++;
		}
		
		// Average
		double average = getAverage(intervalValues);
		sb.append("\nAverage: " + average);
		averages.add(average);

		// Median
		double median = getMedian(intervalValues);
		sb.append("\nMedian: " + median);
		
		for(int i = 0; i < intervalValues.length; i++) {
			medians.add(intervalValues[i]);
		}

		// Max Delay
		sb.append("\nMax Delay: " + maxDel);
		maxdelay.add(maxDel);

		// Min Delay
		sb.append("\nMin Delay: " + minDel);
		mindelay.add(minDel);

		// Delivery %
		double delivery = (double) intervalValues.length / 209 * 100;
		sb.append("\nDelivery (%): " + delivery );
		deliveryPercs.add(delivery);	

		// Payload
		sb.append("\nPayload: (x,y)=(" + coordX + "," + coordY + ")");

		
		return sb.toString();
	}
	
	private String getBySimulationReport() {
		
		StringBuilder sb = new StringBuilder();

		// Msgs created
		sb.append("\nTotal messages created: " + (data.size() - 1));
		
		// Average
		Double[] averageValues = new Double[averages.size()];
		averageValues = medians.toArray(averageValues);
		sb.append("\nAverage: " + getAverage(averageValues));
		
		// Median
		Double[] medianValues = new Double[medians.size()];
		medianValues = medians.toArray(medianValues);
		sb.append("\nMedian: " + getMedian(medianValues));

		// Max Average Delay
		double simAvgMaxDelay = 0.0;
		for(double d : maxdelay) {
			simAvgMaxDelay += d;
		}
		sb.append("\nMax Average Delay: " + simAvgMaxDelay / maxdelay.size());

		// Max Delay
		sb.append("\nMax Delay: " + Collections.max(maxdelay));

		// Min Average Delay
		double simAvgMinDelay = 0.0;
		for(double d : mindelay) {
			simAvgMinDelay += d;
		}
		sb.append("\nMin Average Delay: " + simAvgMinDelay / mindelay.size());

		// Min Delay
		sb.append("\nMin Delay: " + Collections.min(mindelay));
		
		// Average Delivery %
		double simAvgDelivery = 0.0;
		for(double d : deliveryPercs) {
			simAvgDelivery += d;
		}
		sb.append("\nMax Average Delivery (%): " + simAvgDelivery / deliveryPercs.size() );
		
		// Max Delivery %
		sb.append("\nMax Delivery (%): " + Collections.max(deliveryPercs) );

		// Min Delivery %
		sb.append("\nMin Delivery (%): " + Collections.min(deliveryPercs) );

		return sb.toString();
	}

	public double getMedian(Double[] values) {
		
		if (values.length % 2 == 0) {
			return ((double) values[values.length / 2] + (double) values[values.length / 2 - 1]) / 2;
		} else {
			return (double) values[values.length / 2];
		}
	}
	
	public double getAverage(Double[] values) {
		double avg = 0.0;
		for(int i = 0; i < values.length; i++) {
			avg += values[i];
		}
		
		return avg / values.length;
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

	public class DataHolder {
		Message m;
		DTNHost from;
		DTNHost to;

		DataHolder(Message m, DTNHost from, DTNHost to) {
			this.m = m;
			this.from = from;
			this.to = to;
		}
	}
}