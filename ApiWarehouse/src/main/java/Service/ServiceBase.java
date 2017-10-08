package Service;

/**
 * Created by Administrator on 2017/10/9.
 */
public class ServiceBase<T> : IServiceBase<T> where T : EntityBase
        {
private readonly IRepository<T> _repository;
public ServiceBase(IRepository<T> repository)
        {
        _repository = repository;
        }
public virtual IEnumerable<T> Get()
        {
        return Table;
        }

public virtual T Get(int id)
        {
        return _repository.Get(id);
        }

public virtual IEnumerable<T> Table
        {
        get
        {
        object obj = CacheManager.Instance[this.ToString()];
        try
        {
        if (obj == null)
        {
        LogFacade.Log.Info(string.Format("Table {0}", this));
        obj = _repository.Table;
        //CacheManager.Instance.Insert(this.ToString(), obj, DateTime.Now.AddMinutes(5), CacheManager.NoSlidingExpiration);
        }
        }
        catch (Exception ex)
        {
        LogFacade.Log.Error(string.Format("System.Error {0}", ex));
        }
        return obj as IEnumerable<T>;
            }
                    }

public virtual object Create(T entity)
        {
        LogFacade.Log.Info(string.Format("Create {0}", this));
        return _repository.Create(entity);
        }

public virtual object Update(T entity)
        {
        LogFacade.Log.Info(string.Format("Update {0}", this));
        return _repository.Update(entity);
        }

public virtual void Delete(int id)
        {
        var entity = Get(id);
        if (entity != null)
        {
        _repository.Delete(entity);
        }
        }

public void Flush()
        {
        _repository.Flush();
        }

public virtual void Delete(string idList)
        {
        LogFacade.Log.Info(string.Format("Delete {0}", idList));
        foreach (string id in idList.Split(new string[] { "," }, StringSplitOptions.RemoveEmptyEntries))
        {
        Delete(int.Parse(id));
        }
        }


public virtual int Count(Expression<Func<T, bool>> predicate)
        {
        LogFacade.Log.Info(string.Format("Count {0}", predicate));
        return Fetch(predicate).Count();
        }
public virtual T Get(Expression<Func<T, bool>> predicate)
        {
        var key = this.ToString() + "_" + predicate.ToString();
        LogFacade.Log.Info(string.Format("Get {0}", key));
        var getData = CacheManager.Instance[key];
        if (getData == null)
        {
        getData = _repository.Get(predicate);
        //CacheManager.Instance.Insert(key, getData, DateTime.Now.AddMinutes(5), CacheManager.NoSlidingExpiration);
        }
        return getData as T;
        }
public virtual IEnumerable<T> Fetch(Expression<Func<T, bool>> predicate)
        {
        var key = this.ToString() + "_" + predicate.ToString();
        LogFacade.Log.Info(string.Format("Get {0}", key));
        var fetchData = CacheManager.Instance[key];
        if (fetchData == null)
        {
        fetchData = _repository.Fetch(predicate);
        //CacheManager.Instance.Insert(key, fetchData, DateTime.Now.AddMinutes(5), CacheManager.NoSlidingExpiration);
        }
        return fetchData as IEnumerable<T>;
        }
public virtual IEnumerable<T> Fetch(Expression<Func<T, bool>> predicate, Action<Orderable<T>> order)
        {
        var key = this.ToString() + "_" + predicate.ToString() + "_" + order.ToString();
        LogFacade.Log.Info(string.Format("Get {0}", key));
        var fetchData = CacheManager.Instance[key];
        if (fetchData == null)
        {
        fetchData = _repository.Fetch(predicate, order);
        //CacheManager.Instance.Insert(key, fetchData, DateTime.Now.AddMinutes(5), CacheManager.NoSlidingExpiration);
        }
        return fetchData as IEnumerable<T>;
        }
public virtual IEnumerable<T> Fetch(Expression<Func<T, bool>> predicate, Action<Orderable<T>> order, int skip, int count)
        {
        var key = this.ToString() + "_" + predicate.ToString() + "_" + order.ToString() + "_" + skip + "_" + count;
        LogFacade.Log.Info(string.Format("Get {0}", key));
        var fetchData = CacheManager.Instance[key];
        if (fetchData == null)
        {
        fetchData = _repository.Fetch(predicate, order, skip, count);
        //CacheManager.Instance.Insert(key, fetchData, DateTime.Now.AddMinutes(5), CacheManager.NoSlidingExpiration);
        }
        return fetchData as IEnumerable<T>;
        }

public virtual IList QueryBySQL(string sql)
        {
        return _repository.QueryBySQL(sql);
        }
public virtual IList QueryBySQL(string sql, params object[] values)
        {
        return _repository.QueryBySQL(sql, values);
        }

public virtual IList QueryBySQL(string sql, Action<ISQLQuery> action)
        {
        return _repository.QueryBySQL(sql, action);
        }

public virtual int Edit(JObject data)
        {
        const string DATA_TYPE_DELETED = "deleted";
        const string DATA_TYPE_UPDATED = "updated";
        const string DATA_TYPE_INSERTED = "inserted";

        const string DATA_TYPE_FORM = "form";
        const string DATA_TYPE_TABS = "tabs";
        const string DATA_TYPE_ARRAY = "Array";

        var rowsAffected = 0;
        Logger("编辑记录", () =>
        {
        var formData = data[DATA_TYPE_FORM];
        var tabsData = data[DATA_TYPE_TABS];


        //更新tabs
        if (tabsData != null)
        {
        foreach (JToken tab in tabsData.Children())
        {
        if (tab == null)
        continue;

        //判断是form类型还是grid类型
        bool IsGrid = (tab[DATA_TYPE_DELETED] != null && tab[DATA_TYPE_DELETED].Type.ToString() == DATA_TYPE_ARRAY)
        || (tab[DATA_TYPE_UPDATED] != null && tab[DATA_TYPE_UPDATED].Type.ToString() == DATA_TYPE_ARRAY)
        || (tab[DATA_TYPE_INSERTED] != null && tab[DATA_TYPE_INSERTED].Type.ToString() == DATA_TYPE_ARRAY);

        if (IsGrid)
        {
        foreach (JProperty item in tab.Children())
        {
        foreach (var row in item.Value.Children())
        {
        if (string.IsNullOrEmpty(row["ID"].ToString())) row["ID"] = "0";
        var model = (T)JsonConvert.DeserializeObject(row.ToString(), typeof(T));
        var editArgs = new EditEventArgs() { data = data, dataOld = model };

        if (tab[DATA_TYPE_DELETED].Count() > 0)
        {
        editArgs.dataAction = OptType.Del;
        var rtnBefore = this.OnBeforeEditRow(editArgs);
        this.Delete(row["ID"].ToString());
        this.OnAfterEditRow(editArgs);
        rowsAffected++;
        }
        if (tab[DATA_TYPE_UPDATED].Count() > 0)
        {
        editArgs.dataAction = OptType.Mod;
        var rtnBefore = this.OnBeforeEdit(editArgs);
        this.Update(model);
        this.OnAfterEditRow(editArgs);
        rowsAffected++;
        }
        if (tab[DATA_TYPE_INSERTED].Count() > 0)
        {
        editArgs.dataAction = OptType.Add;
        var rtnBefore = this.OnBeforeEdit(editArgs);
        this.Create(model);
        this.OnAfterEditRow(editArgs);
        rowsAffected++;
        }

        }
        }
        }
        else
        {
        //var changedFieldCount = wrapper.ToParamUpdate().GetData().Columns.Count;

        ////如果有字段被修改则更新
        //if (changedFieldCount > 0)//更新主表
        //{
        //    //到数据库中取得旧值
        //    var formOld = BuilderParse(wrapper.ToParamQuery()).QuerySingleDynamic();
        //    var strAction = (formOld == null) ? DATA_TYPE_INSERTED : DATA_TYPE_UPDATED;

        //    //事件参数
        //    editArgs.dataNew = tab;
        //    editArgs.dataOld = formOld ?? new JObject();
        //    editArgs.dataAction = actionTypes[strAction];
        //    editArgs.dataWrapper = wrapper;

        //    //Form编辑前事件
        //    rtnBefore = this.OnBeforeEditRow(editArgs);
        //    if (!rtnBefore) return;

        //    //Form数据处理
        //    wrapper.SetValue(editArgs.dataNew);
        //    editArgs.executeValue = handles[strAction](wrapper);
        //    rowsAffected += editArgs.executeValue;

        //    //把未修改的数据更新到form新值上
        //    if (editArgs.dataAction == OptType.Mod)
        //    {
        //        EachHelper.EachObjectProperty(editArgs.dataOld as object, (i, name, value) =>
        //        {
        //            if (editArgs.dataNew[name] == null)
        //                editArgs.dataNew[name] = JToken.FromObject(value ?? string.Empty);
        //        });
        //    }

        //    //Form编辑结束事件
        //    this.OnAfterEditRow(editArgs);
        //}
        }
        }
        }
        }, null);

        return rowsAffected;
        }
protected virtual bool OnBeforeEdit(EditEventArgs arg)
        {
        return true;
        }

protected virtual bool OnBeforeEditForm(EditEventArgs arg)
        {
        return true;
        }

protected virtual void OnAfterEditForm(EditEventArgs arg)
        {

        }

protected virtual bool OnBeforeEditRow(EditEventArgs arg)
        {
        return true;
        }

protected virtual void OnAfterEditRow(EditEventArgs arg)
        {

        }

protected virtual void OnAfterEdit(EditEventArgs arg)
        {

        }

protected static ILog Log = LogManager.GetLogger(String.Format("Service{0}", typeof(T).Name));

protected static void Logger(string function, Action tryHandle, Action<Exception> catchHandle = null, Action finallyHandle = null)
        {
        LogHelper.Logger(Log, function, ErrorHandle.Throw, tryHandle, catchHandle, finallyHandle);
        }

protected static void Logger(string function, ErrorHandle errorHandleType, Action tryHandle, Action<Exception> catchHandle = null, Action finallyHandle = null)
        {
        LogHelper.Logger(Log, function, errorHandleType, tryHandle, catchHandle, finallyHandle);
        }

public void Logger(string position, string target, string type, object message)
        {
        App.OperationLogger(position, target, type, message);
        }
        }
