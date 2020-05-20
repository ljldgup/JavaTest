/**
 * 首先是数据类型，thrift支持的数据类型为:
 *
 *  bool        Boolean, one byte
 *  i8 (byte)   Signed 8-bit integer
 *  i16         Signed 16-bit integer
 *  i32         Signed 32-bit integer
 *  i64         Signed 64-bit integer
 *  double      64-bit floating point value
 *  string      String
 *  binary      Blob (字节数组)
 *  map<t1,t2>  Map from one type to another
 *  list<t1>    一种类型的有序list
 *  set<t1>     Set of unique elements of one type
 *
 * thrift支持C类型的注释
 */
 
// thrift也支持像C一样的单行注释
 
/**
 * thrift文件可以引用其他thrift文件以包含常见的结构和服务定义
 * 可以使用当前路径找到，或通过搜索-l编译器标志指定的任何路径进行搜索
 *
 * 使用.thrift文件的名称作为前缀访问包含的对象，例如shared.SharedObject
 */
include "shared.thrift"
 
/**
 * Thrift文件可以以各种目标语言对其输出进行namespace、package或prefix
 */
namespace cpp ThriftTest.tutorial
namespace java ThriftTest.tutorial

 
/**
 * Thrift 可以做typedef来给你的类型起别名，这也是标准的C风格
 */
typedef i32 MyInteger
 
/**
 * Thrift 还允许你定义用于跨语言的常量. 
 * 复杂的类型和结构体可以通过json表示法进行指定.
 */
const i32 INT32CONSTANT = 9853
const map<string,string> MAPCONSTANT = {'hello':'world', 'goodnight':'moon'}
 
/**
 * 还可以定义enum类型，也就是32bit的整形. 
 * 值是可选项，如果没有定义，默认从1开始，C风格
 */
enum Operation {
  ADD = 1,
  SUBTRACT = 2,
  MULTIPLY = 3,
  DIVIDE = 4
}
 
/**
 * 结构体是基本的复杂数据结构. 
 * 它们由每个具有整数标识符，类型，符号名称和可选缺省值的字段组成
 *
 * 字段可以被声明为"optional", 这确保了如果它们未被设置的话，
 * 会不被包括在序列化输出中。
 */
struct Work {
  1: i32 num1 = 0,
  2: i32 num2,
  3: Operation op,
  4: optional string comment,
}
 
/**
 * 结构也可以是例外，如果它们很讨厌。
 */
exception InvalidOperation {
  1: i32 whatOp,
  2: string why
}
 
/**
 * 重要性能，定义一个service. 
 * service只需要一个名称，并可以选择使用extend关键字从另一个服务继承
 */
service Calculator extends shared.SharedService {
 
  /**
   * method定义看起来像C代码. 有返回类型、参数以及可能会抛出的异常列表
   * 参数列表和异常列表使用与结构体或异常定义中的字段列表完全相同的语法来定义
   */
 
   void ping(),
 
   i32 add(1:i32 num1, 2:i32 num2),
 
   i32 calculate(1:i32 logid, 2:Work w) throws (1:InvalidOperation ouch),
 
   /**
    * 此method具有单向修饰符. 这意味着客户端只提出请求，并且根本不listen任何响应
    * 单向method必须void
    */
   oneway void zip()
 
}
