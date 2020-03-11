package mx.com.nmp.escenariosdinamicos.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum DiaDosEnum {

    _0("0"),
    
    X("X"),
    
    S("S"),
    
    M("M"),
    
    B("B");

    private String value;

    DiaDosEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static DiaDosEnum fromValue(String text) {
      for (DiaDosEnum b : DiaDosEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

}
