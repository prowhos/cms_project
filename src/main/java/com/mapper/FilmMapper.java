package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Film;
import org.springframework.stereotype.Repository;

/**
 * @Auther: jzhang
 * @Date: 2018/10/23 22:31
 * @Description: 影片接口
 */
@Repository
public interface FilmMapper extends BaseMapper<Film> {
}
