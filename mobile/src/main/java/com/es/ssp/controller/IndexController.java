package com.es.ssp.controller;

import com.es.ssp.annotation.NeedFans;
import com.es.ssp.model.Fans;
import common.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController{


    @RequestMapping("/reportList")
    @NeedFans
    public String reportList(){
        Fans fans=getAttribute("fans");
        if(fans!=null){
            System.out.println(fans);
        }
        return "/report_list";
    }
}
