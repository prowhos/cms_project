package com.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Film;
import com.entity.SysDict;
import com.service.FilmServiceImpl;
import com.service.SysDictServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/a/film")
public class FilmController {
    @Autowired
    FilmServiceImpl filmService;
    @Autowired
    SysDictServiceImpl dictService;
    @ModelAttribute("film")
    public Film get(Integer id) {
        if (id != null){
            return filmService.getById(id);
        }else{
            return new Film();
        }
    }
    @RequestMapping("/list")
    public String list() {
        return "film/filmList";
    }

    @ResponseBody
    @RequestMapping("/getListData")
    public Map<String, Object> getListData(Integer pageIndex, Integer pageSize){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<Film> queryWrapper = new QueryWrapper<>();
        IPage<Film> filmIPage =filmService.page(new Page<>(pageIndex,pageSize),queryWrapper);
        map.put("data",filmIPage.getRecords());
        map.put("count",filmIPage.getTotal());
        return map;
    }

    @RequestMapping("/form")
    public String form(Film film,Model model){
        List<SysDict> typeDicts = dictService.list(new QueryWrapper<SysDict>().eq("type_code","filmtype"));
        List<SysDict> lnDicts = dictService.list(new QueryWrapper<SysDict>().eq("type_code","language"));
        model.addAttribute("typeDicts",typeDicts);
        model.addAttribute("lnDicts",lnDicts);
        model.addAttribute("film",film);
        return "film/filmForm";
    }

    @ResponseBody
    @RequestMapping("/save")
    public String save(Film film){
        filmService.saveOrUpdate(film);
        return "success";
    }
    @ResponseBody
    @RequestMapping("delete")
    public String delete(Film film){
        filmService.remove(new QueryWrapper<Film>().eq("id",film.getId()));
        return "success";
    }

    @RequestMapping("album/list")
    public String index(Model model,Integer pageNo){
        int current = 1;
        int pageSize = 10;
        if(pageNo != null) {
            current = pageNo;
        }
        QueryWrapper<Film> queryWrapper = new QueryWrapper<>();
        IPage<Film> iPage = filmService.page(new Page<Film>(current,pageSize),queryWrapper);
        model.addAttribute("page",iPage);
        return "/front/index";
    }
}
