package com.core.service.impl;

import com.core.entity.User;
import com.core.service.UserService;
import com.core.util.JdbcUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("userService")
public class UserServiceImpl implements UserService {

	/** 登陆判断 */
	@Override
	public User login(User user) {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		List<Map<String, Object>> result = null;
		try {
			// 查询结果集
			result = jdbcUtil.findList("MATCH (na:User{username:'" + user.getUserName() + "'}) where na.password='" + user.getPassword()
					+ "' return na ");
			if (null != result && result.size() > 0) {
				// 设置用户角色
				String role = ((Map<String, String>) result.get(0).get("na")).get("role");
				String name = ((Map<String, String>) result.get(0).get("na")).get("name");
				user.setRole(role);
				user.setName(name);
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return null;
	}

	/** 根据用户姓名或者登录名模糊查询 */
	@Override
	public List<User> findUser(Map<String, Object> map) {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		List<Map<String, Object>> result = null;
		try {
			String cql = "MATCH (na:User) ";
			if (!"".equals(map.get("userName")) && null != map.get("userName")) {
				if (map.get("start") != null) {
					cql += "where na.username=~'.*" + map.get("userName") + ".*'";
					cql += "or na.name=~'.*" + map.get("userName") + ".*'";
				} else {
					cql += "where na.username='" + map.get("userName") + "'";
				}
			}
			cql += " return na ";
			if (map.get("start") != null) {
				cql += "skip {1} limit {2}";
				result = jdbcUtil.findList(cql, map.get("start"), map.get("size"));
			} else {
				result = jdbcUtil.findList(cql);
			}
			// 查询结果集
			if (null != result) {
				List<User> arrayList = new ArrayList<User>();
				// 封装结果集
				for (Map<String, Object> result2 : result) {
					User user = new User();
					Map<String, Object> result3 = (Map<String, Object>) result2.get("na");
					user.setId(Integer.valueOf(result3.get("_id").toString()));
					user.setUserName((String) result3.get("username"));
					user.setName((String) result3.get("name"));
					user.setPhone((String) result3.get("phone"));
					user.setEmail((String) result3.get("email"));
					user.setRole((String) result3.get("role"));
					arrayList.add(user);
				}
				return arrayList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return null;
	}

	/** 更新用户信息 */
	@Override
	public int updateUser(User user) {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		int result = 0;
		try {
			String cql = "MATCH (na:User) where id(na)={1} set na.username={2}, na.name={3}, na.phone={4}, na.email={5}, na.role={6} ";
			if (user.getPassword() != null && !"".equals(user.getPassword())) {
				cql += ", na.password='" + user.getPassword() + "'";
			}

			// 查询结果集
			result = jdbcUtil.update(cql, user.getId(), user.getUserName(), user.getName(), user.getPhone(), user.getEmail(),
					user.getRole());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return 1;
	}

	/** 获取分页查询的总数 */
	@Override
	public Long getTotalUser(Map<String, Object> map) {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		Long result2 = 0l;
		try {

			String cql = "MATCH (na:User) ";
			if (!"".equals(map.get("username")) && null != map.get("username")) {
				cql += "where na.username=~'.*" + map.get("username") + ".*'";
				cql += "or na.name=~'.*" + map.get("name") + ".*'";
			}
			cql += " return count(na) ";
			// 查询结果集
			result2 = (Long) jdbcUtil.findList(cql).get(0).get("count(na)");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return result2;
	}

	/** 更新用户信息 */
	@Override
	public int addUser(User user) {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		int result = 0;
		try {
			// 查询结果集
			result = jdbcUtil.update("create (na:User{username:{1}, password:{2}, name:{3}, phone:{4}, email:{5}, role:{6}}) return na",
					user.getUserName(), user.getPassword(), user.getName(), user.getPhone(), user.getEmail(), user.getRole());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return result;
	}

	/** 根据用户id删除用户 */
	@Override
	public int deleteUser(Integer id) {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		int result = 0;
		try {
			// 查询结果集
			result = jdbcUtil.update("match (na:User) where ID(na)={1} delete na ", id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return result;
	}

	/** 首页展示的每个标签对应的节点数量 */
	@Override
	public Map<String, String> findLabels() {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();

		HashMap<String, String> hashMap = new HashMap<String, String>();
		List<Map<String, Object>> result = null;
		try {
			// 查询结果集
			result = jdbcUtil
					.findList("match (n) with 'Count' as labels, count(n) as count, labels(n) as label where not 'User' in label RETURN labels,count union all  MATCH (n) with labels(n)[0] as labels, count(n) as count where 'User' <> labels RETURN labels,count order by count desc");
			long count=0;
			for (Map<String, Object> map : result) {
				if(!map.get("labels").equals("Count"))//标签不是Count的节点数量
					count+=(long)map.get("count");
				hashMap.put(map.get("labels").toString(), map.get("count").toString());
			}
			System.out.println(count);
			hashMap.put("Count",count+"");

			/*Logger log = Logger.getLogger(JdbcUtil.class);// 日志文件
			log.info("数量"+count);*/
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return hashMap;
	}

}
