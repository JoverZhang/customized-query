package org.customizedquery.example.server.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.customizedquery.core.support.MybatisPlusRecover;
import org.customizedquery.core.support.PaginationQuery;
import org.customizedquery.example.server.domain.DemoUser;
import org.customizedquery.example.server.domain.query.DemoUserQuery;
import org.customizedquery.example.server.repository.DemoUserRepository;
import org.customizedquery.example.server.repository.mapper.DemoUserDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 持久化层实现类
 *
 * @author Jover Zhang
 */
@Repository
@RequiredArgsConstructor
public class DemoUserRepositoryImpl implements DemoUserRepository {

    @Getter private final DemoUserDao demoUserDao;

    @Override
    public DemoUser one(DemoUserQuery query) {
        QueryWrapper<DemoUser> wrapper = MybatisPlusRecover.recover(query, DemoUser.class);
        return demoUserDao.selectOne(wrapper);
    }

    @Override
    public List<DemoUser> list(PaginationQuery<DemoUserQuery> query) {
        QueryWrapper<DemoUser> wrapper = MybatisPlusRecover.recover(query.getQuery(), DemoUser.class);
        wrapper.last(" LIMIT " + query.getOffset() + ", " + query.getLimit());
        return demoUserDao.selectList(wrapper);
    }

    @Override
    public Page<DemoUser> page(PaginationQuery<DemoUserQuery> query) {
        QueryWrapper<DemoUser> wrapper = MybatisPlusRecover.recover(query.getQuery(), DemoUser.class);
        return demoUserDao.selectPage(new Page<>(query.getCurrent(), query.getSize()), wrapper);
    }

}
