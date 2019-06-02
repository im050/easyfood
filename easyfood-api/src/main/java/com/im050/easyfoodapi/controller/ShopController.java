package com.im050.easyfoodapi.controller;


import com.im050.easyfoodapi.service.ShopService;
import com.im050.easyfoodcommon.utils.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linyulin
 * @since 2019-06-01
 */
@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    ShopService shopService;

    @GetMapping("/full-menu/{id}")
    public Response index(@PathVariable Integer id) {
        return Response.success(shopService.getAllFoodsWithMenu(id));
    }
}
