package entity;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/10/21.
 */
@Entity
@Table
public class SysUser {

    public String UserCode;
    public String UserSeq;
    public String UserName;
    public String Description;
    public String Password;
    public String RoleName;
    public String OrganizeName;
    public String ConfigJSON;
    //public String Database ;
    public String IsEnable;
    public Integer LoginCount;
    public Timestamp LastLoginDate;
    public String CreatePerson;
    public Timestamp CreateDate;
    public String UpdatePerson;
    public Timestamp UpdateDate;
}
