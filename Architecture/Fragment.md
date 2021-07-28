# Fragment

## Fragment란?

- Honeycomb API 11 에 도입
  - 현재 androidx.fragment.app.Fragment
  - 플랫폼 코드 -> support 라이브러리 -> androidx 로 리패키징
- FragmentActivity 내에 동작
- 하나의 Activity에 여러 개의 Fragment를 표현
- 다른 액티비티에 재사용 할 수 있는 '하위 액티비티' 의미
- 자체적인 생명주기 존재

<br>

## Fragment 왜 사용하는 걸까?

- Framgent는 처음 부터 일종의 Micro Activity로 설계 되었다. 즉, Activity가 할 수 있는 모든 것은 Fragment 도 가능하다!
- BackStack
- View State
- Lots of API Surface
  - ActionBar Menus
  - Context Menus
- Lots of custom hooks into Activity
  - onActivityResult
  - Permissons
  - Multi Window
  - Picture in Picture

-> Activity와 다양한 상호작용을 할 수 있도록 View를 래핑한다.

<br>

## Fragment 사용방법

### onCreateView()

- Fragment Layout XML의 Inflate 가 일어남

- inflate 한  root view를 반환
- *View를 직접 생성하여 반환하는 것도 가능하다*

### onViewCreated()

- onCreateView가 반환한 root view가 인자로 넘어오게 된다 -> **view가 완전한 경우에만 onViewCreated가 호출된다!**
- 해당 인자를 통해 하위 뷰 초기화를 진행한다.
- 실제 초기화 로직을 가장 많이 작성하는 곳

### Activity에 붙이기

- XML 정의

  ~~~xml
  <!--fragment 접근 시 필요 id , tag-->
   <!-- 무조건 명시해줘야 한다!! name -->
  <fragment
       android:id = "" 
       android:tag = "" 
       android:name = "com.naver.MyFragment" <!-- 무조건 명시해줘야 한다!! -->
  />
  
  ~~~

  - `tools:context` : Attatch 된 Activity의 테마 기반으로 프리뷰
  - `tools:layout` : Layout 기반으로 프리뷰

  

- FragmentManager 사용하기

  - Activity 내에서 Fragment와 상호 작용하기 위한 인터페이스

  - Activity 의 getSupportFragmentManager() 로 제어한다.

    > getFragmentManger()는 현재 deprecated된 상태
    >
    > Support prefix는 과거 Support 패키지였을 때 붙은 네이밍이다.

  - **findFragmentId(),** **findFragmentByTag()** 를  통해 Fragment 인스턴스를 획득할 수 있다!

  ~~~kotlin
  val fragmentById = getSupportFragmentManager().findFragmentID(R.id.my_fragment)
  ~~~

  - fragment를 생성후, **FragmentTransaction**을 이용하여 Activity에 붙이자

  ~~~kotlin
  getSupportFragmentManager().beginTransaction()
  	.add(R.id.fragment_container, MyFragment(), "my_fragment")
  	.commit()
  
  val myFragmentContainer = getSupportFragmentManger().findFragmentById(R.id.fragment_container)
  // ViewGroup의 가장 최상위 Fragment를 반환한다.
  ~~~

## Fragment 생성 시 주의할 점

- 반드시 Default 생성자만 사용하자! (인자가 있는 생성자를 정의하여 초기화 X)
- 인자가 필요할 때는 **setArgument(Bundle args)**를 이용하여 **Bundle**형태로 주입하자
- ***다른 형태로 주입시, Fragment 재생성시 복구할 수 없다***

** 많이 사용하는 패턴

~~~kotlin
class BlankFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
~~~

<br>

## FragmentManager

###  FragmentTransaction

- Fragment에 대한 제어를 트랜잭션 개념으로 추상화 한 것
  - 여러가지 변경을 원자적으로 수행하며 변경한 내용을 되돌릴 수 없다!
- **Fragment 생성, 소멸, 가시성을 제어한다.**
  - ViewGroup 하위에 add, remove, visibility 동작
- **Fragment 생성, 소멸, 가시성 동작에 대한 시점을 제어한다.**
  - add() / remove() , replace() 들이 수행되는 시점
- BackStack을 제어한다.
- Fragment 전환에 대한 애니메이션을 처리한다.
- **여러 동작을 하나의 트랜잭션으로 처리 가능하다.**

~~~kotlin
supportFragmentManager.beginTransaction()
  .add(R.id.top_container, TopFragment(), "top_frag")
  .add(R.id.left_container, LeftFragment(), "left_frag")
  .replace(R.id.bottom_container, BottomFragment(), "bottom_frag")
  .commit()
~~~

- **FragmentManager 내부에서 Fragment State를 List로 관리한다.**
  - *각 동작에 따라 Fragment와  Fragment가 보유한  ViewGroup의 제어가 달라진다.*

- add() / remove()
  - Fragment, ViewGroup에 추가 / 제거
- **replace()**
  - **remove() 후 add() 유사**

** FragmentMagnager에 Fragment가 이미 있는 경우 

- show() / hide()
  - Fragment 유지 , ViewGroup에 보이기/ 안보이기
- attach() / detach()
  - Fragment 유지 , ViewGroup에 추가 / 제거

- **BackStack 존재 유무에 따라 동작이 달라진다.**
  - BackStack이 존재할 경우 , Fragment인스턴스는 유지되며 ViewGroup에 대한 동작만 발생한다.
  - BackStack에서 완전히 Pop 되는 경우, Fragment가 소멸된다.

- **Fragment Transaction은 commit() 후 비로소 동작한다!**
  - commit() / commitNow()
  - commitAllowingStateLoss() / commitNowAllowingStateLoss()

<br>

### Commit 과 CommitNow 차이는?!

- commit()

  - **비동기적으로 처리**

    > 복잡한 뷰의 경우 동시다발적으로 동기처리시, 과부화 발생한다.

  - 트랜잭션이 즉시 동작하지 않고, 스케줄링에 따라 적절한 시점에 동작한다!

- commitNow()

  - **동기적으로 처리**
  - 처리를 보장한다.

<br>

### executePendingTransactions()

- getSupportFragmentManager().executePendingTransactions()
  - 동기적으로 처리
  - 처리를 보장하지 않는다. 반환 값에 따라 처리 가능 여부를 판단한다.
  - 리턴값으로 처리가 완료시 True, 실패시 False를 줌

<br>

### FragmentTransaction : stateLoss()

> fragment의 상태를 저장하는 것

- ***onSaveInstanceState() 호출 후, commit() 호출 시 런타임 익셉션 발생한다.***
  - onPause() or onStop() 호출 이후
- 비동기 처리가 많은 안드로이드에서는 예측이 어렵다

### FragmentTransaction : commitAllowingStateLoss()

> Fragment의 상태를 저장하는 방법, 런타임 익셉션 예방

- **commitAllowingStateLoss(), commitNowAllowingStateLoss()** 로 해결 가능하다.
- 단 상태 복구는 보장되지 않는다!!

<br>

## Backstack

### addToBackStack()

- fragment 트랜잭션 단위로 스택에 PUSH 된다.
- fragment 트랜잭션의 히스토리가 쌓이는 것과 동일
- ***인자로 null을 넣어줘도 스택에 쌓이지만 back stack entry로 탐색이 불가능하다.***

 ** 안드로이드  Back 키를 누르면 기본적으로 POP이 구현되어 있다!! 그래서 따로 POP은 없는 것

### onBackPressed()

- Single Backstack은 별도의 처리 없이 동작가능하다.

### popBackStack()

- 기본적으로 비동기 처리
  - 동기처리시 ***popBackStackImmediate()***
- 상태가 유효하지 않으면, 런타임 익셉션 발생시킨다
  - `supportFragmentManager.isStateSaved()`로 체크가 필요하다.
- popBackStack() 인자에 따라 pop 시킬 범위를 지정한다.

~~~kotlin
// 1개씩 POP
supportFragmentManager.popBackStack()
// Back Stack Entry 까지 POP
supportFragmentManager.popBackStack("my_fragment_back_stack", 0)
// Back Stack Entry 까지 포함하여 POP
supportFragmentManager.popBackStack("my_fragment_back_stack",FragmentManager.POP_BACK_STACK_INCLUSIVE)
~~~

### OnBackPressedDispatcher

> androidx 부터 Back key 처리가 OnBackPressedDispatcher로 위임되었다!!

- callback을 등록하고 적절한 callback을 추가하며 핸들링 가능하다

<br>

### BackStack 동작방식

- **POP 시킬시 트랜잭션은 반대로 일어난다.**
  - add() <-> remove()
  - attach() <-> detach()
  - show() <-> hide()
  - replace() // remove() -> add()

<br>

## Child Fragment

> 개념적으로 존재하는 것으로 별도의 클래스가 존재하지는 않는다.

- Fragment에서 getChildFragmentManager()로 트랜잭션을 수행하여  Attach된 Fragment를 의미한다.

### getChildFragmentManger()

- Fragment마다 별도의 FragmentManager가 존재한다.

- 각각의 다른  backstack을 보유하고 있어, 주로 multiple back stack 구현시 사용한다.

- Activity 의 supportFragmentManager

  == Parent Fragment의 getFragmentManger

  == Chile Fragment의 getParentFragment.getFragmetnManager

- Parent Fragment의 getChildFragmentManager()

  == Child Fragment의 getFragmentManager()

<br>

## Fragment의  LifeCycle

![fragmentLifeCycle](../image/fragmentLifeCycle.png)

![스크린샷 2021-07-12 오전 12.53.36](../image/fragmentandactivity.png)

<br>

- FragmentActivity에는  Fragment 관련 Callback이 더 있다!!
  - **onAttachFragment(Fragment fragment)**
    - Fragment가 attach될때 호출된다.
    - Attach된 Fragment의 **onAttach() 이후에 호출된다.**
    - 인자로 Attach된 Fragment가 넘어온다.
  - **onResumeFragments()**
    - Activity에 Attach된 모든 Fragment의 **onResume() 호출 이후에 호출된다.**

<br>

![스크린샷 2021-07-12 오전 12.56.51](../image/fragmentAttach.png)

<br>

![스크린샷 2021-07-12 오전 12.58.03](../image/fragmentAttach2.png)

<br>

### onAttach() / onDetach()

- Fragment가 Activity에 추가/삭제 되었을 때 불린다.
- **getContext() / getActivity() 가 null 이 아니다!**
  - **onAttach() 호출**시, ***getContext() / getActivity() 가 null 이 아니다!***
  - **onDetach() 호출**시, getContext() / getActivity() 가 null 
- 주의 ⚠️ FragmentTransaction의 attach와 detach와는 아무 관련 없다
  - FragmentTransaction : attach (toView)
  - Fragment : onAttach (toActivity)

### onCreateView() / onDestoryView()

- 뷰 계층에 추가 / 삭제 시 호출됨
  - **Fragment 트랜잭션으로 attach(), detach()를 수행하는 경우**
  - onCreate() , onDestory() 가 호출되지 않음
  - 뷰 계층에서 삭제되고, **프레그먼트 상태는 유지된다.**
- **Fragment는 2개의 LifeCycle이 존재한다고 할 수 있다!!** (Fragment의 LifeCycle, View의 LifeCycle)

### onViewCreated() , onActivityCreated()

- onViewCreated()
  - onCreateView 에서 반환 직후 호출됨
- **onActivityCreated()** 
  - Activity의 onCreate() 호출 이후에 호출됨

---

- onCreate() / onDestory()

  - Fragment의 생성 / 소멸과 관련

- onCreateView() / onDestoryView()

  - Fragment가 보유한 View 생성 / 소멸과 관련

- 등록과 해지가 필요한 로직 구현시

  - FragmentLifecycle != ViewLifecycle을 생각하자!!

    > NPE, Memory Leak을 유발할 수 있다.
    >
    > 이런 경우  온전하게 쌓인 onStart(), onStop()에 넣어주기도 한다.

<br>

## State

> 현재 보고 있었던 화면의 상태를 유지하는 것
>
> Ex)  백그라운드 상태에 오래 있다가 앱으로 복귀하는 경우, 작성하던 텍스트나 특정 스크롤의 위치,등등등

### onSaveInstanceState(), onViewStateRestored()

- **setArgument(Bundle bundle) 인자로 주입한 경우 재 생성시 복구된다.**
  - getArguments()로 획득할 수 있다.
- onSaveInstanceState()
  - 현재 상태를  Bundle 형태로 저장한다.
  - **onStop(), onPause() 상태가 되는 상황에서 호출된다.**
  - 플랫폼 버전에 따라 시점이 다르다!!
- onViewStateRestored()
  - 이전 상태를 Bundle에서 복구한다.
- onActivityCreated(), onCreateView(), onViewCreated()
  - 저장된 State 가 Bundle 인자로 넘어오게 된다.

![스크린샷 2021-07-12 오전 1.12.49](../image/fragmentStateLifecycle.png)

<br>

### setRetainInstance()

> ViewModel로 필요없어짐..

- setRetainInstance(true)시 Activity 재생성될 때 fragment의 인스턴스를 보존한다.
- **지정된  Fragment는 BackStack에 들어갈 수없다.**
- 복잡한 상태 복구 시 사용
- Activity 재생성시 , **Fragment의 onCreate()/ onDestory() 호출이 생략된다.**

<br>

## DialogFragment

- Fragment 를 Dialog 스타일로 보여준다.
- Fragment 를 확장한 클래스로 기본 동작은  Fragment와 유사하다
- AppCompat 테마를 사용하는 경우
  - AppCompatDialogFragment를 사용해야한다.
- onCreateView(), onCreateDialog() 를 선택해서 구현할 수 있다!!

### onCreateDialog()

- 내부적으로  Dialog의 인스턴스를 보유한다.
  - NON-UI Fragment : View가 없고, 로직만 존재하는 프레그먼트
  - **onCreateView, onDestoryView는 여전히 호출된다.**
- **Dialog를 FragmentLifecycle과 매칭하여 동작시킨다.**
  - onStart () -> dialog.show()
  - onStop() -> dialog.hide()
  - onDestoryView() -> dialog.dismiss()

 ### onCreateView()

- onCreateView로 구현하는 경우,  Fragment와 동일하다
- View 계층에 포함된다.

- 제공되는 API show (), dismiss() 로 제어한다.
  - 내부적으로는 Fragment 트랜잭션을 수행한다.

<br>

## Fragment의  Communicate

### Fragment 와  Activity

- interface로 넘겨주고 callback 형태로 처리하는 경우 -> XXX
  - 초기에는 문제없이 동작하는 것처럼 보이지만, 재 생성 시나리오시 <u>해당 인스턴스가 복구되지 못한다.</u>
  - BG에 오래 머물다가 앱 실행시, 클릭이 안되는 Dialog가 보임

#### **대안법 1.  onAttachFragment()**

onAttachFragment가 Fragment가 Attach 될 때 마다 호출되므로, 여기에 리스너를 넣어주자.

#### **대안법 2. getActivity()**

** getActivity 란 ? Fragment에서 Activity에 접근할 수 있도록  제공되는 api

detach 된 경우  null 이 되므로 null 체크가 필요하다(`getActivity().isDestoryed()`)

NonNull을 확신할 경우 , **requireActivity()**를 통해 이용하면 된다. (Ex) Activity가 무조건  NonNull 인 onResume에서 사용하면 되지만 주의가 필요하다.

#### 대안법 3.  startActivityForResults()

> Fragment에서도 startActivity(), startActivityForResult()가 존재한다

- onActivityResult() 가 프레그먼트로 넘어온다.

- 단, Fragment의 구현된 startActivityForResult() 를 사용해야한다.
  - Fragment의 startActivityForResult()
    - onActivityResult() 정상 호출됨
  - Fragment 내부에서 getActivity().startActivityForResult()
    - Activity 의 onActivityResult()가 호출 됨

<br>

### Fragment 와  Fragment

> Parent Fragment <-> ChildFragment

#### onAttachFragment()

- getChildFragmentManager 로 트랜잭션 수행 시 호출
- 인자로  Child Fragment가 넘어온다.
  - 해당 인자를 이용하여  Parent Fragment에 인스턴스를 주입한다!!

####  getParentFragmet()

- null, fragment.isAdd() 상태 체크 필요

#### findByFragmentByTag()

> 서로 다른 Fragment Manager에 존재하는 경우, Activity를 경유해 전달한다.

- Tag를 통해  Activity에서  Fragment를 찾아서 전달

#### setTargetFragment

> 서로 같은 Fragment Manager에 존재하는 경우에만 사용가능하다.

- Fragment간의 startActivityForResult()라고 할 수 있다!!

<br>

## Transition

> Fragment의 전환 처리

### Transitions with shared elements

1. 공유 요소들의 같은  Transition Name 설정
2. 전환하려는  Fragment에 SharedElementTransition 설정
3. FragmentTransaction에 이전  Fragment의 공유 요소와 TransitionName을 설정

### Shared elements : Optimization

- Fragment의 무거운 작업 존재시 화면 전환이 매끄럽지 않게 일어난다.
- 무거운 작업이 모두 끝날 때 까지 Transition을 의도적으로 지연시키는 방법
  - postponeEnterTransition()
    - 지연 시킬 시점에 호출 (보통 onCreateView 에서 View가  inflate 되기 전 호출 시킨다.)
    - 인자로  TimeOut은 가급적 걸어주는 게 좋다
  - startPostponedEnterTransition()
    - 지연을 끝낼 시점에 호출 (뷰가 다 만들어졌거나 공유 요소에 필요한 데이터를 가져온 시점)
  - setReorderingAllowed()
    - FragmentTransaction을 최적화한다. (중복을 제거, 더 나은 순서로 변경)
    - postponeEnterTransition() 의 정상적인 동작을 기대하려면 호출해야 한다.









