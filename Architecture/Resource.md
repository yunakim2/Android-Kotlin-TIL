# Resource의 체계와 사용법

## Resource

### aapt2

모든 리소스 ID는 aapt2(Android Asset Packaging Tool) 도구가 자동으로 생성한다.

- Android Studio 및 Android Gradle Plugin 이 앱의 리소스를 컴파일하고 패키징하는 데 사용하는 빌드 도구
- 리소스를 Android 플랫폼에 최적화된 바이너리 형식으로 파싱하고 색인화한다.
- 앱이 컴파일시, aapt가 모든 리소스 아이디가 들어있는 `R.class`를 생성한다.

<br>

 ### R.class

- 코드에서 : `R.<resource_type>.<resource_name>`

- xml에서 : `@string/resource_name`

<br>

### Inflator

> 부풀게하다.
>
> 레이아웃 인플레이터를 이용하여 inflator하면 레이아웃 인플레이터가 알고있는 구문으로 뷰 객체를 생성한다.

- XML 파일을 읽어 약속된 구문으로 파싱한 뒤 객체로 만드는 과정
- 리소스를 읽어오는 원리

![스크린샷 2021-07-11 오후 6.28.12](../image/resourceInflator.png)

<br>

### androidx.annotation

> 안드로이드 코드 내부에서는 리소스를 정수로 다루고, 컴파일 타임에서 타입을 체크한다.
>
> 이때, annotation으로 어떤 타입을 사용하는 지 명시하면 오류를 예방할 수 있다.

~~~kotlin
private fun showToast(@StringRes val messageRes) {
	....
} 
~~~

<br>

### 대체리소스

> 기기의 구성에 따라 알맞는 리소스를 보여줘야한다.
>
> ex) 여러 언어 지원하는 앱, 화면 해상도 별 이미지 크기, 태블릿 전용 ui

- `res/<resource_name>-<config_qualifier>` 형식으로 디렉토리 생성
  - `<resource_name>`: 기본 리소스 디렉토리 이름
  - `<config_qualifier>` : 리소스를 사용할 개별 구성을 지정하는 이름

- 여러 구성 한정자를 같이 사용하는 경우네느 우선순위에 따라 리소스가 정해진다.

#### Q. 태블릿 화면에서 확대 된 화면을 구성하고 싶은 경우 - `values-sw<N>dp`

​	ex) 최소 너미가 600dp 인 경우 늘려서 보여주려면?! -  `values-sw600dp`

​	이때 values의 dimen과 values-sw600dp의 dimen  id를 동일하게 작성하면 적용한다.

#### Q. v26 버전에서는 해상도에 상관없이 보여주고, 나머지 버전은 해상도에 따라 이미지를 제공하려면 - `drawable-<config_qualifier>`

​	ex) `res/drawable-anydpi-v26`을 이용하면 v26버전에서만 해상도 상관없이 보여주고, 나머지는 해상도에 따라 이미지를 제공한다.

<br>

## Configuration Change

> 구성 변경이 발생할 때 (가로모드 전환, 시스템 폰트 변경, 등)
>
> Activity Config을 설정한 경우, **현재 상태를 유지하고 변경된 부분만 바꾼다.** (변경된 구성을 받아 직접 처리한다.)
>
> 설정하지 않은 경우에는 **다시 만든다.** (재생성시, 현재 구성에 맞는 디렉토리를 선택한다.)

 ### onConfigurationChanged()

- Application, Service, ContentProvider, Activity, Fragment, View 등 에서 오버라이딩 가능한 callback

   api 

- 인자로 변경된 Config가 넘어온다.

## Screen Density

> 동일한 크기의 화면에서 다른 px 수를 가질 수 있으므로, px 단위는 사용하지 않는다.
>
> 대신, 화면 밀도가 고려된 **dp** 를 사용한다.

- dpi (Dots Per Inch) : 1 inch에 들어가 있는 픽셀의 수, 안드로이드는 160dpi가 기준

- dp 또는 dip(Density Independent Pixels) : 밀도가 다른 화면에서 가시적 크기를 유지하기 위한 측정 단위

  - dp = px * (dpi / 160)

    ![스크린샷 2021-07-11 오후 6.50.52](../image/dp.png)

- sp (Scale Independent Pixels) : Text의 크기에 쓰이는 단위로, dp와 동일하나 시스템 글꼴 크기에 비례하여 늘어남

<br>

#### 화면 밀도 분류

- **nodpi** : 모든 밀도를 위한 리소스, 밀도에 의존하지 않는 자원들로 현재 화면의 밀도에 관계없이 한정자로 태그가 지정된 리소스를 확장하지 않음
- **tvdpi** : mdpi 와 hdpi 사이의 화면에 대한 리소스, 약 213dpi (텔레비전용으로 만들어진 것이므로, 대부분의 앱은 필요X)
- **anydpi** : 모든 화면 밀도에 일치하고, 다른 한정자보다 우선시 됨 (벡터 drawable에 유용), API 21에 추가됨

#### 화면 밀도 우선순위

> 현재 기기 dpi 기준으로 확대/축소가 가장 적합한 dpi로 우선 순위가 정해진다. (anydpi는 우선순위가 가장 높다.)

![스크린샷 2021-07-11 오후 6.55.25](../image/densitypriority.png)

<br>

## Theme / Style

- res/values
- `<style>`태그 사용
- style, theme 상관없이 대부분 넣을 수 있고, 컴파일까지 되기 때문에 혼동해서 사용하기 쉽다!

### Style

> **Single View의 모양을 지정하는 속성 모음 (글꼴, 글꼴 크기, 배경색)**
>
> 하나의 특정 View 또는 ViewGroup에만 적용된다.
>
> (ViewGroup에 적용한다고 ViewGroup의 자식  View에 까지 적용되는 것은 아님!!)

- key : View Attributes `<item>`
- value : Resources

### Theme

> 앱 전체, Activity, View 계층에 적용되는 스타일
>
> Status bar 및 Window background 같은 non-view 요소에도 적용 가능

- key : Theme Attributes `<attr>` (android:windowBackground 같이 window 배경 뷰에 관한 것도 정의할 수 있다.)
- value : Resource / **?attr** ( 현재 테마의 속성값을 가져올 수 있다!)

** ViewGroup의 theme를 적용 시 모두 적용된다.

** Activity에 적용시 하위 계층에 모두 적용된다. (Context에 따라 동작한다.)

- Application Context 로 리소스를 가져올 시 테마가 정상적으로 적용되지 않을 수 있다. 
- *Activity마다 Context가 존재하므로*

** Theme를 상속하여 정의하는 경우, **하위 테마 속성이 우선됨**

#### Q. 중복 Theme 사용시, 테마를 중첩해서 사용할 수 없을까?! (ThemeOverlay)

![스크린샷 2021-07-11 오후 7.08.50](../image/themeoverlay.png)

> ThemeOveraly를 적용하려면 부모가 없는 theme를 정의하면 된다.
>
> `<style>`의 parent속성을 ""로 지정하자 

** 주의 ⚠️  Context를 **ContextThemeWrapper로 래핑한 상태여야 ThemeOverlay가 사용가능하다!**

***Theme.AppCompat***

> 버전별 파편화된 테마의 호환성을 제공한다.
>
> ContextThemeWrapper가 내부적으로 구현되어 있다!!

<br>

### Theme vs Style

- Style
  - Map<view attribute, value>
  - 단일 View, 단일 ViewGroup에만 적용
- Theme
  - Map<theme attribute, value>
  - Context에 따라 동작한다.
  - 계층 구조를 따라 적용된다.
  - Overlay 형태로 중첩 적용이 가능하다.

<br>

> Theme와 Style은 같은 `<style>` 태그를 사용하므로 네이밍 규칙을 정해 사용하자!

- `<Style type>.<Group name>.<Sub-Group name>.<Variant name>` 
  - Style type : Theme, Widget
  - Group name : 앱 또는 모듈 이름
  - Sub-Group name : 위젯 스타일
  - Variant name : 구분 가능한 변수

  ~~~xml
  Theme.AppName.Blue
  Widget.AppName.Toolbar.Green
  ~~~

***점 표기식 형태로 사용시, 암시적 상속이 일어나서 parent를 명시하지 않아도 된다..!***

<br>

## Color

- res/values/
- `<color>` 태그 사용
- ARGB 포맷 ex) #8001579B

 ### ColorStateList

> View 상태에 따라 색상을 나타낼 수 있는 color 상태값 모음

** Color는  **res/values** 에 생성했지만,  **ColorStateList는 res/color 에 생성**해야 함!!

- `<selector>` 태그 사용
- 위에서 아래로 작성한 순서대로 적용이 된다!! (특정적인 성격이 강하면, 상단에 배치하자.)
- 성격이 약한 항목일 경우 하단에 배치하자. (가장 아래값이 default가 된다.)

####  기존 색상에 알파값을 적용하려면

- 롤리팝(API 21)부터 android:alpha 로 알파값 변조가 가능해졌다.
- 마시멜로(API 23)부터 테마 속성으로 사용할 수 있다.
- *AppCompat을 이용하면 API 14도 적용이 가능하다.*(`AppCompatResources.getColorStateList()`)

<br>

## Drawable

- res/drawable/
- 화면에 그릴 수 있는 리소스
- Android:drawable, android:icon, android:background ,등
  - **XML에 작성시 Drawable 형태로 inflate 된다!**

### BitmapDrawable

- .png, .jpg, .gif 파일
- `<bitmap>` 태그로 작성한 XML 파일

### NinePatchDrawable

** *9-patch?!*

> 이미지를 9개 이상의 섹션으로 나눠, **고정,확장,패딩,컨텐츠**가 들어갈 영역을 지정한 이미지이다.

- .9.png
- `<nine-patch>`태그로 작성한  XML파일

** background 형태로 지정하고, width, height 하나라도  **`wrap_content`**로 지정해야 동작!

### LayerDrawable

- Drawable 배열로 구성된 Drawable
  - 목록에 나열된 순서대로 그려진다.
- `<layer-list>` 태그로 작성한 XML 파일

~~~xml
<layer-list>
	<item>
  	<bitmap android:src="@drawable/android_red"
            android:gravity="center">
    </bitmap>
  </item>
</layer-list>
~~~

 ### StateListDrawable

> ColorStateList의  Drawable 버전

- View 상태에 따라 Drawable 나타 낼 수 있는 Drawable 상태 값 모음으로 **순서를 고려해서 작성해야 함!**

### TransitionDrawable

- 두개의 Drawable 리소스 간의 전환 효과를 보여줄 때 사용하는 Drawable
- `<transition>` 태그로 작성한  XML 파일

~~~xml
<transition>
	<item android:drawable="@drawable/image_expand"/>
 ....
</transition>
~~~

~~~kotlin
val transition : TransitionDrawable = ResourceCompat.getDrawable(res, R.drawable.expand_collapse,null)

transition.startTransition(1000); // 정방향
transition.reverseTransition(); // 역방향
~~~

### ShapeDrawable

- XML에 정의된 일반 Shape
- `<shape>` 태그

### AnimationDrawable

- Frame Animation
- `<animation-list>` 태그로 작성한  XML 파일
  - 너무 많은 프레임을 넣을 시, OOM(OutOfMemory) 발생할 수 있다 주의!

### CustomDrawable

- 모든 Drawable 구현체는 view의 onDraw()를 통해 그려진다.

<br>

## String

- res/values
- `<string>` 태그로 작성한  XML 파일

### String : array

- `<string-array>` 태그로 작성한 XML 파일

### String : Quantity Strings(Plurals) 복수형 처리

- `<plurals>` 태그로 작성한  XML 파일

### String : Formatting strings

- 문자열 서식 지정

~~~xml
<resource>
	<string name="welcome_message">Hello, %1$! you have %2$d new message!!</string>
</resource>
~~~

~~~kotlin
val text = getString(R.string.welcom_message,"hi", 10);
~~~

### Spannable

- 색상 및 글꼴 두께와 같이 서체 속성을 사용하여 스타일을 지정할 수 있는 텍스트 객체
- SpannableStringBuilder로 생성하고, CharSequence, Spannable 구현체

> 같은 문자열을 특정 문자열을 drawable을 여러개로 지정할 수 있다.

- android.text.style에 다양한 Span 유형이 있다.
- Custom Span도 구현가능하다.

### Syling with Annotations

- `<annotation>` 태그로 문자열 리소스 중간에 스타일이 적용 가능하다.

<br>

## Font

- res/font
- .ttf, .ttc, .otf
- `<font-family>` 태그로 작성한  XML 파일

> 오레오 (API 26) 부터 지원
>
> AppComapt을 이용하면 API 16부터 사용가능하다. 

~~~
app: 형태로 적용하여 API 레벨에 맞게 사용하자
~~~

<br>

##  Layout

- res/layout

### `<include>`

- 다른 layout 리소스를 포함시켜 재활용할 때 유용
- android:id, android:layout_height, width 정의 가능하다.

~~~xml
<include layout="@layout/view_layout"
	android:layout_width="match_parent"
	android:layout_height="200dp"/>
~~~

### `<merge>`

- 최상위 계층 ViewGroup이 아닌  **layout 리소스를 재활용시**
  - 최상위를  `<merge>` 태그로 정의하자.
- 뷰 계층 구조를 줄일 때 유용하다!
- parentTag를 이용하면 레이아웃 에디터와 프리뷰도 정상적으로 이용 가능하다

** *자기 자신이  Root 인 커스텀 뷰를 만들 때 사용한다.*

***Q. view 계층을 왜 줄여야할까!?***

> 안드로이드는 상위 계층부터 하나씩 그려나가는데, 만약 하위 계층에서 크기가 변경되거나 하면 뷰를 상위 계층부터 다시 그리게 되므로, view 계층을 줄여야한다. (ConstraintLayout을 이용하면 뷰 계층이 대부분 1계층에서 끝난다.)

ex) ConstraintLayout > ConstraintLayout 구조에서 ConstraintLayout이 2계층으로 이뤄져있으므로, merge 태그를 이용하면 하위 ConstraintLayout의 하위  View들을  상위 ConstraintLayout에서 자기 자신이 rootview가 되어 계층을 더이상 만들지 않는다.

<br>

### View는 트리를 전위 순회 하면서 처리해 나가는 구조이다!! 

> 깊이 우선  탐색인  DFS로 View를 전위 순회 하면서 처리해나간다.
>
> 그러므로 View계층을 최소화 하는 것이 좋다!!

렌더링 - measure - layout- draw 

- layout : ViewLayout에 어디에 위치할지
- measure : 크기와 위치가 어딘지
- draw : 실제로 ViewLayout에 그리는 것

<br>

## Animation

> Android Animation은 View Animation과 Property Animation으로 이뤄져있다.

- View Animation
  - Tween Animation
  - Frame Animation
- Property Animation

<br>

### View Animation

- res/**anim**
- **Tween Animation**
  - Animation 
  - `<alpha>`, `<scale>`, `<translate>`,`<rotate>`,`<set>`

- **Frame Animation** (= AnimationList , AnimationDrawable)
  - AnimationDrawable
- View 객체에만 적용이 가능하다
- ***View를 그린 위치만 수정 가능하다.***
  - 버튼을 이동하는 애니메이션을 수행할 때, 버튼을 실제로 클릭할 수 있는 위치는 변경되지 않는다!!

<br>

### Property Animation

- res/**animator**
- <u>설정 시간 동안</u> 배경색, 알파 값과 같은 대상 객체의 속성을 수정하는 XML
  - `<objectAnimator>`, `<animator>`, `<set>`, `<propertyValuesHolder>`

- 모든 객체의 속성을 애니메이션으로 보여줄 수 있다
- ***객체 자체가 실제로 수정 되는 것***

~~~xml
<set>
	<set>
    <objectAnimator></objectAnimator>
  </set>
  
  <objecAnimator>
 	</objecAnimator>
</set>
~~~

** `<set>` 은 android:ordering이라는 속성을 통해 set안의 Animator들을 같이 적용할 것인지 설정할 수 있음

#### ObjectAnimator

- ObjectAnimator는 ValueAnimator를 부모로 갖는다!!

- Start 값 부터 End 값 까지 duration동안 변화하는 형태
- 변경되는  **Value 값**을 받을 수 있기 때문에 여러 객체 값들을 변화시킬 수 있다!

### Interpolator

> 애니메이션의 특정 값이 시간 함수로 계산되는 방식을 정의한 일종의 함수!!

- https://easings.net/

#### ViewPropertyAnimator

- 하나의 기본 Animator를 사용하여 View의 여러 속성을 **병렬 처리로 애니메이션 시 유용**
- view.animate()로 생성
- withLayer()는 하드웨어 가속

> 안드로이드는 화면을 계속 다시 그리는 속성이 있으므로 withLayer()를 사용하면 
>
> view에게 layer를 주고, 전체를 그리지 않고  layer만 다시 그리게 된다!!

<br>

~~~kotlin
onAnimtionStart() {
	setLayerType(LAYER_TYPE_HARDWARE)
}

onAnimationEnd() {
	setLayerType(LAYER_TYPE_SOFTWARE)
}
~~~



