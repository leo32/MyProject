package Entity;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/10/21.
 */
@Entity
@Table
public class SysPermission {
    public String PermissionCode;
    public String PermissionName;
    public String ParentCode;
    public String CreatePerson;
    public Timestamp CreateDate;
    public String UpdatePerson;
    public Timestamp UpdateDate;
}
