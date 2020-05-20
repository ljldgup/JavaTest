namespace cpp ThriftTest.shared
namespace java ThriftTest.shared

 
struct SharedStruct {
  1: i32 key
  2: string value
}
 
service SharedService {
  SharedStruct getStruct(1: i32 key)
}
