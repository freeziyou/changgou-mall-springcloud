package com.changgou.search.controller;

import com.changgou.search.feign.SkuFeign;
import com.changgou.search.pojo.SkuInfo;
import entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author Dylan Guo
 * @date 9/26/2020 15:20
 * @description TODO
 */
@Controller
@RequestMapping("/search")
public class SkuController {

    @Autowired
    private SkuFeign skuFeign;

    @GetMapping("/list")
    public String search(@RequestParam(required = false) Map<String, String> searchMap, Model model) throws Exception {
        // 调用搜索微服务
        Map resultMap = skuFeign.search(searchMap);
        model.addAttribute("result", resultMap);

        // 计算分页
        Page<SkuInfo> pageInfo = new Page<>(
                Long.parseLong(resultMap.get("total").toString()),
                Integer.parseInt(resultMap.get("pageNumber").toString() + 1),
                Integer.parseInt(resultMap.get("pageSize").toString())
        );
        model.addAttribute("pageInfo", pageInfo);

        //  将条件存储, 用于页面回显数据
        model.addAttribute("searchMap", searchMap);
        // 获取上次请求地址, 2个 url，一个带排序，一个不带
        String[] urls = getUrl(searchMap);
        model.addAttribute("url", urls[0]);
        model.addAttribute("sortUrl", urls[1]);
        return "search";
    }

    /**
     * 拼接用户请求的 URL 地址
     *
     * @param searchMap
     * @return
     */
    public String[] getUrl(Map<String, String> searchMap) {
        // 初始化地址
        String url = "/search/list";
        String sortUrl = "/search/list";
        if (searchMap != null && searchMap.size() > 0) {
            url += ("?");
            sortUrl += ("?");
            for (Map.Entry<String, String> entry : searchMap.entrySet()) {
                // key 是搜索的条件对象
                String key = entry.getKey();
                // value 是搜索的值
                String value = entry.getValue();
                // 跳过分页参数
                if ("pageNum".equalsIgnoreCase(key)) {
                    continue;
                }
                url += key + "=" + value + "&";


                // 跳过排序参数
                if ("sortField".equalsIgnoreCase(key) || "sortRule".equalsIgnoreCase(key)) {
                    continue;
                }
                sortUrl += key + "=" + value + "&";

            }

            // 去掉最后一个 “&”
            url = url.substring(0, url.length() - 1);
            sortUrl = sortUrl.substring(0, sortUrl.length() - 1);
        }
        return new String[]{url.toString(), sortUrl.toString()};
    }

}
