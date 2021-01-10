## CoordinatorTabLayout

coordinatorLayout + CollapsingToolbarLayout  와 TabLayout , ViewPager 응용하기
<br>

✔️[참고자료](https://github.com/CHJUNE-android/CoordinatorExercise)

<br>

<center><img height="500" alt="coordinatorLayout" src="https://user-images.githubusercontent.com/45454585/81466250-17cfa080-920b-11ea-9cd9-8c99fca9b782.gif"></center>

<br>

###  ➡️ App theme 바꿔주기

1️⃣ **res -> values -> styles.xml 에 Lab3Theme style 추가해주기**

~~~xml
 <style name="Lab3Theme" parent="Theme.AppCompat.Light.NoActionBar">
        <item name="windowActionBar">false</item>
        <item name="android:windowNoTitle">true</item>
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
~~~

<br>

2️⃣ **manifests ->AnroidManifest.xml 에** ***android:theme ="@style/Lab3Theme"*** **로 변경해주기**

~~~xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.coordinatortablayout">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Lab3Theme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
~~~

<br>

<br>

### ➡️ CoordinatorLayout & CollapsingToolbarLayout 이용해서 activity_main.xml 추가

1️⃣ **Gradle에 implementation 'com.google.android.material:material:1.0.0' 추가하기 **

(CoordinatorLayout ,TabLayout 과 ViewPager 사용하기 위해,등등)

~~~kotlin
dependencies {
   	
    implementation 'com.google.android.material:material:1.0.0'
    
}
~~~

<br>

2️⃣ **activity_main.xml 파일 작성하기**

​	✅ 참고 ( [참고자료](https://github.com/CHJUNE-android/CoordinatorExercise) )

<br>

<center><img width="515" alt="main_xml" src="https://user-images.githubusercontent.com/45454585/81466211-aa237480-920a-11ea-9a35-a207d2a0ca45.png"></center>

<br>

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.coordinatorlayout.widget.CoordinatorLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/lab3_coordinator"
        android:fitsSystemWindows="true"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent">


    <com.google.android.material.appbar.AppBarLayout
        android:id="@+id/lab3_appbar"
        android:layout_width="match_parent"
        android:layout_height="242dp"
        android:fitsSystemWindows="true"
        android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar">

        <com.google.android.material.appbar.CollapsingToolbarLayout
            android:id="@+id/lab3_collapsing"
            android:layout_width="match_parent"
            android:layout_height="242dp"
            app:contentScrim="@color/colorAccent"
            app:expandedTitleMarginBottom="50dp"
            app:expandedTitleMarginStart="20dp"
            app:layout_scrollFlags="scroll|exitUntilCollapsed">
        <ImageView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:id="@+id/lab3_appbar_image"
            android:scaleType="centerCrop"
            android:src ="@mipmap/one"
            app:layout_collapseMode="parallax"/>
        <!--parallax는 스크롤시 이동, pin은 이동하지 않음.-->
        <androidx.appcompat.widget.Toolbar
            android:id="@+id/lab3_toolbar"
            android:layout_width="match_parent"
            android:layout_height="100dp"
            android:layout_margin="8dp"
            app:layout_collapseMode="pin"/>

            <com.google.android.material.tabs.TabLayout
                android:id="@+id/tab"
                android:layout_width="match_parent"
                android:layout_height="40dp"
                app:contentScrim="?attr/colorPrimary"
                app:tabTextColor="#ffffff"
                android:layout_gravity="bottom"/>

        </com.google.android.material.appbar.CollapsingToolbarLayout>
    </com.google.android.material.appbar.AppBarLayout>

        <androidx.viewpager.widget.ViewPager
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:id="@+id/viewpager"
            app:layout_behavior="@string/appbar_scrolling_view_behavior"/>
        />
    </androidx.coordinatorlayout.widget.CoordinatorLayout>

  <!--야매로 상태바 만들어줌.-->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintTop_toTopOf="parent"
        android:layout_marginTop="30dp">

    <ImageView
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:id="@+id/img_btn"
        android:layout_marginLeft="10dp"
        android:layout_gravity="center"
        android:src="@drawable/ic_arrow_back_white_24dp"/>
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="CoordinatorTabLayout"
        android:textColor="#ffffff"
        android:textSize="20sp"
        android:textStyle="bold"
        android:layout_marginLeft="10dp"
        android:padding="10dp"/>
    </LinearLayout>
</androidx.constraintlayout.widget.ConstraintLayout>
```

<br>

### ➡️ TabLayout , ViewPager에 들어갈 Fragment 생성 및 연결 

​	✅ 만드는 방법 ([참고자료2](https://github.com/yunakim2/android_template/blob/master/TabLayout_basic/TabLayout_basic.md))

1️⃣ **OneFragment, TwoFragment, ThreeFragment 생성해준다.**

<br>

2️⃣ **각각의 Fragment에 newInstant() 함수 추가해주기**

TwoFragment, ThreeFragment 동일

~~~kotlin
 fun newInstant() : OneFragment
        {
            val args = Bundle()
            val frag = OneFragment()
            frag.arguments = args
            return frag
        }
~~~

<br>

3️⃣ **FragmentAdapter.kt 파일 생성**

~~~kotlin
package com.example.coordinatortablayout

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentAdapter ( fm : FragmentManager): FragmentPagerAdapter(fm) {
  //position 에 따라 원하는 Fragment로 이동시키기
  override fun getItem(position: Int): Fragment {
       val fragment =  when(position)
       {
           0->OneFragment().newInstant()
           1-> TwoFragment().newInstant()
           2-> ThreeFragment().newInstant()
           else -> OneFragment().newInstant()
       }
        return fragment
    }

  //tab의 개수만큼 return
    override fun getCount(): Int = 3

  //tab의 이름 fragment마다 바꾸게 하기
    override fun getPageTitle(position: Int): CharSequence? {
        val title = when(position)
        {
            0->"0ne"
            1->"Two"
            2->"Three"
            else -> "main"
        }
        return title     }
}
~~~

<br>

4️⃣ **MainActivity.kt에 TabLayout과 ViewPager 연결**

~~~kotlin
 fun initViewPager()
    {
        val pagerAdapter = FragmentAdapter(supportFragmentManager)
        val pager = findViewById<ViewPager>(R.id.viewpager)
        pager.adapter = pagerAdapter
        val tab = findViewById<TabLayout>(R.id.tab)
        tab.setupWithViewPager(pager)
    }
~~~

<br>

<br>

### ➡️ 각각에 Fragment에 RecyclerView 만들어주기

​	‼️ ***One,Two,Three Fragment 모두 동일! 아래 방식 똑같이 해주기***

<br>

1️⃣ **fragment_one.xml에 RecyclerView 추가해주기**

~~~xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".OneFragment">

    <androidx.recyclerview.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/rv_one"/>

</FrameLayout>
~~~

<br>

2️⃣ **rv_item.xml 파일 추가해주기**

(recyclerview 안에 들어갈 각각의 item)

~~~xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="hi"
        android:id="@+id/tv_recycler"
        android:textSize="16sp"
        android:textColor="#000000"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        android:padding="10dp"/>

</androidx.constraintlayout.widget.ConstraintLayout>
~~~

<br>

3️⃣ **RecyclerData.kt, RecyclerViewAdapter.kt, RecyclerViewViewHolder.kt 추가해주기**

​	✅ **RecyclerData.kt**

~~~kotlin
package com.example.coordinatortablayout.RecyclerView

data class RecyclerData (
    val string : String
)
~~~

<br>

​	✅ **RecyclerViewViewHolder.kt**

~~~kotlin
package com.example.coordinatortablayout.RecyclerView

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coordinatortablayout.R
import kotlinx.android.synthetic.main.rv_item.view.*

class RecyclerViewViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val tv = itemView.findViewById<TextView>(R.id.tv_recycler)

    fun bind(Data: RecyclerData)
    {
        tv.text = Data.string
    }
}
~~~

<br>

​	✅ **RecyclerViewAdapter.kt**

~~~kotlin
package com.example.coordinatortablayout.RecyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coordinatortablayout.R

class RecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerViewViewHolder>() {
    var datas = mutableListOf<RecyclerData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder{
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item,parent,false)
        return RecyclerViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder:RecyclerViewViewHolder, position: Int) {
        holder.bind(datas[position])
    }

}
~~~

<br>

4️⃣ **OneFragment.kt 에서 rv_one 에 Adapter 연결해주기**

~~~kotlin
package com.example.coordinatortablayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coordinatortablayout.RecyclerView.RecyclerData
import com.example.coordinatortablayout.RecyclerView.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_one.*

/**
 * A simple [Fragment] subclass.
 */
class OneFragment : Fragment() {

    lateinit var rvAdapter: RecyclerViewAdapter
    val datas = mutableListOf<RecyclerData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAdapter  = RecyclerViewAdapter(view.context)
        rv_one.adapter = rvAdapter
        rv_one.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL,false)

        loadDatas()
    }
    fun loadDatas()
    {
        datas.apply {
            add(RecyclerData(string= "text 1"))
            add(RecyclerData(string= "text 2"))
            add(RecyclerData(string= "text 3"))
            add(RecyclerData(string= "text 4"))
            add(RecyclerData(string= "text 5"))
            add(RecyclerData(string= "text 6"))
            add(RecyclerData(string= "text 7"))
            add(RecyclerData(string= "text 8"))
            add(RecyclerData(string= "text 9"))
            add(RecyclerData(string= "text 10"))
            add(RecyclerData(string= "text 11"))
            add(RecyclerData(string= "text 12"))
            add(RecyclerData(string= "text 13"))
            add(RecyclerData(string= "text 14"))
            add(RecyclerData(string= "text 15"))
            add(RecyclerData(string= "text 16"))
            add(RecyclerData(string= "text 17"))
            add(RecyclerData(string= "text 18"))
            add(RecyclerData(string= "text 19"))
            add(RecyclerData(string= "text 20"))
        }
        rvAdapter.datas = datas
        rvAdapter.notifyDataSetChanged()
    }

    fun newInstant() : OneFragment
        {
            val args = Bundle()
            val frag = OneFragment()
            frag.arguments = args
            return frag
        }

}

~~~

<br>

<br>

### ➡️ MainActivity.kt 에서 각각의 tabLayout 별 색깔 다르게 지정해주기

1️⃣ **colors.xml 파일에 원하는 색깔 추가해주기**

~~~xml
  	<color name ="colorOrange">#FACC2E</color>
    <color name="colorRed">#FA5882</color>
    <color name="colorBlue">#81DAF5</color>
~~~

<br>

2️⃣ **MainActivity.kt 에서 addOnTabSelectedListener 추가해주기**

​	‼️ ***아까 만들었던 pager.adapter = pagerAdapter 와 tab.setupWithViewPager(pager) 사이에 넣어주기***

~~~kotlin
				val red = ContextCompat.getColor(this, R.color.colorRed)
        val orange = ContextCompat.getColor(this,R.color.colorOrange)
        val blue = ContextCompat.getColor(this,R.color.colorBlue)
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            @SuppressLint("ResourceAsColor")
            override fun onTabSelected(p0: TabLayout.Tab?) {
               when(p0!!.position)
               {
                   0-> {
                       lab3_appbar_image.setImageResource(R.mipmap.one)
//                       lab3_collapsing.setContentScrimColor(R.color.colorAccent)
                       lab3_collapsing.setBackgroundColor(orange)
                       lab3_collapsing.setContentScrimColor(orange)
                       lab3_collapsing.setStatusBarScrimColor(orange)

                   }
                   1-> {
                       lab3_appbar_image.setImageResource(R.mipmap.two)
//                       lab3_collapsing.setContentScrimColor(R.color.colorAccent)
                       lab3_collapsing.setBackgroundColor(red)
                       lab3_collapsing.setContentScrimColor(red)
                       lab3_collapsing.setStatusBarScrimColor(red)

                   }
                   2-> {
                       lab3_appbar_image.setImageResource(R.mipmap.three)
//                       lab3_collapsing.setContentScrimColor(R.color.colorAccent)
                       lab3_collapsing.setBackgroundColor(blue)
                       lab3_collapsing.setContentScrimColor(blue)
                       lab3_collapsing.setStatusBarScrimColor(blue)

                   }
                   else-> {
                       lab3_appbar_image.setImageResource(R.mipmap.one)
//                       lab3_collapsing.setContentScrimColor(R.color.colorAccent)
                       lab3_collapsing.setBackgroundColor(blue)
                       lab3_collapsing.setContentScrimColor(blue)
                       lab3_collapsing.setStatusBarScrimColor(blue)
                   }
               }
            }

        })
~~~

<br>

<br>

### ➡️ 각각의 tabLayout 별 이미지 크기만 줄이기

<center><img height="500" alt="coordinatorLayout2" src="https://user-images.githubusercontent.com/45454585/81466286-6f6e0c00-920b-11ea-9f88-e8d454a1c5c4.gif"></center>

~~~kotlin
tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            @SuppressLint("ResourceAsColor")
            override fun onTabSelected(p0: TabLayout.Tab?) {
               when(p0!!.position)
               {
                   0-> {
                       lab3_appbar_image.setImageResource(R.mipmap.one)
                       lab3_collapsing.setContentScrimColor(R.color.colorAccent)
//                       lab3_collapsing.setBackgroundColor(orange)
//                       lab3_collapsing.setContentScrimColor(orange)
//                       lab3_collapsing.setStatusBarScrimColor(orange)

                   }
                   1-> {
                       lab3_appbar_image.setImageResource(R.mipmap.two)
                       lab3_collapsing.setContentScrimColor(R.color.colorAccent)
//                       lab3_collapsing.setBackgroundColor(red)
//                       lab3_collapsing.setContentScrimColor(red)
//                       lab3_collapsing.setStatusBarScrimColor(red)

                   }
                   2-> {
                       lab3_appbar_image.setImageResource(R.mipmap.three)
                       lab3_collapsing.setContentScrimColor(R.color.colorAccent)
//                       lab3_collapsing.setBackgroundColor(blue)
//                       lab3_collapsing.setContentScrimColor(blue)
//                       lab3_collapsing.setStatusBarScrimColor(blue)

                   }
                   else-> {
                       lab3_appbar_image.setImageResource(R.mipmap.one)
                       lab3_collapsing.setContentScrimColor(R.color.colorAccent)
//                       lab3_collapsing.setBackgroundColor(blue)
//                       lab3_collapsing.setContentScrimColor(blue)
//                       lab3_collapsing.setStatusBarScrimColor(blue)
                   }
               }
            }

        })

~~~

<br>

<br>

---

✅ **MainActivity.kt**

~~~kotlin
package com.example.coordinatortablayout


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewPager()
    }

    fun initViewPager()
    {
        val pagerAdapter = FragmentAdapter(supportFragmentManager)
        val pager = findViewById<ViewPager>(R.id.viewpager)
        val tab = findViewById<TabLayout>(R.id.tab)
        pager.adapter = pagerAdapter

        val red = ContextCompat.getColor(this, R.color.colorRed)
        val orange = ContextCompat.getColor(this,R.color.colorOrange)
        val blue = ContextCompat.getColor(this,R.color.colorBlue)
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            @SuppressLint("ResourceAsColor")
            override fun onTabSelected(p0: TabLayout.Tab?) {
               when(p0!!.position)
               {
                   0-> {
                       lab3_appbar_image.setImageResource(R.mipmap.one)
//                       lab3_collapsing.setContentScrimColor(R.color.colorAccent)
                       lab3_collapsing.setBackgroundColor(orange)
                       lab3_collapsing.setContentScrimColor(orange)
                       lab3_collapsing.setStatusBarScrimColor(orange)

                   }
                   1-> {
                       lab3_appbar_image.setImageResource(R.mipmap.two)
//                       lab3_collapsing.setContentScrimColor(R.color.colorAccent)
                       lab3_collapsing.setBackgroundColor(red)
                       lab3_collapsing.setContentScrimColor(red)
                       lab3_collapsing.setStatusBarScrimColor(red)

                   }
                   2-> {
                       lab3_appbar_image.setImageResource(R.mipmap.three)
//                       lab3_collapsing.setContentScrimColor(R.color.colorAccent)
                       lab3_collapsing.setBackgroundColor(blue)
                       lab3_collapsing.setContentScrimColor(blue)
                       lab3_collapsing.setStatusBarScrimColor(blue)

                   }
                   else-> {
                       lab3_appbar_image.setImageResource(R.mipmap.one)
//                       lab3_collapsing.setContentScrimColor(R.color.colorAccent)
                       lab3_collapsing.setBackgroundColor(blue)
                       lab3_collapsing.setContentScrimColor(blue)
                       lab3_collapsing.setStatusBarScrimColor(blue)
                   }
               }
            }

        })


        tab.setupWithViewPager(pager)
    }
}

~~~

<br>

<br>

