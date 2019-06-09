package com.im050.easyfood.admin.param;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class SortParam {
    private Integer shopId;
    private List<Integer> ids;
}
