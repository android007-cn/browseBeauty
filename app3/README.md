# 功能：
1. 下载一组图片，支持上下滑动翻页
2. 浏览图片时支持手势缩放
3. 支持收藏（下载）图片
4. 显示收藏
5. 查看被收藏图片时，可以去收藏
6. "我的"首页显示收藏数量
# 技术点：
1. Retrofit+okHttp:访问网络接口获取数据
2. Coroutines:异步调用
3. ViewPager2:进行翻页
4. Glide:显示图片
5. PhotoView:支持手势缩放图片
6. Room:操作sqLite数据库记录和读取数据
7. BottomNavigationView: 底部导航栏
8. Recyclerview:显示列表