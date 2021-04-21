package org.customizedquery.example.server.example.api;

import org.customizedquery.core.support.Page;
import org.customizedquery.core.support.PaginationQuery;
import org.customizedquery.example.server.domain.dto.DemoUserDTO;
import org.customizedquery.example.server.domain.query.DemoUserQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Jover Zhang
 */
@FeignClient(value = "example-server", url = "http://localhost:8080", path = DemoUserApi.PATH)
public interface DemoUserApi {

    String PATH = "/example-query";

    @PostMapping("/one")
    DemoUserDTO one(@Valid @RequestBody DemoUserQuery demoUserQuery);

    @PostMapping("/list")
    List<DemoUserDTO> list(@Valid @RequestBody PaginationQuery<DemoUserQuery> paginationQuery);

    @PostMapping("/page")
    Page<DemoUserDTO> page(@Valid @RequestBody PaginationQuery<DemoUserQuery> paginationQuery);

}
