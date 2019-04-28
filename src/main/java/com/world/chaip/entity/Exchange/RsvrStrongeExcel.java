package com.world.chaip.entity.Exchange;

import java.util.ArrayList;
import java.util.List;

/**
 * 水库蓄水量分析对比表
 */
public class RsvrStrongeExcel {

    private List<RsvrStrongeItem> strongeItemList;

    public RsvrStrongeExcel() {
        this.strongeItemList = new ArrayList<>();
    }

    public List<RsvrStrongeItem> getStrongeItemList() {
        return strongeItemList;
    }

    public void setStrongeItemList(List<RsvrStrongeItem> strongeItemList) {
        this.strongeItemList = strongeItemList;
    }

    public class RsvrStrongeItem{
        //河系
        private String hnnm;
        //蓄水量集合
        private List<RsvrStrongeChild> childList;

        public RsvrStrongeItem() {
            this.childList = new ArrayList<>();
        }

        public String getHnnm() {
            return hnnm;
        }

        public void setHnnm(String hnnm) {
            this.hnnm = hnnm;
        }

        public List<RsvrStrongeChild> getChildList() {
            return childList;
        }

        public void setChildList(List<RsvrStrongeChild> childList) {
            this.childList = childList;
        }
    }
}
