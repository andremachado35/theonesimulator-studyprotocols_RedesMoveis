public class MessagesReport() {
    private float time;
    private String id;
    private int hopcount;
    private float deliveryTime;
    private String fromhost;
    private String tohost;
    private int remainingTtl;
    private String path;


    public MessagesReport(){

    }
 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getHopcount() {
		return hopcount;
	}

	public void setHopcount(int hopcount) {
		this.hopcount = hopcount;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(float time) {
		this.time = time;
	}
	/**
	 * @return the deliveryTime
	 */
	public float getDeliveryTime() {
		return deliveryTime;
	}
	/**
	 * @param deliveryTime the deliveryTime to set
	 */
	public void setDeliveryTime(float deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	/**
	 * @return the fromhost
	 */
	public String getFromhost() {
		return fromhost;
	}
	/**
	 * @param fromhost the fromhost to set
	 */
	public void setFromhost(String fromhost) {
		this.fromhost = fromhost;
	}
	/**
	 * @return the tohost
	 */
	public String getTohost() {
		return tohost;
	}
	/**
	 * @param tohost the tohost to set
	 */
	public void setTohost(String tohost) {
		this.tohost = tohost;
	}
	/**
	 * @return the remainingTtl
	 */
	public int getRemainingTtl() {
		return remainingTtl;
	}
	/**
	 * @param remainingTtl the remainingTtl to set
	 */
	public void setRemainingTtl(int remainingTtl) {
		this.remainingTtl = remainingTtl;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

}