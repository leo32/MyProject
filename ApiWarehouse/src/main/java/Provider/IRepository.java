package Provider;

/**
 * Created by Administrator on 2017/10/8.
 */
public interface IRepository<T> {

    Object Create(T entity);
    Object Update(T entity);
    void Delete(T entity);
    void Copy(T source, T target);
    void Flush();

    IEnumerable<T> Get();
    T Get(int id);
    T Get(Expression<Func<T, bool>> predicate);

    int Count(Expression<Func<T, Boolean>> predicate);
    Iterable<T> Fetch(Runnable  predicate);
    Iterable<T> Fetch(Expression<T> predicate, Action<Orderable<T>> order);
    Iterable<T> Fetch(Expression<T> predicate, Action<Orderable<T>> order, int skip, int count);

    IList QueryBySQL(string sql);
    IList QueryBySQL(string sql, params object[] values);
    IList QueryBySQL(string sql, Action<ISQLQuery> action);
}
