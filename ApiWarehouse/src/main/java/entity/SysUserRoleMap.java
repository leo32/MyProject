package entity;
import javax.persistence.*;
/**
 * Created by Administrator on 2017/10/21.
 */
@Entity
@Table
public class SysUserRoleMap {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer ID;
    public String UserCode;
    public String RoleCode;
}
