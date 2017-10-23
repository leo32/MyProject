package entity;
import javax.persistence.*;
/**
 * Created by Administrator on 2017/10/21.
 */
import java.sql.Timestamp;
@Entity
@Table
public class SysLoginHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer ID;
    public String UserCode;
    public String UserName;
    public String HostName;
    public String HostIP;
    public String LoginCity;
    public Timestamp LoginDate;
}
