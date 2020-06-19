package com.core.controller;

import com.core.service.TcgnicalSupervisionService;
import com.core.util.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.RequestParam;
import com.core.entity.CKnowledge;
import com.core.service.CknoService;

@Controller
@RequestMapping("/KnowledgeController")
public class KnowledgeController {

	@Resource
	private TcgnicalSupervisionService tcgnicalSupervisionService;
	@Resource
	private CknoService ckonservice;
	/*
	 * @RequestMapping(value="/toempInfo",method=RequestMethod.GET) public String
	 * toempInfo(Employee employee,HttpServletRequest request,HttpSession session) {
	 * String username=(String) session.getAttribute("username");
	 * System.out.println("邮箱："+username);
	 * employee=employeeService.selectByEmailWithDep(username); // String
	 * emp_name=employee.getEmpName(); // String gender=employee.getGender(); //
	 * String email=employee.getEmail(); // Integer deptno=employee.getdId(); //
	 * String dept=deptno.toString(); // request.setAttribute(emp_name, "emp_name");
	 * // request.setAttribute(gender,"gender"); // request.setAttribute(email,
	 * "email"); // request.setAttribute(dept,"dept");
	 * request.getSession().setAttribute("empName", employee.getEmpName());
	 * request.getSession().setAttribute("gender", employee.getGender());
	 * request.getSession().setAttribute("email", employee.getEmail());
	 * System.out.println(employee.getEmpName()); return "empInfo";
	 * 
	 * }
	 */
	/*@RequestMapping(value="/knoInfo",method=RequestMethod.POST)*/
    @RequestMapping("/knoInfo")
    /*CKnowledge cknowledge,HttpServletRequest request,HttpSession session*/
	public String toempInfo(@RequestParam(value = "node_name", required = false) String name,
                            @RequestParam(value = "node_type", required = false) String type,
                            @RequestParam(value = "node_id", required = false) String id,
                            CKnowledge cknowledge,
                            HttpServletRequest request,
                            HttpSession session,
                            HttpServletResponse response) throws Exception{
		/*根据传过来的数据konwledge匹配到值*/
		String knowledge=name;
		cknowledge=ckonservice.selectByKno(knowledge);
		/*将数据传到session中*/
		request.getSession().setAttribute("knowledge",cknowledge.getKnowledge());
		request.getSession().setAttribute("ContenText",cknowledge.getContenttext());
		request.getSession().setAttribute("Video", cknowledge.getVideo());
		request.getSession().setAttribute("Link", cknowledge.getLink());
		request.getSession().setAttribute("exercise", cknowledge.getExercise());
		/*以上数据也可通过EL表达式传输*/
		/*传数据*/

		/*知识点的相关章节*/
		int id_int=Integer.parseInt(id);
		String name_chapter=tcgnicalSupervisionService.getChapterByTopic(id_int);
		request.getSession().setAttribute("PreChapter",name_chapter);

        /*将节点类型，id传到session用于画graph*/
        request.getSession().setAttribute("nodeType",type);
        request.getSession().setAttribute("nodeId",id);
        /*将任意数据传回js，以便在js中跳转页面*/
        Map map=new HashMap();
        map.put("nodeData",name);
        JSONObject result= JSONObject.fromObject(map);
        ResponseUtil.write(response, result);
		return null;
		
	}


}
