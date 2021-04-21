package org.customizedquery.example.server.repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.customizedquery.core.support.PaginationQuery;
import org.customizedquery.example.server.domain.DemoUser;
import org.customizedquery.example.server.domain.query.DemoUserQuery;

import java.util.List;

/**
 * @author Jover Zhang
 */
public interface DemoUserRepository {

    DemoUser one(DemoUserQuery query);

    List<DemoUser> list(PaginationQuery<DemoUserQuery> query);

    Page<DemoUser> page(PaginationQuery<DemoUserQuery> query);

}
