package org.customizedquery.example.server.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Jover Zhang
 */
@Data
public class DemoUserDTO {

    private Long id;

    private String name;

    private BigDecimal balance;

    private Date birthday;

}
