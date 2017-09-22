package top.caoyd.www.dao.beanDao;

import java.util.List;

/**
 * 基础DAO接口
 * @author 曹亚东
 *
 * @param <T>
 */
public abstract interface IBaseGenericDao<T> {

	public abstract int save(T paramT);

	public abstract int delete(T paramT);

	public abstract int update(T paramT);

	public abstract T getEntity(T paramT);

	public abstract List<T> getEntities(T paramT);

	public abstract List<T> selectAll();

	public abstract int deleteByIndex(T paramT);
	
}
