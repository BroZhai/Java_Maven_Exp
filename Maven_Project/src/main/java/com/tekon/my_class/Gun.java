package com.tekon.my_class; // 在maven创建的项目中, package的声明从'com.'的位置开始 (决不能以java开头! 这不合法 XD)

import java.lang.Math;
import java.util.ArrayList;

// import Fun_Programs.Russian_Roulette.Shell;

public class Gun {

  private String gun_name;
  private String ammo_type;
  private transient int gun_id;

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

  public int get_gun_id(){ // 在序列化对象后会失效, 因为gun_id 为transient
    return this.gun_id;
  }

}
