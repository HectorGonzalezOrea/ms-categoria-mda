package mx.com.nmp.escenariosdinamicos.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum DiaUnoEnum {
	_0("0"),
    
    X("X"),
    
    S("S"),
    
    M("M"),
    
    B("B");

    private String value;

    DiaUnoEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static DiaUnoEnum fromValue(String text) {
      for (DiaUnoEnum b : DiaUnoEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
