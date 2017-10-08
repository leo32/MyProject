package Dao;

/**
 * Created by Administrator on 2017/10/9.
 */
public interface Isys_buttonService : IServiceBase<sys_button>, IDependency
        {
        }

public class sys_buttonService : ServiceBase<sys_button>, Isys_buttonService
        {
private readonly IRepository<sys_button> _sys_buttonRepository;
public sys_buttonService(IRepository<sys_button> sys_buttonRepository)
        : base(sys_buttonRepository)
        {
        _sys_buttonRepository = sys_buttonRepository;
        }
        }
