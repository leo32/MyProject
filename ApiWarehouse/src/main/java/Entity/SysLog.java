package Entity;
import javax.persistence.*;
import java.sql.Timestamp;
/**
 * Created by Administrator on 2017/10/21.
 */
@Entity
@Table
public class SysLog {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer ID;
    public String UserCode;
    public String UserName;
    public String Position;
    public String Target;
    public String Type;
    public String Message;
    public Timestamp Date;
}
