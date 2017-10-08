package Provider;

/**
 * Created by Administrator on 2017/10/8.
 */
public class Repository<T> : IRepository<T> where T : Cwg.Models.EntityBase
        {
private readonly ISessionLocator _sessionLocator;
private static object objLock = new object();

public Repository(ISessionLocator sessionLocator)
        {
        _sessionLocator = sessionLocator;
        }

protected virtual ISessionLocator SessionLocator
        {
        get { return _sessionLocator; }
        }

public virtual ISession Session
        {
        get { return SessionLocator.For(typeof(T)); }//;
        }

public virtual IQueryable<T> Table
        {
        get
        {
        return Session.Query<T>().Cacheable();//.Cacheable<T>().CacheMode(CacheMode.Normal);
        }//Session.Query<T>().Cacheable();
        }

        #region IRepository<T> Members

        object IRepository<T>.Create(T entity)
        {
        return Create(entity);
        }

        object IRepository<T>.Update(T entity)
        {
        return Update(entity);
        }

        void IRepository<T>.Delete(T entity)
        {
        Delete(entity);
        }

        void IRepository<T>.Copy(T source, T target)
        {
        Copy(source, target);
        }

        void IRepository<T>.Flush()
        {
        Flush();
        }

public IEnumerable<T> Get()
        {
        return Table;
        }

        T IRepository<T>.Get(int id)
        {
        return Get(id);
        }

        T IRepository<T>.Get(Expression<Func<T, bool>> predicate)
        {
        return Get(predicate);
        }

        IQueryable<T> IRepository<T>.Table
        {
        get { return Table; }
        }

        int IRepository<T>.Count(Expression<Func<T, bool>> predicate)
        {
        return Count(predicate);
        }

        IEnumerable<T> IRepository<T>.Fetch(Expression<Func<T, bool>> predicate)
        {
        return Fetch(predicate).ToReadOnlyCollection();
        }

        IEnumerable<T> IRepository<T>.Fetch(Expression<Func<T, bool>> predicate, Action<Orderable<T>> order)
        {
        return Fetch(predicate, order).ToReadOnlyCollection();
        }

        IEnumerable<T> IRepository<T>.Fetch(Expression<Func<T, bool>> predicate, Action<Orderable<T>> order, int skip,
        int count)
        {
        return Fetch(predicate, order, skip, count).ToReadOnlyCollection();
        }

        #endregion

public virtual T Get(int id)
        {
        return Session.Get<T>(id);
        }

public virtual T Get(Expression<Func<T, bool>> predicate)
        {
        return Fetch(predicate).FirstOrDefault();
        }

public virtual object Create(T entity)
        {
        object obj = Session.Save(entity);
        Flush();
        return obj;
        }

public virtual object Update(T entity)
        {
        Session.Evict(entity);
        Session.Merge(entity);
        Flush();
        return 1;
        }

public virtual void Delete(T entity)
        {
        Session.Delete(entity);
        Flush();
        }

public virtual void Copy(T source, T target)
        {
        var metadata = Session.SessionFactory.GetClassMetadata(typeof(T));
        var values = metadata.GetPropertyValues(source, EntityMode.Poco);
        metadata.SetPropertyValues(target, values, EntityMode.Poco);
        }

public virtual void Flush()
        {
        Session.Flush();
        }

public virtual int Count(Expression<Func<T, bool>> predicate)
        {
        return Fetch(predicate).Count();
        }

public virtual IQueryable<T> Fetch(Expression<Func<T, bool>> predicate)
        {
        return Table.Where(predicate);
        }

public virtual IQueryable<T> Fetch(Expression<Func<T, bool>> predicate, Action<Orderable<T>> order)
        {
        var orderable = new Orderable<T>(Fetch(predicate));
        order(orderable);
        return orderable.Queryable;
        }

public virtual IQueryable<T> Fetch(Expression<Func<T, bool>> predicate, Action<Orderable<T>> order, int skip,
        int count)
        {
        return Fetch(predicate, order).Skip(skip).Take(count);
        }

public IList QueryBySQL(string sql)
        {
        return Session.CreateSQLQuery(sql).List();
        }

public IList QueryBySQL(string sql, params object[] values)
        {
        ISQLQuery query = Session.CreateSQLQuery(sql);
        for (int i = 0; i < values.Length; i++)
        {
        query.SetParameter(i, values[i]);
        }
        return query.List();
        }

public IList QueryBySQL(string sql, Action<ISQLQuery> action)
        {
        ISQLQuery query = Session.CreateSQLQuery(sql);
        action(query);
        return query.List();
        }
        }
        }
