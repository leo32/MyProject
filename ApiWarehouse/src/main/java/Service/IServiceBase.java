package Service;

/**
 * Created by Administrator on 2017/10/9.
 */
public interface IServiceBase<T> : IDependency where T : EntityBase
        {
        object Create(T entity);
        object Update(T entity);
        void Delete(int id);
        void Delete(string idList);
        void Flush();

        IEnumerable<T> Get();
        T Get(int id);
        T Get(Expression<Func<T, bool>> predicate);

        IEnumerable<T> Table { get; }

        int Count(Expression<Func<T, bool>> predicate);
        IEnumerable<T> Fetch(Expression<Func<T, bool>> predicate);
        IEnumerable<T> Fetch(Expression<Func<T, bool>> predicate, Action<Orderable<T>> order);
        IEnumerable<T> Fetch(Expression<Func<T, bool>> predicate, Action<Orderable<T>> order, int skip, int count);

        IList QueryBySQL(string sql);
        IList QueryBySQL(string sql, params object[] values);
        IList QueryBySQL(string sql, Action<ISQLQuery> action);

        int Edit(JObject data);
        }
