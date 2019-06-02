package com.im050.easyfoodapi.dao;

import com.im050.easyfoodcommon.entity.Attr;
import com.im050.easyfoodcommon.entity.AttrRelationship;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author linyulin
 * @since 2019-06-01
 */
public interface AttrRelationshipDao extends BaseMapper<AttrRelationship> {

    @Select({"<script>",
            "SELECT * FROM attr WHERE id IN ",
            "<foreach collection='ids' index='index' item='item' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "UNION",
            "SELECT * FROM attr WHERE parent_id IN",
            "<foreach collection='ids' index='index' item='item' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"})
    List<Attr> getAttrsWithIds(@Param("ids") List<Integer> ids);
}