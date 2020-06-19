package com.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.dao.CKnowledgeMapper;
import com.core.entity.CKnowledge;
@Service
public class CknoService {
	@Autowired 
CKnowledgeMapper cKnowledgeMapper ;
	/*通过Id出信息*/
	public CKnowledge selectById(Integer Id) {
		CKnowledge ckno=cKnowledgeMapper.selectByPrimaryKey(Id);
		return ckno;
		
	}
	/*通过Knowledge查出信息*/
	public CKnowledge selectByKno(String Knowledge) {
		CKnowledge ckno=cKnowledgeMapper.selectByKno(Knowledge);
		return ckno;	
	}
	

}
