# Data Type

## 자료형 (Number Type)

- Byte(byte)

- Short(short)

- Int(int)

- Long(long)

- Float(float)

- Double(double)

- **Any(Object)** 

  return 값이 하나는 String, 하나는 Int 등 자료형이 다른 경우 -> Any 타입

- **Unit(void)** : **자료형이기도 하고, 값이기도 하다!**

** **자바의 Wrapper 타입은 자료형에 `?`을 붙여주면 된다!**

<br>

## String

~~~kotlin
val str1 = "Hello Kotlin ${Date().year}"

val s = "ABC"
val str2 = "$s.length * 2 = ${s.length*2}"

val str3 = String.format("pi = %.2f",pi)
val str4 = "pi = %.2f".format(pi)
~~~

<br>

## Type Cast, Type Alias

- `as`  : 명시적 캐스팅
- `is`  = (`instance of`) 타입이 같은지 확인후 코드 절에서 캐스팅이 된다 (`when`절)

** `var`의 경우는 변수이기 때문에 캐스팅이 되지 않는다!

<br>

## Array

> Primitive 타입의 변수들은 다르게 배열을 정의해놓았다.

| 자바      | 코틀린       | 초기화 함수                    |
| --------- | ------------ | ------------------------------ |
| boolean[] | BooleanArray | booleanArrayOf, BooleanArray() |
| byte[]    | ByteArray    | byteArrayOf, ByteArray()       |
| char[]    | CharArray    | charArrayOf, CharArray()       |
| int[]     | IntArray     | intArrayOf, IntArray()         |
| long[]    | LongArray    | longArrayOf, LongArray()       |
| float[]   | FloatArray   | floatArrayOf, FloatArray()     |
| double[]  | DoubleArray  | doubleArrayOf, DoubleArray()   |

~~~kotlin
val arr1 : IntArray = intArrayOf(0,0,0,0,0)
val arr2 : Array<Int> = arrayOf(24,3,25,36,5,0)
val arr3 : Array<Int> = Array<Int>(6,{0,}) // 0,0,0,0,0,0
val arr4 : Array<Int> = Array(5, {i -> (i*i)}) // 0,1,4,9,16
val arr5 = Array<String>(5,{i -> (i*i).toString()}) // '0','1','4','9','16'
~~~

<br>

## Collection

> 자바의 Collection와 유사하지만 , mutable, immutable 로 두가지의 종류로 나눠져있다 

| 컬렉션 타입 | 읽기 전용 (Immutable) | 가변형 (Mutable)                       |
| ----------- | --------------------- | -------------------------------------- |
| `List<T>`   | listOf()              | arrayListOf, mutableListOf             |
| `Map<K,V>`  | mapOf                 | mutableMapOf, linkedMapOf, sortedMapOf |
| `Set<E>`    | setOf()               | mutableSetOf, linkedSetOf, sortedSetOf |

~~~kotlin
fun main() {
    val strList : List<String> = mutableListOf()
    val strList2 : List<String> = List<String>(10, {index ->  ""})
    var strList3 : List<String?> = MutableList<String?>(10,{index -> null})

    val fruitMap = hashMapOf<String, Int>("Apple" to 10, "Cherry" to 20, "Pear" to 30)

    val numInitSet = mutableSetOf<Int>(1,2,3)
    val numSet = mutableSetOf<Int>()   
}
~~~

