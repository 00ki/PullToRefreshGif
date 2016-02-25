
# PullToRefresh Gif下拉刷新
<div> <img src='https://raw.githubusercontent.com/00ki/PullToRefreshDemo/master/pulltorefresh.gif' width='270px'/> </div>

# 特点
* 下拉刷新支持gif 动画
* ListView、GridView、ScrollView、WebView等都支持下拉刷新
* 添加加载更多的控件  LoadMoreListView, LoadMoreGridView

# 使用
布局layout.xml 添加
```xml 
<com.pulltorefresh.PtrGifFrameLayout
    android:id="@+id/ptrGifFrameLayout"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#ffffff"
    >
    <!--ListView、GridView、ScrollView、WebView、LoadMoreListView、LoadMoreGridView、等-->
    </com.pulltorefresh.PtrGifFrameLayout>
```
实例化、刷新事件监听
```java

//获取布局view
@ViewById
PtrGifFrameLayout ptrGifFrameLayout;

// 监听下接刷新事件
ptrGifFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //loadData()
            }
        });

// 刷新完成
 ptrGifFrameLayout.refreshComplete();

```
# 哪些APP使用
一元进宝 http://www.yyjinbao.com/
