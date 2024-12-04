# Fill Toggle Button
Toggle Button that fills in with a defined color

![](/src/main/res/drawable/demo_imgs.png)
##How to Use
TODO: Publish to central maven -- waiting for sonatype setup

For now, just download the library and add it as a module. 

To use this component:

    <com.jasperlu.filltoggle.FillToggle
      android:layout_width="64dp"
      android:layout_height="64dp"/>

##Custom Attributes
You can customize all of the colors of the button, as well as it's shape. To do so, add this line to the first component in your xml file:

    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:app="http://schemas.android.com/apk/res-auto"
       android:layout_width="match_parent"
       android:layout_height="match_parent">
    </RelativeLayout>
  
These are the attributes you can control: 

    <com.jasperlu.filltoggle.FillToggle
      android:layout_width="64dp"
      android:layout_height="64dp"
      app:shape="rectangle" //anything else will default to circle
      app:on_text_color="" //the rest of these are self-explanatory
      app:off_text_color=""
      app:unchecked_border=""
      app:unchecked_fill=""
      app:pressed_border=""
      app:checked_fill=""
      app:checked_border=""
      app:pressed_fill=""/>
