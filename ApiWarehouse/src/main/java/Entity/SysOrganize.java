package Entity;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/10/21.
 */
@Entity
@Table
public class SysOrganize {
    public String OrganizeCode;
    public String ParentCode;
    public String OrganizeSeq;
    public String OrganizeName;
    public String Description;
    public String CreatePerson;
    public Timestamp CreateDate;
    public String UpdatePerson;
    public Timestamp UpdateDate;
}
