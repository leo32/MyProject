package entity;
import javax.persistence.*;
/**
 * Created by Administrator on 2017/10/21.
 */
@Entity
@Table
public class SysRoleMenuColumnMap {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer ID;
    public String RoleCode;
    public String MenuCode;
    public Boolean IsReject;
    public String FieldName;
}
