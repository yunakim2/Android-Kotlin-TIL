# Flow Control

## Conditional Flow Control

> 조건문 안에서는 변수를 할당할 수 없다!
>
> 코드블럭은 리턴을 갖는다

~~~kotlin
val message = if (view.visibility != View.VISIBLE) {
	view.text = "Hello"
} else {
	view.visibility = View.VISIBLE
	view.text = "Hi"
}
~~~

** ***message 객체는 Unit 일까?*** 

맞다. view.text는 void 이므로 Unit이므로 message는 Unit타입이 된다.

<br>

### When (= Switch 문)

~~~kotlin
when (view.visibility) {
	View.VISIBLE -> toast("visible")
	else -> toast("invisible")
}

// Auto - Casting
when (view) {
	 is TextView -> toast(view.text)
	is RecyclerView -> toast("Item Count = ${view.adapter.itemCount}")
	else -> toast("View Type Not Support")
}

// Without argument
val res = when {
  	x in 1 .. 10 -> "cheap"
  	s.contain("hello") -> "it's a welcome!"
  	v is ViewGroup -> "child count : ${v.getChildCount()}"
  	else -> ""
}
~~~

- `when` 절에서 `is`를 사용하면 자동으로 스마트 캐스팅이 된다!

  -> 캐스팅을 한번더 해야하는 번거로움이 사라짐

- `when`절 사용시 argument를 사용하지 않는 경우에는 else를 통해 모든 경우에 성립할 수 있도록 해야한다.

<br>

## Loop Control

> 자바의 for는 if의 연장선 -> 비효율적

### for, forEach, repeat

- `for` : for 문과 동일 
  - `Collection` `Item` 전체를 볼 경우 , `in` 뒤에 `Collection` 변수명을 입력해주면 된다. => `forEach`와 동일
  - `Collection`의 `Size`만큼 `indexing` 할 경우, list.indices 를 통해 `Collection` 크기만큼 `for`문을 반복
  - `0 .. N` 는 0 ~ N 까지,  `0 util N` 는 0 ~ N-1 까지
  - **`List`를 `index`와 같이 보려면 `list.withIndex()` 이용하자.**

~~~kotlin
val list = arrayOf("Hello", "World", "!")

for (str in list) {
  println(str)
}

for (index in list.indices) {
  println(list[index])
}

// 0 ~ 9 까지
for (i in 0..9) {
  println(i)
}
//  0 ~ 9 까지
for (i in 0 until 10) {
  println(i)
}

list.forEach {
  println(it.toString())
}

repeat(10) {
  println(it.toString())
}

var map = hashMapOf(1 to "apple" , 2 to "pie", 3 to "mellon")
for ((key, value) in map) {
  println("map[$key] = $value")
}
for ((index, element) in list.withIndex()) {
  println("$element at index $index")
}
~~~

<br>

### step , downTo, label

- `for` : `step` , `downTo`
- `label` : `continue`와 같은 의미

~~~kotlin
for(i in 0 .. 9 step 2) {
	print(i)
} // 02468
println()
for(i in 0 until 10 step 2) {
	print(i)
} // 02468
println()
for (i in 9 downTo 0) {
	print(i)
} // 9876543210
~~~

~~~kotlin
loop1@for(i in 'A' .. 'Z') {
        loop2@for(j in 'a' .. 'b'){
            println("i $i , j $j")
            if (i == 'B') {
                break@loop1
            }
        }
    }

'''
i A , j a
i A , j b
i B , j a
'''
~~~

<br>

## Range Type

> `in`을 이용하여 사용하고, 포함되지 않을 경우는 `!in`

