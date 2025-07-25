package com.tekon.my_class; // 在maven创建的项目中, package的声明从'com.'的位置开始 (决不能以java开头! 这不合法 XD)

import java.io.Serializable;

public class Gun implements Serializable{

  // 不知道怎么搞的, fastjson2里面存的成员变量名前面会'多一个 _ ', 导致和这里定义的成员变量不一致, 导致拿不到反序列化对象的数据...
  // 根据小道消息, 我们准备换成Gson重新进行实验
  
  // @JSONField(name = "_gun_name")
  private String gun_name;
  // @JSONField(name = "_ammo_type")
  private String ammo_type;
  // @JSONField(name = "_gun_id")
  private transient int gun_id; // 在使用第三方序列化库时, transient关键字'可能'会失效 (Gson不失效, fastjson2会无视transient)

  public Gun(String gun_name, String ammo_type, int gun_id){
    this.gun_name = gun_name;
    this.ammo_type = ammo_type;
    this.gun_id = gun_id;
    System.out.println("已成功创建枪支: " + this.gun_name +", 弹药类型: " + this.ammo_type + ", 枪序列号: " + this.gun_id);
  }

  public String get_gun_name(){
    return this.gun_name;
  }

  public String get_ammo_type(){
    return this.ammo_type;
  }

  public int get_gun_id(){ // 用第三方序列化库的时候, 该项可能可以正常获取 (fastjson无视transient关键字, 但是Gson会老实一点 XD)
    return this.gun_id;
  }

}
