# EventCollect
这是收集用户行为统计的代码

reademe:

**需求:**:
现在App端需要写一个咱们自己的统计控件。

**要求:** 可以监控用户的所有行为，例如用户谁（没有登录的情况使用UUID），

进入那个页面，进入的时间，在这个页面操作了什么（点击了那个按钮，或者是点击了列表那个列），

离开页面时间，以及其他统计信息，例如版本号，手机系统版本，用户当前网络，用户位置，用户单次使用App时长等

。用户是否把App退到了后台。用户离开了哪一个页面，离开时间。（所有的按钮，页面都需要包含都有的类名，

以及对于的汉子，没有汉子写空即可）

**解决方案:**

重写 dispatchTouchEvent负责分发事件 的方法在这里 遍历 所有节点 收集按下的x y 坐标

在抬起的时候通过用户动作的范围查找相应的View,并组成仔细所需要的json 参数

**功能:**: 可以无埋点 只需要在baseActivity里面 加上几行代码就可以实现收集用户
操作信息的功能.


**注意 :**
统计上报接口采用分布式，不然所有数据都请求同一个接口，那么日活大的情况下，

服务器挂了 不仅无法收到数据，反而影响客户端其他正常的功能

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





last:

这个基本功能是我从另外一个github 上找到的 我在他上面改动了一些.


主要改动的是 前后台变换 ,他用了一个服务 ..痛苦 ,然后我改了一些上传逻辑
