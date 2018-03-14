package com.world.chaip.entity.Exchange;

import java.util.ArrayList;
import java.util.List;

public class RsvrWaterExcel {

    List<RsvrWC> rsvrWCList;

    public RsvrWaterExcel() {
        this.rsvrWCList = new ArrayList<>();
    }

    public List<RsvrWC> getRsvrWCList() {
        return rsvrWCList;
    }

    public void setRsvrWCList(List<RsvrWC> rsvrWCList) {
        this.rsvrWCList = rsvrWCList;
    }

    public class RsvrWC extends RsvrWaterExchange{
        //水系名
        private String name;
        //
        private List<RsvrWaterExchange> rList;

        public RsvrWC() {
            rList = new ArrayList<>();
        }


        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }

        public List<RsvrWaterExchange> getrList() {
            return rList;
        }

        public void setrList(List<RsvrWaterExchange> rList) {
            this.rList = rList;
        }
    }
}
