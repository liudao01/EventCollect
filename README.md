
[TOC]
# EventCollect
这是收集用户行为统计的代码

reademe:

**需求:**:
现在App端需要写一个咱们自己的统计控件。

**要求:** 可以监控用户的所有行为，例如用户谁（没有登录的情况使用UUID），

进入那个页面，进入的时间，在这个页面操作了什么（点击了那个按钮 图片. ），

离开页面时间，以及其他统计信息，例如版本号，手机系统版本，用户当前网络，用户位置，用户单次使用App时长等

。用户是否把App退到了后台。用户离开了哪一个页面，离开时间。（所有的按钮，页面都需要包含都有的类名，

以及对于的汉字，没有汉字写空即可）

**解决方案:**

重写 dispatchTouchEvent负责分发事件 的方法在这里 遍历 所有节点 收集按下的x y 坐标

在抬起的时候通过用户动作的范围查找相应的View,并组成自己所需要的json 参数

**功能:**: 可以无埋点 只需要在baseActivity里面 加上几行代码就可以实现收集用户
操作信息的功能.


**注意 :**
统计上报接口采用分布式，不然所有数据都请求同一个接口，那么日活大的情况下，

服务器挂了 不仅无法收到数据，反而影响客户端其他正常的功能

**演示操作 gif图有点大 :**

我这里演示  模拟请求成功和请求失败的情况

在DateSender类中MySend 线程 中控制请求成功和失败



**第一种情况: 收集事件达到五次 上传数据成功的演示:**

 ![image](https://raw.githubusercontent.com/liudao01/EventCollect/master/%E4%BA%8B%E4%BB%B6%E6%94%B6%E9%9B%86.gif)
 
 
 
 
**第二种情况: 收集事件达到五次 上传数据不成功的演示 存入本地:**

 ![image](https://github.com/liudao01/EventCollect/blob/master/%E4%BA%8B%E4%BB%B6%E6%94%B6%E9%9B%86%E4%B8%8A%E4%BC%A0%E4%B8%8D%E6%88%90%E5%8A%9F.gif?raw=true)
 
 
 
 
**第三种情况: 收集事件达到五次 上传数据成功 并且把以前未上传成功的数据一起上传:**

 ![image](https://github.com/liudao01/EventCollect/blob/master/%E4%BA%8B%E4%BB%B6%E6%94%B6%E9%9B%86%E4%B8%8A%E4%BC%A0%E6%88%90%E5%8A%9F%E5%92%8C%E6%9C%AC%E5%9C%B0%E7%9A%84%E4%B8%80%E8%B5%B7%E4%B8%8A%E4%BC%A0.gif?raw=true)








**流程图**

 ![image](https://raw.githubusercontent.com/liudao01/EventCollect/master/%E7%BB%9F%E8%AE%A1%E6%B5%81%E7%A8%8B%E5%9B%BE%20(1).png)


**上传的json数据**

        {
            "userData":{
                "app_channel":"",
                "network":"NONE",
                "sysVersion":"6.0.1",
                "w_and_h":"1080*1920",
                "UUID":"ffffffff-89a3-f09e-115f-86b90033c587",
                "version":1,
                "phoneModel":"Redmi Note 3",
                "comeFrom":"az_wn",
                "user_loaction":{
                    "district":"朝阳区",
                    "streetName":"三元桥",
                    "province":"北京",
                    "lat":"37.0",
                    "lng":"37.0",
                    "city":"北京"
                }
            },
            "events":[
                {
                    "type":"app",
                    "evenTime":1499672827876,
                    "even":"in",
                    "page":"MainActivity",
                    "name":"首页"
                },
                {
                    "evenTime":1499672844001,
                    "name":"点击收集数据",
                    "page":"MainActivity",
                    "even":"click",
                    "type":"button"
                },
                {
                    "evenTime":1499672844604,
                    "name":"点击收集数据",
                    "page":"MainActivity",
                    "even":"click",
                    "type":"button"
                },
                {
                    "evenTime":1499672845334,
                    "name":"自定义事件",
                    "page":"MainActivity",
                    "even":"click",
                    "type":"button"
                },
                {
                    "evenTime":1499672845338,
                    "name":"nihao",
                    "attributes":{
                        "item-category":"book"
                    },
                    "page":"MainActivity",
                    "even":"click",
                    "type":"custum"
                }
            ],
            "session":{
                "id":"1499672845390_1951868",
                "time":1499672845390
            }
        }



## TODO
目前有个问题 如果保存到本地的数据太多 用网络上传数据的话很容易导致OOM, 目前我把存入本地逻辑干掉了.
后面优化的想法是把需要上传的数据放入队列中,只有当前一个数据请求成功后,队列才可以出去一个数据.

update : rxjava 可以实现


**last**:

感谢github上各种收集事件项目给我的思路.
 
 所以我也把自己写的贡献出来,希望能帮助到有需要的人
