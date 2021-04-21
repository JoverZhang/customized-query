package org.customizedquery.core.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 持久化层通用接口
 *
 * <p>封装常用方法,
 * 目前直接使用 mybatis-plus 的 {@link BaseMapper} 作为默认接口
 *
 * @author Jover Zhang
 */
public interface BaseRepository<T> extends BaseMapper<T> {

}
