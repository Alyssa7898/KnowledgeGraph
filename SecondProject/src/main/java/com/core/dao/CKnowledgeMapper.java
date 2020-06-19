package com.core.dao;

import com.core.entity.CKnowledge;
import com.core.entity.CKnowledgeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface  CKnowledgeMapper{
    long countByExample(CKnowledgeExample example);

    int deleteByExample(CKnowledgeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CKnowledge record);

    int insertSelective(CKnowledge record);

    List<CKnowledge> selectByExample(CKnowledgeExample example);

    CKnowledge selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CKnowledge record, @Param("example") CKnowledgeExample example);

    int updateByExample(@Param("record") CKnowledge record, @Param("example") CKnowledgeExample example);

    int updateByPrimaryKeySelective(CKnowledge record);

    int updateByPrimaryKey(CKnowledge record);
    /*通过 knowledge查出信息*/
	CKnowledge selectByKno(String knowledge);
}