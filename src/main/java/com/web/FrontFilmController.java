package com.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Film;
import com.service.FilmServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/f/film")
public class FrontFilmController {
    @Autowired
    private FilmServiceImpl filmService;
    @RequestMapping("/index")
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

    @RequestMapping("/details")
    public String details(Model model,Film film){
        Film temp = filmService.getById(film.getId());
        model.addAttribute("film",temp);
        return "/front/filmDetails";
    }
}
