package com.world.chaip.controller;

import com.alibaba.druid.sql.visitor.functions.Char;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 毕泽浩
 * @Date: 19-3-26 上午9:04
 * @Descript
 */
@RestController
public class ActiveController {

    @GetMapping("1")
    public int MonitorA(){
        return 1;
    }

    @GetMapping("2")
    public int MonitorB(){
        return 2;
    }

    @GetMapping("3")
    public int MonitorC(){
        return 3;
    }

    @GetMapping("4")
    public int MonitorD(){
        return 4;
    }
}
