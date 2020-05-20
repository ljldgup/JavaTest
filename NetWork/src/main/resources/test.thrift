/**
 * �������������ͣ�thrift֧�ֵ���������Ϊ:
 *
 *  bool        Boolean, one byte
 *  i8 (byte)   Signed 8-bit integer
 *  i16         Signed 16-bit integer
 *  i32         Signed 32-bit integer
 *  i64         Signed 64-bit integer
 *  double      64-bit floating point value
 *  string      String
 *  binary      Blob (�ֽ�����)
 *  map<t1,t2>  Map from one type to another
 *  list<t1>    һ�����͵�����list
 *  set<t1>     Set of unique elements of one type
 *
 * thrift֧��C���͵�ע��
 */
 
// thriftҲ֧����Cһ���ĵ���ע��
 
/**
 * thrift�ļ�������������thrift�ļ��԰��������Ľṹ�ͷ�����
 * ����ʹ�õ�ǰ·���ҵ�����ͨ������-l��������־ָ�����κ�·����������
 *
 * ʹ��.thrift�ļ���������Ϊǰ׺���ʰ����Ķ�������shared.SharedObject
 */
include "shared.thrift"
 
/**
 * Thrift�ļ������Ը���Ŀ�����Զ����������namespace��package��prefix
 */
namespace cpp ThriftTest.tutorial
namespace java ThriftTest.tutorial

 
/**
 * Thrift ������typedef��������������������Ҳ�Ǳ�׼��C���
 */
typedef i32 MyInteger
 
/**
 * Thrift �������㶨�����ڿ����Եĳ���. 
 * ���ӵ����ͺͽṹ�����ͨ��json��ʾ������ָ��.
 */
const i32 INT32CONSTANT = 9853
const map<string,string> MAPCONSTANT = {'hello':'world', 'goodnight':'moon'}
 
/**
 * �����Զ���enum���ͣ�Ҳ����32bit������. 
 * ֵ�ǿ�ѡ����û�ж��壬Ĭ�ϴ�1��ʼ��C���
 */
enum Operation {
  ADD = 1,
  SUBTRACT = 2,
  MULTIPLY = 3,
  DIVIDE = 4
}
 
/**
 * �ṹ���ǻ����ĸ������ݽṹ. 
 * ������ÿ������������ʶ�������ͣ��������ƺͿ�ѡȱʡֵ���ֶ����
 *
 * �ֶο��Ա�����Ϊ"optional", ��ȷ�����������δ�����õĻ���
 * �᲻�����������л�����С�
 */
struct Work {
  1: i32 num1 = 0,
  2: i32 num2,
  3: Operation op,
  4: optional string comment,
}
 
/**
 * �ṹҲ���������⣬������Ǻ����ᡣ
 */
exception InvalidOperation {
  1: i32 whatOp,
  2: string why
}
 
/**
 * ��Ҫ���ܣ�����һ��service. 
 * serviceֻ��Ҫһ�����ƣ�������ѡ��ʹ��extend�ؼ��ִ���һ������̳�
 */
service Calculator extends shared.SharedService {
 
  /**
   * method���忴������C����. �з������͡������Լ����ܻ��׳����쳣�б�
   * �����б���쳣�б�ʹ����ṹ����쳣�����е��ֶ��б���ȫ��ͬ���﷨������
   */
 
   void ping(),
 
   i32 add(1:i32 num1, 2:i32 num2),
 
   i32 calculate(1:i32 logid, 2:Work w) throws (1:InvalidOperation ouch),
 
   /**
    * ��method���е������η�. ����ζ�ſͻ���ֻ������󣬲��Ҹ�����listen�κ���Ӧ
    * ����method����void
    */
   oneway void zip()
 
}
