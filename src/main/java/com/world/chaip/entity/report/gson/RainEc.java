package com.world.chaip.entity.report.gson;

import java.util.List;

public class RainEc {
    List<PptnGson> pptnGsonList;
    String message;

    public List<PptnGson> getPptnGsonList() {
        return pptnGsonList;
    }

    public void setPptnGsonList(List<PptnGson> pptnGsonList) {
        this.pptnGsonList = pptnGsonList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
