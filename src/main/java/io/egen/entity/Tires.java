package io.egen.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tires {
    @Id
    private String tireId;

    @OneToOne(mappedBy = "tires")
    private Readings readings;

    private int frontLeft;
    private int frontRight;
    private int rearLeft;
    private int rearRight;

    public Tires() {
        this.tireId = UUID.randomUUID().toString();
    }

    public String getTireId() {
        return tireId;
    }

    public void setTireId(String tireId) {
        this.tireId = tireId;
    }

    public Readings getReadings() {
        return readings;
    }

    public void setReadings(Readings readings) {
        this.readings = readings;
    }

    public int getFrontLeft() {
        return frontLeft;
    }

    public void setFrontLeft(int frontLeft) {
        this.frontLeft = frontLeft;
    }

    public int getFrontRight() {
        return frontRight;
    }

    public void setFrontRight(int frontRight) {
        this.frontRight = frontRight;
    }

    public int getRearLeft() {
        return rearLeft;
    }

    public void setRearLeft(int rearLeft) {
        this.rearLeft = rearLeft;
    }

    public int getRearRight() {
        return rearRight;
    }

    public void setRearRight(int rearRight) {
        this.rearRight = rearRight;
    }

    @Override
    public String toString() {
        return "Tires{" +
                "tireId='" + tireId + '\'' +
                ", readings=" + readings +
                ", frontLeft=" + frontLeft +
                ", frontRight=" + frontRight +
                ", rearLeft=" + rearLeft +
                ", rearRight=" + rearRight +
                '}';
    }
}
