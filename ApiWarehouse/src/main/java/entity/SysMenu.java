package entity;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/10/21.
 */
@Entity
@Table
public class SysMenu {
    public String MenuCode;
    public String ParentCode;
    public String MenuName;
    public String URL;
    public String IconClass;
    public String IconURL;
    public String MenuSeq;
    public String Description;
    public String IsVisible;
    public String IsEnable;
    public String CreatePerson;
    public Timestamp CreateDate;
    public String UpdatePerson;
    public Timestamp UpdateDate;
    public String ParentName;
}