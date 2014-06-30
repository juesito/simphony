/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.pojos;

/**
 *
 * @author ga25rciaj
 */
public class ComboBox {

  private String key;
  private String name;

  public ComboBox() {}

  public ComboBox( String key, String name ) {
    setKey( key );
    setName( name );
  }

  public String getKey() { return key; }
  public void setKey(String key) { this.key = key; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  @Override
  public String toString() { return name; }

  @Override
  public boolean equals(Object obj) {

    if( !( obj instanceof ComboBox ) ) {
      return false;
    }
    return key.equals( ((ComboBox)obj).key );
  }

  @Override
  public int hashCode() {
    return key.hashCode();
  }

}
