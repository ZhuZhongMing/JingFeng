<template>
  <a-popover
    trigger="click"
    placement="bottomRight"
    :autoAdjustOverflow="true"
    :arrowPointAtCenter="true"
    overlayClassName="header-notice-wrapper"
    @visibleChange="handleHoverChange"
    :overlayStyle="{ width: '300px', top: '50px' }">
    <template slot="content">
      <a-spin :spinning="loadding">
        <a-tabs>
          <a-tab-pane :tab="msg1Title" key="1">
            <!--<a-list>
              <a-list-item>
                <a-list-item-meta title="你收到了 14 份新周报" description="一年前">
                  <a-avatar style="background-color: white" slot="avatar" src="https://gw.alipayobjects.com/zos/rmsportal/ThXAXghbEsBCCSDihZxY.png"/>
                </a-list-item-meta>
              </a-list-item>
              <a-list-item>
                <a-list-item-meta title="你推荐的 IT大牛 已通过第三轮面试" description="一年前">
                  <a-avatar style="background-color: white" slot="avatar" src="https://gw.alipayobjects.com/zos/rmsportal/OKJXDXrmkNshAMvwtvhu.png"/>
                </a-list-item-meta>
              </a-list-item>
              <a-list-item>
                <a-list-item-meta title="这种模板可以区分多种通知类型" description="一年前">
                  <a-avatar style="background-color: white" slot="avatar" src="https://gw.alipayobjects.com/zos/rmsportal/kISTdvpyTAhtGxpovNWd.png"/>
                </a-list-item-meta>
              </a-list-item>
            </a-list>-->
            <a-list>
              <!--<a-list-item :key="index" v-for="(record, index) in announcement1">
                <div style="margin-left: 5%;width: 80%">
                  <p><a @click="showAnnouncement(record)">标题：{{ record.titile }}</a></p>
                  <p style="color: rgba(0,0,0,.45);margin-bottom: 0px">{{ record.createTime }} 发布</p>
                </div>
                <div style="text-align: right">
                  <a-tag @click="showAnnouncement(record)" v-if="record.priority === 'L'" color="blue">一般消息</a-tag>
                  <a-tag @click="showAnnouncement(record)" v-if="record.priority === 'M'" color="orange">重要消息</a-tag>
                  <a-tag @click="showAnnouncement(record)" v-if="record.priority === 'H'" color="red">紧急消息</a-tag>
                </div>
              </a-list-item>-->
              <div style="margin-top: 5px;text-align: center" v-if="msg1Count > 0">
                <a-button @click="toMyAnnouncement()" type="dashed" block>点击查看</a-button>
              </div>
              <div style="margin-top: 5px;text-align: center" v-else>
                <a-button :disabled="true" block>暂无任务</a-button>
              </div>
            </a-list>
          </a-tab-pane>
          <a-tab-pane :tab="msg2Title" key="2">
            <a-list>
              <!-- <a-list-item :key="index" v-for="(record, index) in announcement2"> -->
              <!--<a-list-item :key="index" v-for="(record, index) in announcement1">
                <div style="margin-left: 5%;width: 80%">
                  <p><a @click="showAnnouncement(record)">标题：{{ record.titile }}</a></p>
                  <p style="color: rgba(0,0,0,.45);margin-bottom: 0px">{{ record.createTime }} 发布</p>
                </div>
                &lt;!&ndash;<div style="text-align: right">
                  <a-tag @click="showAnnouncement(record)" v-if="record.priority === 'L'" color="blue">一般消息</a-tag>
                  <a-tag @click="showAnnouncement(record)" v-if="record.priority === 'M'" color="orange">重要消息</a-tag>
                  <a-tag @click="showAnnouncement(record)" v-if="record.priority === 'H'" color="red">紧急消息</a-tag>
                </div>&ndash;&gt;
              </a-list-item>-->
              <div style="margin-top: 5px;text-align: center" v-if="msg2Count > 0">
                <a-button @click="toMyAnnouncement()" type="dashed" block>点击查看</a-button>
              </div>
              <div style="margin-top: 5px;text-align: center" v-else>
                <a-button :disabled="true" block>暂无任务</a-button>
              </div>
            </a-list>
          </a-tab-pane>
        </a-tabs>
      </a-spin>
    </template>
    <span @click="fetchNotice" class="header-notice">
      <a-badge :count="msgTotal">
        <a-icon style="font-size: 16px; padding: 4px" type="bell" />
      </a-badge>
    </span>
    <show-announcement ref="ShowAnnouncement" @ok="modalFormOk"></show-announcement>
  </a-popover>
</template>

<script>
  import { getAction,putAction } from '@/api/manage'
  import ShowAnnouncement from './ShowAnnouncement'
  import store from '@/store/'

  export default {
    name: "HeaderNotice",
    components: {
      ShowAnnouncement,
    },
    data () {
      return {
        loadding: false,
        url:{
          //listCementByUser:"/sys/annountCement/listByUser",
          //listCementByUser:"/system/mMainplanabstract/countByid",
          listCementByUser:"/system/mProductTaskOrder/count",
          count:"/system/mProductTaskOrder/countStart",
          editCementSend:"/sys/sysAnnouncementSend/editByAnntIdAndUserId",
          queryById:"/sys/annountCement/queryById",
        },
        hovered: false,
        announcement1:[],
        announcement2:[],
        msg1Count:0,
        msg2Count:0,
        msg1Title:"等待开始",
        msg2Title:"正在生产",
        stopTimer:false,
      }
    },
    computed:{
      msgTotal () {
        /*console.log("1   "+parseInt(this.msg1Count))
        console.log("2   "+parseInt(this.msg2Count))
        console.log("sum   "+parseInt(this.msg1Count)+parseInt(this.msg2Count))*/
        //return 20;
        //return parseInt(this.msg1Count)+parseInt(this.msg2Count);
        return parseInt(this.msg1Count)+parseInt(this.msg2Count);
      }
    },
    mounted() {
      this.loadData();
      //this.timerFun();
      //this.initWebSocket();
      //this.countFinshNumber ();
      /*监控数量*/
      /*setInterval(() => {
        this.countFinshNumber ();
        //this.loadData();
      },1000);*/
    },
    destroyed: function () { // 离开页面生命周期函数
      this.websocketclose();
    },
    methods: {
      /*监控加工数量*/
      countFinshNumber () {
        getAction('/system/mProductTaskOrder/countFinshNumber',null).then((res) => {
          if (res.success) {
            //console.log("ss"+res.result)
          }
        })
      },
      timerFun() {
        this.stopTimer = false;
        let myTimer = setInterval(()=>{
          // 停止定时器
          if (this.stopTimer == true) {
            clearInterval(myTimer);
            return;
          }
          this.loadData()
        },6000)
      },
      loadData (){
        /*等待开始*/
        try {
          // 获取系统消息
          getAction(this.url.listCementByUser).then((res) => {
            if (res.success) {
              //console.log("total:"+res.result.total)
              this.msg1Count = res.result
              //console.log("长度："+res.result.length)
              this.msg1Title = "生产任务 (" + this.msg1Count + ")";
              /*this.announcement1 = res.result.anntMsgList;
              this.msg1Count = res.result.anntMsgTotal;
              this.msg1Title = "通知(" + res.result.anntMsgTotal + ")";
              this.announcement2 = res.result.sysMsgList;
              this.msg2Count = res.result.sysMsgTotal;
              this.msg2Title = "系统消息(" + res.result.sysMsgTotal + ")";*/
            }
          }).catch(error => {
            console.log("系统消息通知异常",error);//这行打印permissionName is undefined
            this.stopTimer = true;
            console.log("清理timer");
          });
        } catch (err) {
          this.stopTimer = true;
          console.log("通知异常",err);
        }
        /*正在生产*/
        try {
          // 获取系统消息
          getAction(this.url.count).then((res) => {
            if (res.success) {
              //console.log("total:"+res.result.total)
              this.msg2Count = res.result
              //console.log("长度："+res.result.length)
              this.msg2Title = "正在生产 (" + this.msg2Count + ")";
              /*this.announcement1 = res.result.anntMsgList;
              this.msg1Count = res.result.anntMsgTotal;
              this.msg1Title = "通知(" + res.result.anntMsgTotal + ")";
              this.announcement2 = res.result.sysMsgList;
              this.msg2Count = res.result.sysMsgTotal;
              this.msg2Title = "系统消息(" + res.result.sysMsgTotal + ")";*/
            }
          }).catch(error => {
            console.log("系统消息通知异常",error);//这行打印permissionName is undefined
            this.stopTimer = true;
            console.log("清理timer");
          });
        } catch (err) {
          this.stopTimer = true;
          console.log("通知异常",err);
        }
      },
      fetchNotice () {
        if (this.loadding) {
          this.loadding = false
          return
        }
        this.loadding = true
        setTimeout(() => {
          this.loadding = false
        }, 200)
      },
      showAnnouncement(record){
        putAction(this.url.editCementSend,{anntId:record.id}).then((res)=>{
          if(res.success){
            this.loadData();
          }
        });
        this.hovered = false;
        this.$refs.ShowAnnouncement.detail(record);
      },
      /*path: '/plan/MyPlanList',
          name: 'plan/MyPlanList'*/


      toMyAnnouncement(){

        this.$router.push({
          /*path: '/isps/userAnnouncement',
          name: 'isps-userAnnouncement'*/
          /*path: '/plan/MyPlanList',
          name: 'plan-MyPlanList'*/
          path: '/jf/plan/MyProductTaskOrderList',
          name: 'jf-plan-MyProductTaskOrderList'

        });
        this.msg1Count = 0
        this.msg2Count = 0
        this.msg1Title = "生产任务(" + this.msg1Count + ")";
        this.msg2Title = "正在生产 (" + this.msg2Count + ")";

      },
      modalFormOk(){
      },
      handleHoverChange (visible) {
        this.hovered = visible;
      },

      initWebSocket: function () {
        // WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https
        var userId = store.getters.userInfo.id;
        var url = window._CONFIG['domianURL'].replace("https://","wss://").replace("http://","ws://")+"/websocket/"+userId;
        //console.log(url);
        this.websock = new WebSocket(url);
        this.websock.onopen = this.websocketonopen;
        this.websock.onerror = this.websocketonerror;
        this.websock.onmessage = this.websocketonmessage;
        this.websock.onclose = this.websocketclose;
      },
      websocketonopen: function () {
        console.log("WebSocket连接成功");
      },
      websocketonerror: function (e) {
        console.log("WebSocket连接发生错误");
      },
      websocketonmessage: function (e) {
        console.log("-----接收消息-------",e.data);
        var data = eval("(" + e.data + ")"); //解析对象
        this.loadData();
        //if(data.cmd == "topic"){
          //系统通知
            this.openNotification(data);
        //}else if(data.cmd == "user"){
          //用户消息
        //  this.openNotification(data);
        //}


      },
      websocketclose: function (e) {
        //console.log("connection closed (" + e.code + ")");
      },

      openNotification (data) {
        var text = data.msgTxt;
        const key = `open${Date.now()}`;
        this.$notification.open({
          message: '消息提醒',
          placement:'bottomRight',
          description: text,
          key,
          btn: (h)=>{
            return h('a-button', {
              props: {
                type: 'primary',
                size: 'small',
              },
              on: {
                click: () => this.showDetail(key,data)
              }
            }, '查看详情')
          },
        });
      },

      showDetail(key,data){
        this.$notification.close(key);
        var id = data.msgId;
        getAction(this.url.queryById,{id:id}).then((res) => {
          if (res.success) {
            var record = res.result;
            this.showAnnouncement(record);
          }
        })

      },
    }
  }
</script>

<style lang="css">
  .header-notice-wrapper {
    top: 50px !important;
  }
</style>
<style lang="scss" scoped>
  .header-notice{
    display: inline-block;
    transition: all 0.3s;

    span {
      vertical-align: initial;
    }
  }
</style>