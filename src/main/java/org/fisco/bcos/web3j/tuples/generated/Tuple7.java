package org.fisco.bcos.web3j.tuples.generated;

import org.fisco.bcos.web3j.tuples.Tuple;

/**
 * Auto generated code.
 *
 * <p><strong>Do not modifiy!</strong>
 *
 * <p>Please use TupleGenerator in the <a
 * href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 */
public final class Tuple7<T1, T2, T3, T4, T5, T6, T7> implements Tuple {
  private static final int SIZE = 7;

  private final T1 value1;

  private final T2 value2;

  private final T3 value3;

  private final T4 value4;

  private final T5 value5;

  private final T6 value6;

  private final T7 value7;

  public Tuple7(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7) {
    this.value1 = value1;
    this.value2 = value2;
    this.value3 = value3;
    this.value4 = value4;
    this.value5 = value5;
    this.value6 = value6;
    this.value7 = value7;
  }

  public T1 getValue1() {
    return value1;
  }

  public T2 getValue2() {
    return value2;
  }

  public T3 getValue3() {
    return value3;
  }

  public T4 getValue4() {
    return value4;
  }

  public T5 getValue5() {
    return value5;
  }

  public T6 getValue6() {
    return value6;
  }

  public T7 getValue7() {
    return value7;
  }

  @Override
  public int getSize() {
    return SIZE;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tuple7<?, ?, ?, ?, ?, ?, ?> tuple7 = (Tuple7<?, ?, ?, ?, ?, ?, ?>) o;
    if (value1 != null ? !value1.equals(tuple7.value1) : tuple7.value1 != null) {
      return false;
    }
    if (value2 != null ? !value2.equals(tuple7.value2) : tuple7.value2 != null) {
      return false;
    }
    if (value3 != null ? !value3.equals(tuple7.value3) : tuple7.value3 != null) {
      return false;
    }
    if (value4 != null ? !value4.equals(tuple7.value4) : tuple7.value4 != null) {
      return false;
    }
    if (value5 != null ? !value5.equals(tuple7.value5) : tuple7.value5 != null) {
      return false;
    }
    if (value6 != null ? !value6.equals(tuple7.value6) : tuple7.value6 != null) {
      return false;
    }
    return value7 != null ? value7.equals(tuple7.value7) : tuple7.value7 == null;
  }

  @Override
  public int hashCode() {
    int result = value1.hashCode();
    result = 31 * result + (value2 != null ? value2.hashCode() : 0);
    result = 31 * result + (value3 != null ? value3.hashCode() : 0);
    result = 31 * result + (value4 != null ? value4.hashCode() : 0);
    result = 31 * result + (value5 != null ? value5.hashCode() : 0);
    result = 31 * result + (value6 != null ? value6.hashCode() : 0);
    result = 31 * result + (value7 != null ? value7.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Tuple7{"
        + "value1="
        + value1
        + ", value2="
        + value2
        + ", value3="
        + value3
        + ", value4="
        + value4
        + ", value5="
        + value5
        + ", value6="
        + value6
        + ", value7="
        + value7
        + "}";
  }
}
