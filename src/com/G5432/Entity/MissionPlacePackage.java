package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-14
 * Time: 下午8:46
 * To change this template use File | Settings | File Templates.
 */
public class MissionPlacePackage {
    @Expose
    @DatabaseField
    private Integer packageId;
    @Expose
    @DatabaseField
    private String placeName;
    @Expose
    @DatabaseField
    private String placePoint;
    @Expose
    @DatabaseField
    private String sequence;

    public MissionPlacePackage(){

    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlacePoint() {
        return placePoint;
    }

    public void setPlacePoint(String placePoint) {
        this.placePoint = placePoint;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

}
