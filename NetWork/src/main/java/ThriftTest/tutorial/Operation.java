/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package ThriftTest.tutorial;


/**
 * 还可以定义enum类型，也就是32bit的整形.
 * 值是可选项，如果没有定义，默认从1开始，C风格
 */
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.13.0)", date = "2019-11-08")
public enum Operation implements org.apache.thrift.TEnum {
  ADD(1),
  SUBTRACT(2),
  MULTIPLY(3),
  DIVIDE(4);

  private final int value;

  private Operation(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  @org.apache.thrift.annotation.Nullable
  public static Operation findByValue(int value) { 
    switch (value) {
      case 1:
        return ADD;
      case 2:
        return SUBTRACT;
      case 3:
        return MULTIPLY;
      case 4:
        return DIVIDE;
      default:
        return null;
    }
  }
}
