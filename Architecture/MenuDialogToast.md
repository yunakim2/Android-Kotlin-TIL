# Menu, Dialog, Toast의 사용방법

## Menu

화면의 옵션들의 모음

ex) 정보 검색, 저장, 수정 등 액션이나 화면 이동에 관한 것

#### 메뉴 종류

- 옵션 메뉴

  > 앱바라고 불리는 화면 상단에 보이는 메뉴

- 컨텍스트 메뉴

  > 리스트 중 특정 리스트에 관한 메뉴

- 컨텍스처 액션 바

  > 앱바를 오버레이하여 선택된 아이템에 대한 액션 아이템의 메뉴
  >
  > *컨텍스처와 유사하나, 적용되는 범위가 1개 이상이다*

- 팝업 메뉴

  > 특정 뷰를 기준으로 나타나는 뷰
  >
  > 옵션 메뉴와 다르게 특정 뷰를 기준으로 함

<br>

### 앱바 (App Bar)

> 화면 상단, 하단에 위치하여 앱의 타이틀, up 버튼 등을 제공한다.

- 네비게이션 아이콘
- 타이틀
- 메뉴 아이템
- 오버 플로우 버튼  

### 액티비티에 앱 바 추가하기

- 액션바
  - 기본 테마를 사용하는 Activity 의 기본 앱바
  - 버전에 따라 다르게 동작함! ( 옵션 메뉴 사용시, 툴바를 이용 하여 앱바를 구성하자 ) 

-> Empty Activity 템플릿 사용시 액션바를 제공한다.

- 툴바

  - v7 appcompat 라이브러리 제공

    (커스텀 하기 쉽다 , 거의 모든 디바이스에서 동작한다)

-> Basic Activity 템플릿 사용시 Toolbar + 옵션 메뉴를 제공한다.

<br>

### *액션 바 대신 툴바를 사용하려면?!* 

1. style.xml 에 NoActionBar 테마를 추가하자 

2. 액티비티의 NoActionBar 테마를 지정

3. 해당 액티비티 레이아웃에 toolbar를 추가

   > 앱바 레이아웃 안에 툴바를 선언하여 추가해야함!
   >
   > CoordinatorLayout, AppBar Layout 사용시 AppBar는 CoordinatorLayout의 직계자식이여야하고, ToolBar는 AppBar의 직계자식이여야 한다..!

4. toolbar를 onCreate() 에서 기본 액션바로 지정한다.

<br>

### 옵션 바를 추가하자

1. 메뉴 리소스 파일을 생성

   1. resource/menu 를 추가

   2. menu resource 파일에 `<menu>` 태그 밑에 `<item>`을 지정하자

      `app:showAsAction` 

      always : 항상 앱바에 표시

      ifRoom : 앱바에 공간이 있으면 표시

      never : overflow 메뉴에 노출

      withText : Text와 함께 아이콘이 노출 (overflow 메뉴에서는 항상 텍스트 노출)

2. onCreateOptionMenu 를 오버라이드 하여 만든 메뉴를 inflate 한다.
3. onOptionItemSelected를 오버라이드 하여 메뉴의 아이템마다 어떤 작업을 할지 구현해준다.

<br>

### Context Menu

> 리스트 뷰, 리사이클러 뷰, 그리드 뷰 등 컬렉션 뷰에서 아이템에 대한 메뉴 제공을 위해 사용된다.

1. Context menu

   > floating 형태로 제공되며, 하나의 아이템에 적용

2. Contextual action bar 

   > 앱바를 덮고 나타나며 하나 이상에 적용

####  Context Menu 구현하기

1. 메뉴 xml 생성

2. registerForContextMenu를 이용하여 view 등록

3. Activity의 onCreateContextMenu를 오버라이드하여 item을 inflate

   > Context menu가 등록된 뷰에 long-click 발생시, 시스템은 onCreateContextMenu를 호출한다.

4. Activity의 onContextItemSelected를 오버라이드하여 item의 액션을 처리

<br>

#### Contextual Action Bar 구현하기

> View를 long - click 시 액션 바 영역에 나타나게 된다.
>
> Contextual Action bar는 Done / edit / share / delete 버튼이 존재하며, 
>
> 하단 View는 Contextual Action bar를 트리거 할 View 이다.

1. 메뉴 xml 생성 *Contextual Action bar는 다크가 표준이여서, 되도록 밝은색 아이콘을 사용하자*

2. setOnLongClickListener 를 설정하고 Contextual Action Bar를 트리거한다.

   startActionMode 메서드 호출함 *(mActionMode가 널인지 아닌지 여부를 확인하고 ActionMode를 활성화하자!)*

3. ActionMode.Callback 인터페이스를 구현하여 ActionMode Lifecycle을 처리한다.

   onCreateActionMode 메소드 - 메뉴  inflate

   onActionItemClick 포함

4. Menu item 액션 메소드를 구현한다.

   *ActionMode.Callback의 onActionItemClicked 구현*

<br>

** `ActionMode`

- UI 일부를 덮고 대체 UI를 제공한다.

  ex) 텍스트를 롱클릭시 나타나는 텍스트 영역 

- Action Mode가 enable 되어 있는 경우, 앱의 구현에 따라 하나 또는 그 이상의 아이템을 선택할 수 있다!!
- 선택된 아이템들을 해지하거나 Action 스크린을 계속 탐색할 수 있다.
- Action Mode가 disable 되는 경우 *비활성화*
  - 모든 아이템 해지
  - Back 버튼 클릭
  - Done 버튼 클릭

** ActionMode 의 Callback 구현

- onPrepareActionMode : ActionMode가 발생시마다 호출되어 onCreateActionMode 뒤에 호출

  > ActionMode가 변경되거나 menu가 변경될 때 return값이 true이다.

- onDestoryActionMode : Menu에 할당된 자원 회수

  > mActionMode = null // 

#### *Context Menu와 Contextual Action Bar 뭐가 다를까?*

> Context Menu는 화면 노출시 Activity의 다른 인터렉션이 불가능하다!
>
> 하지만 Contextual Action Bar는 Activity와 계속 인터렉션이 가능하다!

<br>

### Popup Menu

- Anchor view를 기준으로 세로 리스트로 메뉴를 보여준다.

  Anchor view의 위/아래 공간 여부로 나타날 위치를 정한다.

- 옵션 메뉴와 유사하게 옵션이 많을 때, 몇개는 상단에 보여주고, 나머지는 팝업으로 보여준다.

*** Context Menu와 차이점*

> ContextMenu와 다르게 button에 붙어있고, 일반적으로 View 컨텐츠에 영향을 미치지 않는다.
>
> ex) Gmail 앱에서 popupmenu는 overflow에서 볼 수 있다. 이때, Reply, ReplyAll, Forward와 같이 메일 메시지와 연관된 아이템을 갖고 있으며 각 아이템은 메시지에 영향을 끼치지 않는다..!

#### Popup Menu 구현

1. 메뉴 xml 생성
2. 액티비티 layout에 popup menu anchor가 될 ImageButton 추가
3. ImageButton의 onClickListener 설정
4. onClick 메서드에서 popup menu를 inflate하고, PopupMenu.OnMenuItemClickListener를 설정한다.
5. onMenuItemClick 메소드를 구현
6. Popup menu 아이템이 선택되었을 때, 액션을 구현한다.

<br>

## Dialog

> 디스플레이 상단에 나타나거나 디스플레이를 덮으면서 보여지는 것으로 Activity의 흐름을 가로챈다.

Dialog의 유형

- AlertDialog (정보 알리기)
- Picker (날짜 선택)

### AlertDialog

1. 타이틀
2. 컨텐츠
3. 버튼

####  AlterDialog 구현방법

1. Builder Pattern으로 기본 제공

   > Builder Pattern 이란 디자인 패턴 중 하나로, 생성 과정이 복잡하고 어려울 때 사용되는 패턴으로 
   >
   > Builder 객체를 생성하고 원하는 옵션들을 추가해주면 된다.

2. AlertDialog.Builder 에 버튼 설정과 이벤트 처리 구현 (ex) setNegativeButton, setPositiveButton에 DialogInterface.OnClickListener를 넣어준다.

   ** 중립의 경우 *setNeutralButton* 을 통해 지정한다.

### Date & Time Picker

> 시간 날짜 등 사용자에게 정해진 포맷으로 입력받을 때 사용

- Dialog Fragment를 사용하여 구현해야한다. (Activity위에 떠있는 Window)
- Fragment도 Activity UI 또는 동작의 일부이다
- Input 메시지를 받을 수 있고, Activity 가 동작하는 동안 Add / Remove가 될 수 있다

#### Picker 구현방법

1. Dialog Fragment 구현하기
   - DialogFragment를 상속
   - DatePickerDialog.OnDateSetListener를 추가해주자
   - onCreateDialog를 오버라이드하자 (주의 ⚠️  NonNull 이므로, 반드시 Dialog를 리턴해야 한다!!)

** DatePickerDialog의 경우 초기값을 지정하고 인스턴스 생성 및 리턴이 가능하다

2. Picker 노출하기
   1. DialogFragment의 show 메소드로 노출한다.
3. Activity에서 선택된 값 처리하기
   1. Acivity에 처리할 메소드 정의하고
   2. onDateSet에서  Activity내의 정의한 메소드를 호출한다.

<br>

## Toast

> 간단한 메시지를 단방향으로 표시한다.
>
> 일정 시간이 지나면 클릭되지 않으므로 Notification을 이용하자

~~~
Toast.makeToast(context, text, duration).show()
~~~

** 위치 변경시 Gravity와 offset을 이용하면 된다

### Custom Toast View

Custom Layout을 만들어준다음 해당 Layout을 inflate하고, Toast를 설정한다.

Toast의  setView 메서드에 inflate한 layout을 넣어준다!



