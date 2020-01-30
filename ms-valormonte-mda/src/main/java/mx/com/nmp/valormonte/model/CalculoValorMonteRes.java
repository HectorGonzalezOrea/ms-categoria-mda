package mx.com.nmp.valormonte.model;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;

import mx.com.nmp.valormonte.model.CalculoValorMonteResInner;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CalculoValorMonteRes
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-30T17:34:52.695Z")

public class CalculoValorMonteRes extends ArrayList<CalculoValorMonteResInner>  {

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CalculoValorMonteRes {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

