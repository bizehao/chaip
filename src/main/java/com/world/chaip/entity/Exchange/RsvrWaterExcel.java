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
        private String hnnm;
        //
        private List<RsvrWaterExchange> rList;

        public RsvrWC() {
            rList = new ArrayList<>();
        }


        public String getHnnm() {
            return hnnm;
        }

        public void setHnnm(String hnnm) {
            this.hnnm = hnnm;
        }

        public List<RsvrWaterExchange> getrList() {
            return rList;
        }

        public void setrList(List<RsvrWaterExchange> rList) {
            this.rList = rList;
        }
    }
}
