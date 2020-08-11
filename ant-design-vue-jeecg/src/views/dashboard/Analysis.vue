<!--
<template>
  <div class="page-header-index-wide">
    <a-row :gutter="24">
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="总销售额" total="￥126,560">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <trend flag="up" style="margin-right: 16px;">
              <span slot="term">周同比</span>
              12%
            </trend>
            <trend flag="down">
              <span slot="term">日同比</span>
              11%
            </trend>
          </div>
          <template slot="footer">日均销售额<span>￥ 234.56</span></template>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="访问量" :total="8846 | NumberFormat">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <mini-area />
          </div>
          <template slot="footer">日访问量<span> {{ '1234' | NumberFormat }}</span></template>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="支付笔数" :total="6560 | NumberFormat">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <mini-bar :height="40" />
          </div>
          <template slot="footer">转化率 <span>60%</span></template>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="运营活动效果" total="78%">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <mini-progress color="rgb(19, 194, 194)" :target="80" :percentage="78" :height="8" />
          </div>
          <template slot="footer">
            <trend flag="down" style="margin-right: 16px;">
              <span slot="term">同周比</span>
              12%
            </trend>
            <trend flag="up">
              <span slot="term">日环比</span>
              80%
            </trend>
          </template>
        </chart-card>
      </a-col>
    </a-row>

    <a-card :loading="loading" :bordered="false" :body-style="{padding: '0'}">
      <div class="salesCard">
        <a-tabs default-active-key="1" size="large" :tab-bar-style="{marginBottom: '24px', paddingLeft: '16px'}">
          <div class="extra-wrapper" slot="tabBarExtraContent">
            <div class="extra-item">
              <a>今日</a>
              <a>本周</a>
              <a>本月</a>
              <a>本年</a>
            </div>
            <a-range-picker :style="{width: '256px'}" />
          </div>
          <a-tab-pane loading="true" tab="销售额" key="1">
            <a-row>
              <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">
                <bar title="销售额排行" :dataSource="barData"/>
              </a-col>
              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">
                <rank-list title="门店销售排行榜" :list="rankList"/>
              </a-col>
            </a-row>
          </a-tab-pane>
          <a-tab-pane tab="访问量" key="2">
            <a-row>
              <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">
                <bar title="销售额趋势" :dataSource="barData"/>
              </a-col>
              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">
                <rank-list title="门店销售排行榜" :list="rankList"/>
              </a-col>
            </a-row>
          </a-tab-pane>
        </a-tabs>
      </div>
    </a-card>

    <a-row>
      <a-col :span="24">
        <a-card :loading="loading" :bordered="false" title="最近一周访问次数统计" :style="{ marginTop: '24px' }">
          <a-row>
            <a-col :span="6">
              <head-info title="今日访问IP数" :content="loginfo.todayIp"></head-info>
            </a-col>
            <a-col :span="2">
              <a-spin class='circle-cust'>
                <a-icon slot="indicator" type="environment" style="font-size: 24px"  />
              </a-spin>
            </a-col>
            <a-col :span="6">
              <head-info title="今日访问次数" :content="loginfo.todayVisitCount"></head-info>
            </a-col>
            <a-col :span="2">
              <a-spin class='circle-cust'>
                <a-icon slot="indicator" type="team" style="font-size: 24px"  />
              </a-spin>
            </a-col>
            <a-col :span="6">
              <head-info title="访问总次数" :content="loginfo.totalVisitCount"></head-info>
            </a-col>
            <a-col :span="2">
              <a-spin class='circle-cust'>
                <a-icon slot="indicator" type="rise" style="font-size: 24px"  />
              </a-spin>
            </a-col>
          </a-row>
          <line-chart-multid :fields="visitFields" :dataSource="visitInfo"></line-chart-multid>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  import ChartCard from '@/components/ChartCard'
  import ACol from "ant-design-vue/es/grid/Col"
  import ATooltip from "ant-design-vue/es/tooltip/Tooltip"
  import MiniArea from '@/components/chart/MiniArea'
  import MiniBar from '@/components/chart/MiniBar'
  import MiniProgress from '@/components/chart/MiniProgress'
  import RankList from '@/components/chart/RankList'
  import Bar from '@/components/chart/Bar'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import HeadInfo from '@/components/tools/HeadInfo.vue'

  import Trend from '@/components/Trend'
  import { getLoginfo,getVisitInfo } from '@/api/api'

  const rankList = []
  for (let i = 0; i < 7; i++) {
    rankList.push({
      name: '白鹭岛 ' + (i+1) + ' 号店',
      total: 1234.56 - i * 100
    })
  }
  const barData = []
  for (let i = 0; i < 12; i += 1) {
    barData.push({
      x: `${i + 1}月`,
      y: Math.floor(Math.random() * 1000) + 200
    })
  }
  export default {
    name: "Analysis",
    components: {
      ATooltip,
      ACol,
      ChartCard,
      MiniArea,
      MiniBar,
      MiniProgress,
      RankList,
      Bar,
      Trend,
      LineChartMultid,
      HeadInfo
    },
    data() {
      return {
        loading: true,
        center: null,
        rankList,
        barData,
        loginfo:{},
        visitFields:['ip','visit'],
        visitInfo:[],
        indicator: <a-icon type="loading" style="font-size: 24px" spin />
      }
    },
    created() {
      setTimeout(() => {
        this.loading = !this.loading
      }, 1000)
      this.initLogInfo();
    },
    methods: {
      initLogInfo () {
        getLoginfo(null).then((res)=>{
          if(res.success){
            Object.keys(res.result).forEach(key=>{
              res.result[key] =res.result[key]+""
            })
            this.loginfo = res.result;
          }
        })
        getVisitInfo().then(res=>{
          if(res.success){
             console.log("aaaaaa",res.result)
             this.visitInfo = res.result;
           }
         })
      },
    }
  }
</script>

<style lang="scss" scoped>
  .circle-cust{
    position: relative;
    top: 28px;
    left: -100%;
  }
  .extra-wrapper {
    line-height: 55px;
    padding-right: 24px;

    .extra-item {
      display: inline-block;
      margin-right: 24px;

      a {
        margin-left: 24px;
      }
    }
  }

  /* 首页访问量统计 */
  .head-info {
    position: relative;
    text-align: left;
    padding: 0 32px 0 0;
    min-width: 125px;

    &.center {
      text-align: center;
      padding: 0 32px;
    }

    span {
      color: rgba(0, 0, 0, .45);
      display: inline-block;
      font-size: .95rem;
      line-height: 42px;
      margin-bottom: 4px;
    }
    p {
      line-height: 42px;
      margin: 0;
      a {
        font-weight: 600;
        font-size: 1rem;
      }
    }
  }
</style>-->

<template>
  <!--  @dblclick="getKeyupValue" -->
  <div>

    <!--<div class="page-header-index-wide" v-if="flag">
      <a-row :gutter="24">
        <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
          <chart-card title="今日订单物料数量" :total="todaySumMaterial">
            <a-tooltip title="订单物料数量" slot="action">
              <a-icon type="info-circle-o" />
            </a-tooltip>
            <div>
              订单物料总数：<b>{{sumMaterial}}</b>&nbsp;
            </div>
            <template slot="footer"><br /></template>
          </chart-card>
        </a-col>
        <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
          <chart-card title="设备今日动作次数" :total="todayRdata">
            <a-tooltip title="设备动作次数" slot="action">
              <a-icon type="info-circle-o" />
            </a-tooltip>
            <div>
              设备动作总次数：<b>{{sumRdata}}</b>&nbsp;
            </div>
            <template slot="footer"><br /></template>
          </chart-card>
        </a-col>
        <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
          <chart-card title="今日生产数量" :total="todayNode">
            <a-tooltip title="设备生产数量" slot="action">
              <a-icon type="info-circle-o" />
            </a-tooltip>
            <div>
              物料生产总数：<b>{{sumNode}}&nbsp;</b>
            </div>
            <template slot="footer"><br /></template>
          </chart-card>
        </a-col>
      </a-row>

      <a-card :bordered="false" :body-style="{padding: '0'}">
        <div class="salesCard">
          <a-row>
            <a-col :xl="2" :lg="12" :md="12" :sm="24" :xs="24"></a-col>
            <a-col :xl="20" :lg="12" :md="12" :sm="24" :xs="24">
              <bar :height="520" :title="' 2019 年每月订单总数统计图'" :dataSource="barData" style="margin-top: 20px;"/>
            </a-col>
            <a-col :xl="2" :lg="12" :md="12" :sm="24" :xs="24"></a-col>
          </a-row>
        </div>
      </a-card>

    </div>-->
    <!-- style="background-color: #142437;" -->
    <!--<dv-full-screen-container v-else style="background-color: #142437; color: white;">
      &lt;!&ndash; style="width: 25%; height: 300px; line-height: 300px; text-align: center; float: left" &ndash;&gt;
      <h1 align="center" style="color: white; margin-top: 30px;margin-bottom: -50px;">生产综合数据</h1>
      <dv-decoration-8 style="width:20%;height:60px;float: left;margin-right: 5%;" />
      <dv-decoration-5 style="width:50%;height:125px;float: left;margin: 0px auto;" />
      <dv-decoration-8 :reverse="true" style="width:20%;height:60px;float: right;margin-left: 5%;" />

      &lt;!&ndash; 看板主体内容 &ndash;&gt;
      <div style="clear: both;height: 930px;">

        &lt;!&ndash; 空白间隔 &ndash;&gt;
        <div style="width: 1%; height: 100%;float: left;"></div>
        &lt;!&ndash; 空白间隔 &ndash;&gt;
        <dv-border-box-3 style="width: 23%;height: 100%;float: left;">dv-border-box-3</dv-border-box-3>
        &lt;!&ndash; 空白间隔 &ndash;&gt;
        <div style="width: 1%; height: 100%;float: left;"></div>
        &lt;!&ndash; 空白间隔 &ndash;&gt;
        &lt;!&ndash;<div style="float: left;width: 75%; height: 100%;">&ndash;&gt;
        &lt;!&ndash; :color="['red','yellow']" &ndash;&gt;
        <dv-border-box-3 style="width: 50%; height: 75%;float: left;">dv-border-box-3</dv-border-box-3>
        &lt;!&ndash; 空白间隔 &ndash;&gt;
        <div style="width: 1%; height: 75%;float: left;"></div>
        &lt;!&ndash; 空白间隔 &ndash;&gt;

        &lt;!&ndash; 检测机数据 &ndash;&gt;
        <dv-border-box-2 style="width: 23%;height: 75%;float: left;">
          <div style="width: 100%;height: 24%;">
            &lt;!&ndash; 检测机 &ndash;&gt;

          </div>

          <dv-decoration-4 :reverse="true" style="width:100%;height:1%;" />
          <div style="width: 100%;height: 24%;">
            &lt;!&ndash; 检测机 &ndash;&gt;

          </div>

          <dv-decoration-4 :reverse="true" style="width:100%;height:1%;" />
          <div style="width: 100%;height: 24%;">
            &lt;!&ndash; 检测机 &ndash;&gt;

          </div>

          <dv-decoration-4 :reverse="true" style="width:100%;height:1%;" />
          <div style="width: 100%;height: 24%;">
            &lt;!&ndash; 检测机 &ndash;&gt;

          </div>

        </dv-border-box-2>


        &lt;!&ndash; 空白间隔 &ndash;&gt;
        <div style="width: 75%; height: 1%;float: left;"></div>
        &lt;!&ndash; 空白间隔 &ndash;&gt;

        &lt;!&ndash; 装配机数据 &ndash;&gt;
        <dv-border-box-2 style="width: 74%;height: 24%;float: left">
          <div style="width: 15%;height: 100%;float: left;">
            &lt;!&ndash; 装配机 &ndash;&gt;

          </div>

          <dv-decoration-2 :reverse="true" style="width:2%;height:90%;float: left;margin-top: 1%;" />
          <div style="width: 15%;height: 100%;float: left;">
            &lt;!&ndash; 装配机 &ndash;&gt;

          </div>

          <dv-decoration-2 :reverse="true" style="width:2%;height:90%;float: left;margin-top: 1%;" />
          <div style="width: 15%;height: 100%;float: left;">
            &lt;!&ndash; 装配机 &ndash;&gt;

          </div>

          <dv-decoration-2 :reverse="true" style="width:2%;height:90%;float: left;margin-top: 1%;" />
          <div style="width: 15%;height: 100%;float: left;">
            &lt;!&ndash; 装配机 &ndash;&gt;

          </div>

          <dv-decoration-2 :reverse="true" style="width:2%;height:90%;float: left;margin-top: 1%;" />
          <div style="width: 15%;height: 100%;float: left;">
            &lt;!&ndash; 装配机 &ndash;&gt;

          </div>

          <dv-decoration-2 :reverse="true" style="width:2%;height:90%;float: left;margin-top: 1%;" />
          <div style="width: 15%;height: 100%;float: left;">
            &lt;!&ndash; 装配机 &ndash;&gt;

          </div>

        </dv-border-box-2>
        &lt;!&ndash;</div>&ndash;&gt;




      </div>



    </dv-full-screen-container>-->

    <index-task></index-task>

  </div>

</template>

<script>
  import ChartCard from '@/components/ChartCard'
  import ACol from "ant-design-vue/es/grid/Col"
  import ATooltip from "ant-design-vue/es/tooltip/Tooltip"
  import MiniArea from '@/components/chart/MiniArea'
  import MiniBar from '@/components/chart/MiniBar'
  import MiniProgress from '@/components/chart/MiniProgress'
  import RankList from '@/components/chart/RankList'
  import Bar from '@/components/chart/Bar'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import HeadInfo from '@/components/tools/HeadInfo.vue'
  import Trend from '@/components/Trend'
  import {getAction} from '@/api/manage'

  import IndexTask from "./IndexTask"

  //const barData = []


  export default {
    name: "Analysis",
    components: {
      ATooltip,
      ACol,
      ChartCard,
      MiniArea,
      MiniBar,
      MiniProgress,
      RankList,
      Bar,
      Trend,
      LineChartMultid,
      HeadInfo,
      getAction,
      IndexTask
    },
    data() {
      return {
        /*页面样式flag*/
        flag:true,
        heightL:document.body.clientHeight - 125,
        barData:[],
        datasource: [],
        /*年份*/
        year:'',
        /*今日订单总数*/
        todaySumMaterial:'0',
        /*订单总数*/
        sumMaterial:'0',
        /*今日动作总数*/
        todayRdata:'0',
        /*动作总数*/
        sumRdata:'0',
        /*生产总数*/
        sumNode:'0',
        /*今日生产总数*/
        todayNode:'0',

      }
    },
    created () {
     /* var _self = this;

      document.onkeydown = function(e) {

        var key = window.event.keyCode;

        if (key == 13) {

          console.log(key)

        }


      }*/
    },
    mounted() {
      /*this.getsumMaterial();
      this.gettodaySumMaterial();
      this.getSumRdata();
      this.getTodayRdata();*/
      /*this.getSumNode ()*/
      /*this.getTodayNode ()*/
    },
    methods: {
      /*键盘监听*/
      getKeyupValue(e) {
        console.log('.0.0.0..0')
        var _self = this;
        _self.flag = (!_self.flag)
        //var key = window.event.keyCode;
        //console.log(key)
      },
      /*订单总数*/
      getsumMaterial ()  {

        getAction('/system/mOrder/getSumMaterial',null).then((res)=>{
          if (res.success) {
            this.sumMaterial = res.result.toString()
            //console.log("tall:"+res.result.tall)
            this.queryByMonth()
          }
        });

      },
      /*今日订单总数*/
      gettodaySumMaterial () {

        getAction('/system/mOrder/getTodaySumMaterial',null).then((res)=>{
          if (res.success) {
            this.todaySumMaterial = res.result.toString()
            //console.log("low:"+res.result.low)
          }
        });

      },
      /*动作总数*/
      getSumRdata () {

        getAction('/system/rAcquisitionnodeA/getSumRdata',null).then((res)=>{
          if (res.success) {
            this.sumRdata = 0
            this.sumRdata = res.result.toString()
            //console.log("count:"+res.result.count)
          }
        });

      },
      /*今日动作总数*/
      getTodayRdata () {

        getAction('/system/rAcquisitionnodeA/getTodayRdata',null).then((res)=>{
          if (res.success) {
            this.todayRdata =0
            this.todayRdata = res.result.toString()
            //console.log("count:"+res.result.count)
          }
        });

      },
      /*生产总数*/
      getSumNode () {

        getAction('/system/rAcquisitionnodeA/getSumNode',null).then((res)=>{
          if (res.success) {
            this.sumNode = 0

            for (let j = 0; j < res.result.length; j++) {
              /*生产数*/
              this.sumNode += (parseInt(parseInt(res.result[j].rdata) / parseInt(res.result[j].actionamount) * parseInt(res.result[j].productamount)))
            }


          }
        });

      },
      /*今日生产总数*/
      getTodayNode () {

        getAction('/system/rAcquisitionnodeA/getTodayNode',null).then((res)=>{
          if (res.success) {
            this.todayNode = 0

            for (let j = 0; j < res.result.length; j++) {
              /*生产数*/
              this.todayNode += (parseInt(parseInt(res.result[j].rdata) / parseInt(res.result[j].actionamount) * parseInt(res.result[j].productamount)))
            }


          }
        });

      },
      /*按月统计*/
      queryByMonth () {

        /*getAction('/system/gsMaterial/queryByMonth',null).then((res)=>{
          if (res.success) {
            var date = new Date();
            this.year = date.getFullYear()
            for (let i = 0; i < res.result.length; i++) {
              var time = '';
              if (res.result[i].time == '01') {
                time = '一月'
              } else if (res.result[i].time == '02') {
                time = '二月'
              } else if (res.result[i].time == '03') {
                time = '三月'
              } else if (res.result[i].time == '04') {
                time = '四月'
              } else if (res.result[i].time == '05') {
                time = '五月'
              } else if (res.result[i].time == '06') {
                time = '六月'
              } else if (res.result[i].time == '07') {
                time = '七月'
              } else if (res.result[i].time == '08') {
                time = '八月'
              } else if (res.result[i].time == '09') {
                time = '九月'
              } else if (res.result[i].time == '10') {
                time = '十月'
              } else if (res.result[i].time == '11') {
                time = '十一月'
              } else if (res.result[i].time == '12') {
                time = '十二月'
              }

              this.datasource.push({
                "x": time,
                "y": res.result[i].count
              })
            }
          }
        });*/

        for (let i = 0; i < 12; i += 1) {

          if ((i+1) == 10) {
            this.barData.push({
              x: `${i + 1}月`,
              y: this.sumMaterial
            })
          } else {
            this.barData.push({
              x: `${i + 1}月`,
              y: 0
            })
          }


        }


      }
    }
  }
</script>

<style lang="scss" scoped>

</style>
