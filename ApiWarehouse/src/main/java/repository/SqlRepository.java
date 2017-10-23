package repository;
import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2017/10/22.
 */
public class SqlRepository {
    @PersistenceUnit
    private EntityManagerFactory emf;
/*
    public List<Object[]> queryBySQL(String sql){
        EntityManager em=emf.createEntityManager();
        Query query=em.createNativeQuery(sql);
        List<Object[]> list = query.getResultList();
        em.close();
        return  list;
    }
*/

    public List queryBySQL(String sql, Object obj){
        EntityManager em=emf.createEntityManager();
        Query query=em.createNativeQuery(sql,obj.getClass());
        List list = query.getResultList();
        em.close();
        return  list;
    }

    public List queryBySQL(String sql){
        EntityManager em=emf.createEntityManager();
        Query query=em.createNativeQuery(sql);
        List list = query.getResultList();
        em.close();
        return  list;
    }
}
