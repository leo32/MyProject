package entity;
import javax.persistence.*;
import java.sql.Timestamp;
/**
 * Created by Administrator on 2017/10/21.
 */
@Entity
@Table
public class SysCodeType {
    public String CodeType;
    public String CodeTypeName;
    public String Description;
    public String Seq;
    public String CreatePerson;
    public Timestamp CreateDate;
    public String UpdatePerson;
    public Timestamp UpdateDate;
}
