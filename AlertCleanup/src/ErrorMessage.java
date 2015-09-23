
import java.util.StringTokenizer;

class ErrorMessage {

    private int severity;
    private int count;
    private String node;
    private String property;
    private String firstOccur;
    private String summary;

    public ErrorMessage(String message) {
	messageParser(message);
    }

    public int getSeverity() {
	return severity;
    }

    public void setSeverity(int severity) {
	this.severity = severity;
    }

    public int getCount() {
	return count;
    }

    public void setCount(int count) {
	this.count = count;
    }

    public String getNode() {
	return node;
    }

    public void setNode(String node) {
	this.node = node;
    }

    public String getProperty() {
	return property;
    }

    public void setProperty(String property) {
	this.property = property;
    }

    public String getFirstOccur() {
	return firstOccur;
    }

    public void setFirstOccur(String firstOccur) {
	this.firstOccur = firstOccur;
    }

    public String getSummary() {
	return summary;
    }

    public void setSummary(String summary) {
	this.summary = summary;
    }

    public String toString() {
	return severity + "|" + count + "|" + node + "|" + property + "|" + firstOccur + "|" + summary;
    }

    private void messageParser(String message) {
	StringTokenizer strTok = new StringTokenizer(message, "|");
	this.severity = Integer.parseInt(strTok.nextToken());
	this.count = Integer.parseInt(strTok.nextToken());
	this.node = strTok.nextToken();
	this.property = strTok.nextToken();
	this.firstOccur = strTok.nextToken();
	this.summary = strTok.nextToken();
    }

}
