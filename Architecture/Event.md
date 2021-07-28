# Event 전달과정의 이해와 처리방법

> 사용자의 이벤트 의미( 화면터치, 키보드 입력 )

## Input Events

View Class는 UI 이벤트를 받기위해 public callback method가 있다.

ex) 터치 이벤트 시 , onTouchEvent() 가 호출되는 데 이때 이벤트가 발생할 때마다 override 해서 사용하면 너무 복잡하다!

​	**-> event listeners 를 사용하자**

### View의 event listener들

- onClick()
- onLongClick()
- onFocusChange()
- onKey() 
- onTouch()
  - MotionEvent.getAction()
    - ACTION_DOWN : 누름
    - ACTION_MOVE : 움직임
    - ACTION_UP : 뗌
    - *ACTION_CANCEL : 상위 ViewGroup에서 가로챔*
  - MotionEvent.getX(), getY() : 터치된 영역의 위치
- onCreateContextMenu()

<br>

***boolean을 return해야하는 리스너***

- **onLongClick()** 이벤트를 소비했는지 더 이상 전파될 필요가 없는지 return
- **onKey() ** 이벤트를 소비했는지 더 이상 전파될 필요가 없는지 return
- **onTouch()** 이벤트를 소비했는지 return. ACTION_DOWN에서 false를 return할 경우, 관련된 이벤트에 관심이 없다는 뜻으로 더이상 해당 터치에 대한 action정보가 넘어 오지 않는다 !!

<br>

### Touch Event가 전달되는 과정

> 여러개의 ViewGroup이 겹쳐진 경우, onTouchEvent()의 리턴값이 false면 해당 viewgroup은 터치이벤트를 사용하지 않는다고 생각하고 그냥 넘어가버림

![스크린샷 2021-07-09 오후 5.38.40](../image/event.png)

Touch가 발생하면 Activityd 에서 View까지 순차적으로 내려가며 onInterceptTouchEvent() 반환값이 false이면 해당 ViewGroup은 넘어가게 된다. 만약 View안의 TextView의 OnTouchListener가 존재하면 해당 OnTouchListener를 수행하고, 아니면 부모 View의 onTouchEvent() 를 수행한다.

<br>

## Detect Gestures

안드로이드 많이 쓰는 터치 제스처들의 처리를 쉽게 해주는 GestureDetector가 존재한다.

- onDown
- onFing
- onLongPress
- onScroll
- onShowPress
- onSingleTapUp
- onDoubleTap
- onDoubleTapEvent
- onSingleTapConfirmed

<br>

** ActivityClass에 View.OnClickListener를 implement하여 사용할 수도 있다.
이때, 여러개의 view의 onClick들이 모두 onClick()을 통해 오므로 switch문으로 처리가 필요하다.

** 계속 화면을 누르고 있는 경우 onDown -> onShowPress -> onLongPress 호출

** 눌렀다 떼는 경우 onSingleTap -> onSingleTapUp 호출 (만약 2번째 터치가 없는 경우 -> onSingleTapConfirmed가 호출 됨)

<br>

## Multi - Touch

- index : Motion Event는 각각의 pointer정보를 array로 보관하고, pointer의 Index는 array의 위치를 의미한다.
- ID : 각각의  pointer가 터치되는 동안 변하지 않는 ID를 갖고 있으며, ID를 사용하여 각 pointer를 추적해야한다.

*ex) 손가락 두개를 터치하고 첫번째 손가락을 때게되면*

id[0] = 0 , id[1] = 1 에서 id[0] = 1로 바뀐다.

<br>

## ETC Listeners

### EditText

- **addTextChangeListener** : text 변경 시 **textWatcher**를 추가한다.

  - **beforeTextChanged** - 텍스트가 변경되기 전 호출

  - **onTextChanged** - 텍스트가 변경된 후 호출

  - **afterTextChanged** - 텍스트가 변경된 후 호출, 텍스트를 수정 시 s를 수정하면된다.

    이때, s 수정시 afterTextChanged가 다시 호출됨

- **setOnEditorActionListener** (editor enter 버튼 수정시 사용)
  - editor에서 action이 수행되면 호출된다.

### RadioGroup

- **setOnCheckedChangedListener** : 체크된 Radio 버튼이 변경되면 호출되며, checkId로 어떤 버튼이 체크되었는 지 알 수 있다.

### SeekBar

- **setOnSeekBarChangeListener**
  - **onProgressChanged** : progress 변경 시 호출
  - **onStartTrackingTouch** : 사용자가 터치 동작을 시작시 호출
  - **onStopTrackingTouch** : 사용자가 터치 동작을 종료시 호출

### AdapterView

- **setOnItemClickListener**
  - adapterView의 Item Click 시 호출
  - 선택된 Item의 data 접근 필요시 adpaterView.getItemAtPosition(position)
- **setOnItemLongClickListener**
- **setOnItemSelectedListener**
  - **onItemSelected** : 이전 선택된 아이템과 다른 아이템이 선택되면 호출됨
  - **onNothingSelected** : 선택된 아이템이 없어질때 호출됨

<br>

## View 접근하기 

### findViewById

현재는 권장하지 않는다!

### ButterKnife

ViewBinding으로 대체 하는 게 좋다..!

### DataBinding

xml에서 맨 상단에 에  `<layout>`으로 명시해주면 databinding을 사용하겠다는 의미가 된다.

~~~kotlin
val databinding : ActivityHomeBinding = DataBindingUtil.inflate(inflator, R.layout.activity_home, flase)

databinding.title.text = "안녕하세요"
~~~

** databinding은 xml에서 연산을 하기 위해 만들어진 형태

### ViewBinding

databinding과 다르게 java, kotlin 코드에서 view를 접근하기 위해 만들어진 형태로 findViewById 문제점을 해결하기 위해 나옴

~~~kotlin
val viewBinding : ActivityHomeBinding = ActivityHomeBinding.inflate(inflater)
viewBinding.title.text = "안녕하세요"
~~~

<br>

** **findViewById는 해당 Activity나 Fragment의 view id 값이 아닌 동일한 다른 view id 값으로 호출해도 컴파일 타임에는 오류가 나지 않는다 (런타임시 에러가 발생) -> 그래서 ViewBinding을 사용해야한다!**

![스크린샷 2021-07-09 오후 6.26.47](../image/viewAccess.png)

<br>

