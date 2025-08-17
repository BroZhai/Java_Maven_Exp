package com.tekon;

public class Item {
  private String name;
  private String desc;
  private int count;

  public Item(String item_name, String item_desc, int item_count){
    this.name = item_name;
    this.desc = item_desc;
    this.count = item_count;
  }

  public String get_name(){
    return this.name;
  }

  public String get_desc(){
    return this.desc;
  }

  public int get_count(){
    return this.count;
  }
  
}
