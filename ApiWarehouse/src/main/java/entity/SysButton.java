package entity;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/10/21.
 */
@Entity
@Table
public class SysButton {
    public String ButtonCode;
    public String ButtonName;
    public Integer ButtonSeq;
    public String Description;
    public String ButtonIcon;
    public String CreatePerson;
    public Timestamp CreateDate;
    public String UpdatePerson;
    public Timestamp UpdateDate;

}
