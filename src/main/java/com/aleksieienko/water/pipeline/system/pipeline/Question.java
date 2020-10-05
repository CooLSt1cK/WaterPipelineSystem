package com.aleksieienko.water.pipeline.system.pipeline;

public class Question {
    private Integer pointAId;
    private Integer pointBId;

    public Question(Integer pointAId, Integer pointBId) {
        this.pointAId = pointAId;
        this.pointBId = pointBId;
    }

    public Question() {
    }

    public Integer getPointAId() {
        return pointAId;
    }

    public void setPointAId(Integer pointAId) {
        this.pointAId = pointAId;
    }

    public Integer getPointBId() {
        return pointBId;
    }

    public void setPointBId(Integer pointBId) {
        this.pointBId = pointBId;
    }

    @Override
    public String toString() {
        return "Question{" +
                "pointAId=" + pointAId +
                ", pointBId=" + pointBId +
                '}';
    }
}
