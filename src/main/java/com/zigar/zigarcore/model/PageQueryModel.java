package com.zigar.zigarcore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zigar
 * @createTime 2020-01-19 16:24:11
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageQueryModel {

    private Integer page;
    private Integer pageSize;

}
