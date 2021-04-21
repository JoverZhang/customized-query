package org.customizedquery.example.server.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Jover Zhang
 */
@Data
@NoArgsConstructor
public class AbstractEntity {

    private Long id;

    private Boolean deleted;

    private Date gmtCreate;

    private Date gmtModified;

}
