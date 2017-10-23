package entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/10/21.
 */
@Entity
@Table
public class SysUserSetting {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer ID;
    public String UserCode;
    public String SettingCode;
    public String SettingName;
    public String SettingValue;
    public String Description;
}