package model;

import Annotation.Column;
import Annotation.Entity;
import Annotation.PK;

import java.time.LocalTime;


@Entity(name = "CHUYENBAY")
public class Trip {
    @PK(value = "MaCB")
    private String id;
    @Column(value = "GaDi")
    private String start;
    @Column(value = "GaDen")
    private String destination;
    @Column(value = "DoDai")
    private long length;
    @Column(value = "GioDi")
    private LocalTime stratedTime;
    @Column(value = "GioDen")
    private LocalTime destinationTime;
    @Column(value = "ChiPhi")
    private int price;

    public Trip() {
    }

    public Trip(String id, String start, String destination, long length, LocalTime startedTime, LocalTime destinationTime, int price) {
        this.id = id;
        this.start = start;
        this.destination = destination;
        this.length = length;
        this.stratedTime = startedTime;
        this.destinationTime = destinationTime;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public LocalTime getStratedTime() {
        return stratedTime;
    }

    public void setStratedTime(LocalTime stratedTime) {
        this.stratedTime = stratedTime;
    }

    public LocalTime getDestinationTime() {
        return destinationTime;
    }

    public void setDestinationTime(LocalTime destinationTime) {
        this.destinationTime = destinationTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "TripDao{" +
                "id='" + id + '\'' +
                ", start='" + start + '\'' +
                ", destination='" + destination + '\'' +
                ", length=" + length +
                ", stratedTime=" + stratedTime +
                ", destinationTime=" + destinationTime +
                ", price=" + price +
                '}';
    }
}
