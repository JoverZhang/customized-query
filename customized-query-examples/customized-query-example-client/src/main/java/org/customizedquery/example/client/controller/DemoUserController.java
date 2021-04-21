package org.customizedquery.example.client.controller;

import org.customizedquery.core.support.Page;
import org.customizedquery.core.support.PaginationQuery;
import org.customizedquery.core.utils.Asserts;
import org.customizedquery.example.server.domain.dto.DemoUserDTO;
import org.customizedquery.example.server.domain.query.DemoUserQuery;
import org.customizedquery.example.server.example.api.DemoUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.customizedquery.core.support.Range.*;

/**
 * @author Jover Zhang
 */
@RestController
@RequestMapping("/example-query")
public class DemoUserController {

    @Autowired
    private DemoUserApi demoUserApi;

    @GetMapping("/example-one")
    public DemoUserDTO one() {
        DemoUserQuery query = DemoUserQuery.queryBuilder()
                .name("little_b")
                .id(ne(1L))
                .build();

        DemoUserDTO result = demoUserApi.one(query);

        Asserts.notNull(result);
        return result;
    }

    @GetMapping("/example-list")
    public List<DemoUserDTO> list() {
        DemoUserQuery query = DemoUserQuery.queryBuilder()
                .id(eq(1L))
                .birthday(eq(new Date(0)))
                .orderById(true)
                .build();
        PaginationQuery<DemoUserQuery> paginationQuery = PaginationQuery.query(1, 3, query);

        List<DemoUserDTO> result = demoUserApi.list(paginationQuery);

        Asserts.isTrue(result.size() == 1);
        return result;
    }

    @GetMapping("/example-page")
    public Page<DemoUserDTO> page() {
        DemoUserQuery query = DemoUserQuery.queryBuilder()
                .id(between(1L, 3L))
                .birthday(ge(new Date(86400000)))
                .orderByBirthday(false)
                .orderByName(true)
                .build();
        PaginationQuery<DemoUserQuery> paginationQuery = PaginationQuery.query(1, 4, query);

        Page<DemoUserDTO> result = demoUserApi.page(paginationQuery);

        Asserts.isTrue(result.getTotal() == 2);
        Asserts.isTrue(result.getRecords().size() == 2);
        return result;
    }

    @PostMapping("/one")
    public DemoUserDTO one(@RequestBody DemoUserQuery demoUserQuery) {
        return demoUserApi.one(demoUserQuery);
    }

    @PostMapping("/list")
    public List<DemoUserDTO> list(@RequestBody PaginationQuery<DemoUserQuery> paginationQuery) {
        return demoUserApi.list(paginationQuery);
    }

    @PostMapping("/page")
    public Page<DemoUserDTO> page(@RequestBody PaginationQuery<DemoUserQuery> paginationQuery) {
        return demoUserApi.page(paginationQuery);
    }

}
