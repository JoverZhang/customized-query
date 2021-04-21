package org.customizedquery.core.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 持久化层通用基类
 *
 * <p>目前直接使用 mybatis-plus 的 {@link BaseMapper} 作为默认实现
 *
 * @author Jover Zhang
 */
public abstract class BaseRepositoryImpl<T, M extends BaseRepository<T>> implements BaseRepository<T> {

    abstract public M getBaseMapper();

    /**
     * {@inheritDoc}
     */
    @Override
    public int insert(T entity) {
        return getBaseMapper().insert(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteById(Serializable id) {
        return getBaseMapper().deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        return getBaseMapper().deleteByMap(columnMap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(Wrapper<T> queryWrapper) {
        return getBaseMapper().delete(queryWrapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        return getBaseMapper().deleteBatchIds(idList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateById(T entity) {
        return getBaseMapper().updateById(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update(T entity, Wrapper<T> updateWrapper) {
        return getBaseMapper().update(entity, updateWrapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T selectById(Serializable id) {
        return getBaseMapper().selectById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> selectBatchIds(Collection<? extends Serializable> idList) {
        return getBaseMapper().selectBatchIds(idList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> selectByMap(Map<String, Object> columnMap) {
        return getBaseMapper().selectByMap(columnMap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T selectOne(Wrapper<T> queryWrapper) {
        return getBaseMapper().selectOne(queryWrapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer selectCount(Wrapper<T> queryWrapper) {
        return getBaseMapper().selectCount(queryWrapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> selectList(Wrapper<T> queryWrapper) {
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<T> queryWrapper) {
        return getBaseMapper().selectMaps(queryWrapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> selectObjs(Wrapper<T> queryWrapper) {
        return getBaseMapper().selectObjs(queryWrapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E1 extends IPage<T>> E1 selectPage(E1 page, Wrapper<T> queryWrapper) {
        return getBaseMapper().selectPage(page, queryWrapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E1 extends IPage<Map<String, Object>>> E1 selectMapsPage(E1 page, Wrapper<T> queryWrapper) {
        return getBaseMapper().selectMapsPage(page, queryWrapper);
    }

}
