/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Distance;

/**
 *
 * @author srushti
 */
public class DistanceParam {
    private String[] destinationAddresses;
    private String[] originAddresses;
    private Row[] rows;
    //private String status;

    public String[] getDestinationAddresses() { return destinationAddresses; }
    public void setDestinationAddresses(String[] value) { this.destinationAddresses = value; }

    public String[] getOriginAddresses() { return originAddresses; }
    public void setOriginAddresses(String[] value) { this.originAddresses = value; }

    public Row[] getRows() { return rows; }
    public void setRows(Row[] value) { this.rows = value; }

    //public String getStatus() { return status; }
    //public void setStatus(String value) { this.status = value;}

   
// Row.java
public class Row {
    private Element[] elements;

    public Element[] getElements() { return elements; }
    public void setElements(Element[] value) { this.elements = value; }
}

public class Element {
    private Distance distance;
    private Distance duration;
    //private String status;

    public Distance getDistance() { return distance; }
    public void setDistance(Distance value) { this.distance = value; }

    public Distance getDuration() { return duration; }
    public void setDuration(Distance value) { this.duration = value; }

   // public String getStatus() { return status; }
   // public void setStatus(String value) { this.status = value; }
}

public class Distance {
    private String text;
    private long value;

    public String getText() { return text; }
    public void setText(String value) { this.text = value; }

    public long getValue() { return value; }
    public void setValue(long value) { this.value = value; }
}
public class Duration {
    private String text;
    private long value;

    public String getText() { return text; }
    public void setText(String value) { this.text = value; }

    public long getValue() { return value; }
    public void setValue(long value) { this.value = value; }
}

}
