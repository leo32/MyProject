package entity;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/10/21.
 */
@Entity
@Table
public class SysParameter {
    public String ParamCode;
    public String ParamValue;
    public String IsUserEditable;
    public String Description;
    public String CreatePerson;
    public Timestamp CreateDate;
    public String UpdatePerson;
    public Timestamp UpdateDate;
}