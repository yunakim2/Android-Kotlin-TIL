# Kotlin - CodeLab1

- 문장에서 가장 빈번하게 등장하는 단어 4개 볼드 처리
  - Step 1 : 문단을 라인으로 쪼갠다.
  - Step 2 : 라인을 공백으로 구분하여 단어를 쪼갠다.
  - Step 3 : 단어를 map에 넣고 개수를 카운트 한다.
  - Step 4 : map에 단어를 빈도를 세고 랭킹을 만든다.
  - Step 5 : 문장을 볼드 처리하여 재구성한다.



~~~kotlin
  val sentence = """안녕하세요. 안녕하십니까. 안녕~. 안녕안녕. 안녕 안녕 안녕"""

  val wordCountMap = sentence.split(".")?.flatMap { it.split(" ")}
  	.filter { it.isNotBlank() }.map{ it.trim(' ', '\r','\n','\t',',') }
  	.groupingBy { it }.eachCount()
  	.toList().sortedBy { (_, count) -> -count }.slice(0 .. 4).toMap()
  wordCountMap.forEach{ (w,c) ->
  	println("$w = $c")
  }
~~~

- `split('.')` : '.'로 쪼갠다.
- `flatMap` : iterable한 객체의 원소들을 하나씩 flatMap 코드블럭을 수행하고 하나의 iterable 객체로 반환한다.
- `filter` : 해당 iterable 객체에서 filter 코드블럭에 해당하는 결과들만 반환한다.
- `map` : iterable 객체의 각각의 요소를 하나씩 순회하여 조작한 결과를 리턴한다. (하나의  iterable 객체로 반환하지 않음 1:1 매핑)
- `groupingBy` : 리스트의 원소에 두 값을 묶어 `Map<Key,Value>` 타입으로 변환
- `eachCount` : 그루핑된 것의 개수를 세어줌 
- `toList()` : List로 변환 -> 해당 코드에서는 정렬을 하기 위해 list로 반환함! (sortedBy는 list만 사용할 수 있음)
- `sortedBy` : `groupingBy` 로 나온 `<Key, Value>` 값 중 `Value`(=`count`) 를 기준으로 내림차순(`-`) 정렬함 
- `slice(0 .. N)` : 0 ~ N 번째 원소까지 자름
- `toMap()` : Map으로 변환
- `forEach` : iterable 객체의 원소들을 하나하나를 순회한다.

---

## Map 과 FlatMap

### Map

- 반복 가능한 배열 대상으로 배열의 각각의 요소를 하나하나 순회하여 요소를 조작하고 조작한 요소가 모인 **배열을 리턴**

~~~kotlin
val testList = listOf("A","B","C")
val newList = testList.map {
  	"$it!"
}
println(newList) // ['A!','B!','C!']
~~~

### FlatMap

- map처럼 배열을 리턴한다.
- map은 1:1 매핑이지만, flatMap은 1:1 뿐만 아니라 1: 다 매핑이 가능하다.
- flatMap으로 넘겨주는 함수는 무조건 **(`iterable`한 값을 리턴해야한다!!)**

** *iterable이란?* : 하나씩 반환할 수 있는 object (ex List, Str, Tuple)

~~~kotlin
val testList = listOf("A","B","C")
val newList = testList.flatMap {
  	"$it!".toList()
} // flatMap 코드블럭 안에 있는 값을 또 iterable(List)로 반환해준다.
println(newList) // ['A', '!', 'B', '!', 'C', '!']
~~~

***Q. `flatMap`은 어떻게 동작하여 다음과 같은 리턴값을 낼까?!***

-> `flatMap` 함수 호출하여 동시에 내부적으로 **`destination` 빈 배열이 자동으로 생성**된다. `flatMap` 코드블럭에서 수행된 결과값이 `addAll`() 을 통해 `destination` 배열에 덧붙여지게되고 **해당 iterable을 모두 순회한 뒤 `destination`을 리턴**한다.

<br>

## add와 addAll 차이

`add` 는 원소 하나씩을 리스트에 넣는 것이고, addAll은 iterable한 리스트 객체를 통째로 붙인다.

~~~kotlin
val list1 = listOf('A')
list1.add('B') // ['A', 'B']

val list2 = listOf('C','D')
list1.addAll(list2) // ['A','B','C','D']
~~~



