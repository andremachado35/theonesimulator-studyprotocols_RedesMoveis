public class Message {
    float startTime;
    float endTime;
    String status;
    String source;
    String dest;
    String name;

    public Message() {
    }

    public Message(float endTime, String status, String source, String dest) {
        this.endTime = endTime;
        this.status = status;
        this.source = source;
        this.dest = dest;
    }

    public float getEndTime() {
        return endTime;
    }

    public void setEndTime(float endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDelay()
    {
        return endTime - startTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", status='" + status + '\'' +
                ", source='" + source + '\'' +
                ", dest='" + dest + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
