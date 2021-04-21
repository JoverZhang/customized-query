package org.customizedquery.example.server.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Jover Zhang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DemoUser extends AbstractEntity {

    private String name;

    private BigDecimal balance;

    private Date birthday;

}
