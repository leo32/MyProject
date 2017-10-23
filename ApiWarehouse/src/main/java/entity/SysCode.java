package entity;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/10/21.
 */
@Entity
@Table
public class SysCode {
    public String Code;
    public String Value;
    public String Text;
    public String ParentCode;
    public String Seq;
    public String IsEnable;
    public String IsDefault;
    public String Description;
    public String CodeTypeName;
    public String CodeType;
    public String CreatePerson;
    public Timestamp CreateDate;
    public String UpdatePerson;
    public Timestamp UpdateDate;
}
