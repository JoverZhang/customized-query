package org.customizedquery.example.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.var;
import org.customizedquery.core.support.Page;
import org.customizedquery.core.support.PaginationQuery;
import org.customizedquery.example.server.domain.DemoUser;
import org.customizedquery.example.server.domain.dto.DemoUserDTO;
import org.customizedquery.example.server.domain.query.DemoUserQuery;
import org.customizedquery.example.server.example.api.DemoUserApi;
import org.customizedquery.example.server.repository.DemoUserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jover Zhang
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(DemoUserApi.PATH)
public class DemoUserController implements DemoUserApi {

    private final DemoUserRepository demoUserRepository;

    static Page<DemoUserDTO> convertAll(com.baomidou.mybatisplus.extension.plugins.pagination.Page<DemoUser> page) {
        Page<DemoUserDTO> rst = new Page<>();
        rst.setRecords(convertAll(page.getRecords()));
        rst.setTotal(page.getTotal());
        rst.setPages(page.getPages());
        return rst;
    }

    static List<DemoUserDTO> convertAll(List<DemoUser> list) {
        return list.stream().map(DemoUserController::convert).collect(Collectors.toList());
    }

    static DemoUserDTO convert(DemoUser o) {
        if (o == null) {
            return null;
        }
        DemoUserDTO dto = new DemoUserDTO();
        dto.setId(o.getId());
        dto.setName(o.getName());
        dto.setBalance(o.getBalance());
        dto.setBirthday(o.getBirthday());
        return dto;
    }

    @Override
    public DemoUserDTO one(DemoUserQuery demoUserQuery) {
        DemoUser demoUser = demoUserRepository.one(demoUserQuery);
        return convert(demoUser);
    }

    @Override
    public List<DemoUserDTO> list(PaginationQuery<DemoUserQuery> paginationQuery) {
        List<DemoUser> demoUsers = demoUserRepository.list(paginationQuery);
        return convertAll(demoUsers);
    }

    @Override
    public Page<DemoUserDTO> page(PaginationQuery<DemoUserQuery> paginationQuery) {
        var page = demoUserRepository.page(paginationQuery);
        return convertAll(page);
    }

}
