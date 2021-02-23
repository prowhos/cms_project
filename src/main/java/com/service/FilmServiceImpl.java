package com.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Film;
import com.mapper.FilmMapper;
import org.springframework.stereotype.Service;

@Service
public class FilmServiceImpl extends ServiceImpl<FilmMapper, Film> implements IFilmService {
}
