# View, Layout 기초

- View : 화면의 보이는 사각형 영역
  - ImageView
  - TextView
  - Button
  - ViewGroup (작은 뷰들을 담고 있는 큰 뷰)
- ViewGroup : 내부의 작은 View들을 담고 있는 View
  - 작은 뷰 : ViewGroup의 Children
  - 큰 뷰 : ViewGroup의 Parent
- Layout : 사용자가 화면을 보게될 디자인 (View, ViewGroup으로 이루어짐)

### XML

- Hierachy 구조로 Item은 여러개의 Child와 Parent를 가질 수 있다.
- 안드로이드에서 Layout을 작성시 사용

### Attributes

- id
- width
- height
- padding
- margin
- background
- visiblity

### LayoutParams

XML에서 Layout의 파라미터 값 (parent에게 child를 어떻게 배치할 지 알려주는 것임)

- layout_gravity와 gravity
  - layout_gravity : 부모에게 TextView를 어디에 둘지 알려주는 것
  - gravity : TextView의 Text를 어디에 둘지 알려주는 것

## dp, sp, px

> 안드로이드는 기기별로 px 크기가 다르기 때문에 dp, sp를 사용하게 되었다.
>
> px은 거의 안씀

- DPI (Dots Per Inch - 1인치 당 픽셀 수)

  > 요즘 나오는 핸드폰 해상도는 아마 xxxhdpi이다.

- dp (Density Indipendent Pixel) : 화면 밀도가 다른 디바이스에서 동일한 크기로 보여지도록 측정한 단위
  - px = dp * (dpi / 160)

- sp (Scaled Pixel) : 폰트 크기를 위한 단위
  - 기본적으로 dp와 동일한 크기나, 사용자의 폰트 크기 설정에 따라 크기가 변함

## Layout

ViewGroup 이 Frame, Relative, Constraint, LinearLayout 등이 있다.

### FrameLayout 

하나의 아이템을 담기 위해 고안됨

- 여러 child를 가질 수 있으나 view가 순서대로 겹쳐짐 (child 위치를 지정해주긴 어렵다..!)

**  `wrap_content` : View의 크기에 따라 변함 `match_parent` : ParentView 크기와 동일하게

<br>

### LinearLayout

한쪽 방향으로 정렬시키는 레이아웃 (수직 | 수평)

- ***android:orientation = "vertical | horizontal"***
- Child View는 크기와 상관없이 한 줄을 차지한다.

***Q. 모든 child의 크기를 동일하게 하고싶다면!?***

모든 child weight 크기 1, `vertical`의 경우  `height = 0dp` , `horizontal`의 경우 `width = 0dp`

<br>

### RelativeLayout

viewgroup 안의 view들의 상대적인 위치를 지정한다.

parent 영역에서의 상대적 위치를 잡을 수 있는 viewgroup

<br>

### ConstraintLayout

크고 복잡한 레이아웃을 단순한 계층구조를 이용하여 표현할 수 있는 ViewGroup

형제 View 와의 관계를 레이아웃을 구성함

- Child view간의 관계를 정의할 수 있다.
- LinearLayout 을 써야만 했던 뷰의 비율 정의를 가능
- view 계층을 간단하게 유지할 수 있어 성능이 좋고 유지보수가 좋다.

<br>

![스크린샷 2021-07-09 오후 4.42.04](../image/constraintLayout.png)

`androidx.constraintLayout.widget.ConstraintLayout` 안드로이드 SDK 하위 버전에서도 호환 가능하게 도와줌

** `match_parent` 대신 **0dp** (match_constraint)을 이용하자 (성능 개선)

***Q. start, end와 left, right 의 차이***

​	Start : left와 동일해보이지만 , 아랍처럼 특별한 경우 right -> left 등 디바이스 환경에 따라 start 위치가 달라진다.

​	** **대부분 start와 end를 사용한다.**

`layout_constraintHorizontal(Vertical)_bias` :  0~1 사이의 값으로, 비율에 따라 위치를 설정할 수 있다.

- Dimension - percent : 부모의 크기에 차지하게 될 크기를 지정

  ~~~xml
  andorid:layout_width = 0dp
  app:layout_constraintWidth_default = "percent"
  app:layout_constraintWidth_percent = "0.7"
  ~~~

- Dimensions-Ratio : 가로, 세로의 비율로 크기 지정

  - 적어도 한 방향은 0dp로 지정한다.

  - 명시적으로 비율을 지정할 축을 지정할 수 있다.

    ~~~xml
    app:layout_constraintDimensionRatio = "W, 16:9"
    ~~~

- Chain : 가로 세로 축의 여러 뷰들을 연결하여 그룹과 같이 동작하도록 구현

  - **가장 좌측, 상단에 위치한 view가 head가 된다.**(head 에 설정된 속성이 chain의 속성)

  - Chain style

    ![스크린샷 2021-07-09 오후 4.56.36](../image/chainstyle.png)

- **Barrier : 여러 뷰의 가장자리에 위치하는 가상의 뷰**

  > view의 크기에 따라 달라질 수 있으므로, 참조할 id 명과 barrier의 위치를 설정할 수 있다.

  ![스크린샷 2021-07-09 오후 4.59.49](../image/barrier.png)

  ~~~xml
  <andoridx.constraintlayout.widget.Barrier
  	android:id = "@+id/barrier"
  	android:layout_width="wrap_content"
  	android:layout_height="wrap_content"
  	android:barrierDirection="end"
  	app:constraint_referenced_ids="id,pw"/>
  ~~~

- **Group : 여러 뷰의  visiblity를 컨트롤하는 클래스**

  - 하나 뷰가 여러 그룹에 속한 경우, 마지막에 정의된 그룹의 visibility 를 따른다.

  ~~~xml
  <androidx.constraintlayout.widget.Group
  	android:visibility="visible"
  	app:constraint_referenced_ids="button4, button9"/>
  ~~~

- Guideline : 가로축, 세로축 방향의 가상의 뷰 (부모 뷰의 특정 위치를 기준점으로 삼을 때 사용)

  > 특정 뷰에만 padding을 줘야할 때, guideline을 이용하여 사용할 수 있다.

  - begin : 왼쪽, 위쪽으로 부터의 고정 거리
  - end : 오른쪽, 아래쪽으로 부터의 고정 거리
  - percent : 너비 혹은 높이의 퍼센트

  ~~~xml
  <androidx.constraintlayout.widget.Guideline
  	android:orientation="vertical | horizontal"
  	app:layout_constraintGuid_begin = "100dp"
  	app:layout_constraintGuid_end = "100dp"
  	app:layout_constraintGuid_percent = "100dp"/>
  	
  ~~~

- Placeholder : 기존 뷰를 위치시킬 수 있는 가상의 뷰

  `placeholder.setContentId(R.id.~~~)`

<br>

** View의 계층 구조는 앱 성능의 영향을 미치므로 간단한 뷰를 최대한 적게 사용하자

계층 구조는 flat하게 , view와 view group의 중첩은 최대한 적게

<br>

## layout-and-measure-(draw)

- measure 

  top-down 형식으로 measure 메소드가 호출되며, 각 view들은 크기 정보를 하위 view에게 넘겨준다.

  measure가 끝날 때는 모든 view들이 자신의 크기를 갖는다.

- layout

  measure가 끝난 뒤, 불리며 top-down 형식이다. 각 parent가 measure에서 측정한 값으로 child의 위치를 지정한다.

***-> 이 과정에서 measure는 여러번 불릴 수 있다.*** 

> parent보다 child의 크기가 더 크게 될 경우, 다시 계산하도록 measure가 불리게 된다.

** ***Double Texation (layout을 여러번 불리는 경우)***

- RelativeLayout에서 view의 위치가 다른 view에 따라 달라지는 경우
- LinearLayout이 horizontal인 경우
- Vertical LinearLayout이 measureWithLargestChild를 사용할 경우
- GridLayout에 weight를 주거나 gravitiy를 사용하는 경우

*-> Layout이 변경되는 경우  measure, layout이 항상 불리게 되므로 신경쓰자*

<br>

>  Constraint 항상 옳은 것은 아니다 하지만 개별 View보다  ConstraintLayout으로 성능이 좋을 수 있다.



### LayouTip

- xml - tools : editor에서만 적용되고, 실제로는 적용되지 않음

- 개발자 옵션 show layout bounds : 영역을 볼 수 있음

- Tool - layoutinspect

- 다른 앱 layout 구조 궁금할 때, **UI AnimatorViewer** 로 확인할 수 있다 (*UI Test 시 사용*)

  

