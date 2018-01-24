package com.gmail.shelkovich.anton.web.controller;


import com.gmail.shelkovich.anton.service.BucketService;
import com.gmail.shelkovich.anton.service.model.dto.entity.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping(value="/bucket")
public class BucketController {

    @Autowired
    BucketService bucketService;

    @RequestMapping(value="", method = RequestMethod.GET)
    public String bucketPage(ModelMap model, HttpSession session) throws IOException {
        //model.addAttribute("bucket", bucketService.getAll());
        return "bucket";
    }

    @RequestMapping(value="/ajax/add/{productId}")
    public String addToBucket(@PathVariable Long productId, @RequestParam(value="productCount", required=false) Integer count, ModelMap model) throws IOException {
        if (count == null){
            count = 1;
        }
        bucketService.addToBucket(productId, count);
        model.addAttribute("currentBucketTotal", bucketService.getTotal());
        return "ajax_bucketCounter";
    }

    @RequestMapping(value="/ajax/list")
    public String bucketList(ModelMap model) throws IOException {
        model.addAttribute("bucket", bucketService.getAll());
        return "ajax_bucketList";
    }

}
