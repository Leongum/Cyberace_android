package com.G5432.Entity;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-14
 * Time: 下午8:46
 * To change this template use File | Settings | File Templates.
 */
public class MissionPlacePackage {

    @DatabaseField
    private Integer packageId;

    @DatabaseField
    private String placeName;

    @DatabaseField
    private String placePoint;

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
