package Entity;
import javax.persistence.*;
/**
 * Created by Administrator on 2017/10/21.
 */
@Entity
@Table
public class SysMenuButtonMap {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer ID;
    public String MenuCode;
    public String ButtonCode;
}