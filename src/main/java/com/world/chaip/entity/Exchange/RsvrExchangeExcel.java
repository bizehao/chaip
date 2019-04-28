package com.world.chaip.entity.Exchange;

import java.util.ArrayList;
import java.util.List;

public class RsvrExchangeExcel {

    private List<RsvrExchangeItem> RsvrPro;

    public RsvrExchangeExcel() {
        this.RsvrPro = new ArrayList<>();
    }

    public List<RsvrExchangeItem> getRsvrPro() {
        return RsvrPro;
    }

    public void setRsvrPro(List<RsvrExchangeItem> rsvrPro) {
        RsvrPro = rsvrPro;
    }

    public class RsvrExchangeItem{
        //河名
        private String rvnm;
        //子项信息
        private List<RsvrTZCount> data;

        public RsvrExchangeItem() {
            this.data = new ArrayList<>();
        }

        public RsvrExchangeItem(String rvnm, List<RsvrTZCount> data) {
            this.rvnm = rvnm;
            this.data = data;
        }

        public String getRvnm() {
            return rvnm;
        }

        public void setRvnm(String rvnm) {
            this.rvnm = rvnm;
        }

        public List<RsvrTZCount> getData() {
            return data;
        }

        public void setData(List<RsvrTZCount> data) {
            this.data = data;
        }
    }
}
