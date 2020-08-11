<template>
  <div class="page-header-index-wide"  style="background-color: white;">
    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="设备监控" key="1" :forceRender=true>
        <div style="background-color: #ececec; padding: 20px;">
          <a-row :gutter="24">
            <a-col :xs="{ span: 24 }" :sm="{ span: 12 }" :lg="{ span: 6 }" v-for="(item,index) in sbList" style="margin-bottom: 10px;" >
              <a-card :height="300" :title="item.equipname" :bordered=false :type="'inner'" :hoverable=true  >
                <!--<a href="#" slot="extra" @click="changeDrawer(index)">
                  <a-tooltip>
                    <template slot='title'>
                      点击查看池内当前温度变化全图
                    </template>
                    查看
                  </a-tooltip>
                </a>-->
                <div v-for="(i,j) in nodeList" v-if="i.equipid == item.id">
                  <!--{{i.gatewayid}}-->

                    <p >
                      网络网关：<b>{{i.gatewayid}}</b>
                    </p>

                    <p>
                      今日动作数：<b>{{i.actionamount}}</b>
                    </p>

                    <p>
                      今日生产数：<b>{{i.productamount}}</b>
                    </p>
                </div>
                <!--  v-if="nodeList[index].gatewayid == 0 && nodeList[index].equipid !=item.id" -->
                <div v-for="(i,j) in list" v-if="checkList.toString().indexOf(item.id) == -1">
                  <p>
                    <br/>
                  </p>
                  <p>
                    <b style="color: rgba(156,233,135,0.7)">暂无信息</b>
                  </p>
                  <p>
                    <br/>
                  </p>

                </div>

                <!--<p v-if="item.node.gatewayid == 0"></p>
                <p v-else>
                  网络网关：<b>{{item.node.gatewayid}}</b>
                </p>
                <p v-if="item.node.actionamount == 0"></p>
                <p v-else>
                  今日动作数：<b>{{item.node.actionamount}}</b>
                </p>
                <p v-if="item.node.productamount == 0"></p>
                <p v-else>
                  今日生产数：<b>{{item.node.productamount}}</b>
                </p>-->
                     <hr color="#bbbbbb" size="1px"/>
              </a-card>
            </a-col>

          </a-row>
        </div>

      </a-tab-pane>
      <a-tab-pane tab="检测信息" key="2" :forceRender=true>
        <div style="background-color: #ececec; padding: 20px;">
          <a-row :gutter="24">
            <a-col :xs="{ span: 24 }" :sm="{ span: 12 }" :lg="{ span: 6 }" v-for="(item,index) in sbList" style="margin-bottom: 10px;" >
              <a-card :height="300" :title="item.equipname" :bordered=false :type="'inner'" :hoverable=true  >
                <!--<a href="#" slot="extra" @click="changeDrawer(index)">
                  <a-tooltip>
                    <template slot='title'>
                      点击查看池内当前温度变化全图
                    </template>
                    查看
                  </a-tooltip>
                </a>-->
                <div v-for="(i,j) in nodeList" v-if="i.equipid == item.id">
                  <!--{{i.gatewayid}}-->

                  <p >
                    网络网关：<b>{{i.gatewayid}}</b>
                  </p>

                  <p>
                    今日动作数：<b>{{i.actionamount}}</b>
                  </p>

                  <p>
                    今日生产数：<b>{{i.productamount}}</b>
                  </p>
                </div>
                <!--  v-if="nodeList[index].gatewayid == 0 && nodeList[index].equipid !=item.id" -->
                <div v-for="(i,j) in list" v-if="checkList.toString().indexOf(item.id) == -1">
                  <p>
                    <br/>
                  </p>
                  <p>
                    <b style="color: rgba(156,233,135,0.7)">暂无信息</b>
                  </p>
                  <p>
                    <br/>
                  </p>

                </div>

                <!--<p v-if="item.node.gatewayid == 0"></p>
                <p v-else>
                  网络网关：<b>{{item.node.gatewayid}}</b>
                </p>
                <p v-if="item.node.actionamount == 0"></p>
                <p v-else>
                  今日动作数：<b>{{item.node.actionamount}}</b>
                </p>
                <p v-if="item.node.productamount == 0"></p>
                <p v-else>
                  今日生产数：<b>{{item.node.productamount}}</b>
                </p>-->
                <hr color="#bbbbbb" size="1px"/>
              </a-card>
            </a-col>

          </a-row>
        </div>

      </a-tab-pane>


    </a-tabs>

  </div>
</template>

<script>
  import ATooltip from "ant-design-vue/es/tooltip/Tooltip"
  import {getAction,postAction} from '@/api/manage'
  import SplitPanelModal from '../../jeecg/modules/SplitPanelModal'
  import ChartCard from '@/components/ChartCard'

  export default {
    name: "detail",
    components: {
      SplitPanelModal,
      ATooltip,
      getAction,
      ChartCard,
    },
    data() {
      return {
        /*设备列表*/
        sbList:[],
        /*设备节点信息*/
        nodeList:[],
        list:[{}],
        checkList: []
      };
    },
    methods: {
      /*查询设备节点实时信息*/
      queryNode () {
        /*清空*/
        this.nodeList.splice(0)
        this.checkList.splice(0)
        //console.log("长度："+this.sbList.length)
        //var ids = []
        /*for (let i = 0; i < this.sbList.length; i++) {
          ids.push(this.sbList.id)
        }*/
        for (let i = 0; i < this.sbList.length; i++) {
          //console.log("id："+this.sbList[i].id)
          getAction('/system/rAcquisitionnodeA/queryNode',{'id':this.sbList[i].id}).then((res)=>{
            if (res.success) {
              //console.log("结果："+res.result)
              let nodeInfo = {
                /*设备编号*/
                equipid:0,
                /*动作数*/
                actionamount:0,
                /*生产数*/
                productamount:0,
                /*网络网关*/
                gatewayid:0,
              }
              for (let j = 0; j < res.result.length; j++) {

                this.checkList.push(res.result[j].equipid)

                /*设备编号*/
                nodeInfo.equipid = res.result[j].equipid
                /*动作数*/
                nodeInfo.actionamount += res.result[j].actionamount
                /*生产数*/
                nodeInfo.productamount += (parseInt(parseInt(res.result[j].rdata) / parseInt(res.result[j].actionamount) * parseInt(res.result[j].productamount)))
                /*网络网关*/
                nodeInfo.gatewayid = res.result[j].gatewayid
              }
              this.nodeList.push(nodeInfo)
              /*if (this.nodeList.length > 1) {
                this.nodeList.shift()
              }*/

              //console.log(this)
              //this.set(this.sbList[i],"node",node);
              //this.sbList[i].set(node)
              //this.sbList[i].nodeInfo = nodeInfo
            }
          });
        }

      },
      /*获取设备下拉框内容*/
      getSBList () {
        getAction('/system/rEquipment/getAll',null).then((res)=>{
          if(res.success){
            /*清空*/
            this.sbList.splice(0)
            this.sbList = res.result
            this.queryNode ()
          }
        });
      },
    },
    created () {
      //获取设备信息
      this.getSBList()

    },
    mounted () {


      //this.queryNode ()
      //定时器
      setInterval(() => {
        this.queryNode ()
      }, 5000);
    },

  }
</script>

<style lang="scss" scoped>
  .pools {
    font-size: 20px;
    color: white;
    border-radius: 30px;
    background: rgba(0, 160, 233, 0.6);//#00A0E9;
    line-height: 50px;
    text-align: center;
  }
</style>