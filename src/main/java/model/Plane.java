package model;

import Annotation.Column;
import Annotation.Entity;
import Annotation.PK;
import javafx.scene.chart.ValueAxis;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

@Entity(name = "MAYBAY")
public class Plane {
    @PK(value = "MaMB")
    private String id;
    @Column(value = "Loai")
    private String type;
    @Column(value = "TamBay")
    private long tamBay;

    public Plane(String id, String type, long tamBay) {
        this.id = id;
        this.type = type;
        this.tamBay = tamBay;
    }

    public Plane() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTamBay() {
        return tamBay;
    }

    public void setTamBay(long tamBay) {
        this.tamBay = tamBay;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", tamBay=" + tamBay +
                '}';
    }
}
