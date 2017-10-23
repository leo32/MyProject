package entity;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/10/21.
 */
@Entity
@Table
public class SysRole {

    public String RoleCode;
    public String RoleSeq;
    public String RoleName;
    public String Description;
    public String CreatePerson;
    public Timestamp CreateDate;
    public String UpdatePerson;
    public Timestamp UpdateDate;
}