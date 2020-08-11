<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :maskClosable="true"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">


        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="生产计划编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <!--<a-input v-decorator="[ 'materialencodeid', validatorRules.materialencodeid]" placeholder="请输入生产物料编码"></a-input>-->
              <a-select placeholder="请选择" v-decorator="['planid',{}]" @change="changePlan"><!-- 车间计划摘要 -->
                <a-select-option :key="0" :value="0">请选择</a-select-option>
                <a-select-option  v-for="(material,index) in planList" :key="material.id" :value="material.id">{{material.id}}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item label="计划详情编号" :labelCol="labelCol" :wrapperCol="wrapperCol" v-if="planFlag">
              <!--<a-input v-decorator="[ 'materialencodeid', validatorRules.materialencodeid]" placeholder="请输入生产物料编码"></a-input>-->
              <a-select placeholder="请选择" v-decorator="['shopplanid',validatorRules.shopplanid]" @change="changeShopplan"><!-- 车间计划摘要 -->
                <a-select-option  v-for="(material,index) in shopplanList" :key="material.id" :value="material.id">{{material.id}}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>

        <!--<a-form-item label="生产计划编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          &lt;!&ndash;<a-input v-decorator="[ 'materialencodeid', validatorRules.materialencodeid]" placeholder="请输入生产物料编码"></a-input>&ndash;&gt;
          <a-select placeholder="请选择" v-decorator="['planid',{}]" @change="changePlan">&lt;!&ndash; 车间计划摘要 &ndash;&gt;
            <a-select-option :key="0" :value="0">请选择</a-select-option>
            <a-select-option  v-for="(material,index) in planList" :key="material.id" :value="material.id">{{material.id}}</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item label="计划详情编号" :labelCol="labelCol" :wrapperCol="wrapperCol" v-if="planFlag">
          &lt;!&ndash;<a-input v-decorator="[ 'materialencodeid', validatorRules.materialencodeid]" placeholder="请输入生产物料编码"></a-input>&ndash;&gt;
          <a-select placeholder="请选择" v-decorator="['shopplanid',validatorRules.shopplanid]" @change="changeShopplan">&lt;!&ndash; 车间计划摘要 &ndash;&gt;
            <a-select-option  v-for="(material,index) in shopplanList" :key="material.id" :value="material.id">{{material.id}}</a-select-option>
          </a-select>
        </a-form-item>-->

        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="产品编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <!--<a-input v-decorator="[ 'materialencodeid', validatorRules.materialencodeid]" placeholder="请输入生产物料编码"></a-input>-->
              <a-select :disabled="materialFlag" placeholder="请选择" v-decorator="['materialencodeid',validatorRules.materialencodeid]">
                <a-select-option  v-for="(material,index) in materialList" :key="material.id" :value="material.id">{{material.productname}}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item label="生产车间编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <!--<a-input v-decorator="[ 'workshopid', validatorRules.workshopid]" placeholder="请输入生产车间编号"></a-input>-->
              <a-select :disabled="workshopFlag" placeholder="请选择" v-decorator="['workshopid',validatorRules.workshopid]" @change="handleWorkshopChange">
                <a-select-option  v-for="(shop,index) in workshopList" :key="shop.id" :value="shop.id">{{shop.wsname}}</a-select-option>
              </a-select>

            </a-form-item>
          </a-col>
        </a-row>

        <!--<a-form-item label="产品编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          &lt;!&ndash;<a-input v-decorator="[ 'materialencodeid', validatorRules.materialencodeid]" placeholder="请输入生产物料编码"></a-input>&ndash;&gt;
          <a-select :disabled="materialFlag" placeholder="请选择" v-decorator="['materialencodeid',validatorRules.materialencodeid]">
            <a-select-option  v-for="(material,index) in materialList" :key="material.id" :value="material.id">{{material.productname}}</a-select-option>
          </a-select>
        </a-form-item>
          
        <a-form-item label="生产车间编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          &lt;!&ndash;<a-input v-decorator="[ 'workshopid', validatorRules.workshopid]" placeholder="请输入生产车间编号"></a-input>&ndash;&gt;
          <a-select :disabled="workshopFlag" placeholder="请选择" v-decorator="['workshopid',validatorRules.workshopid]" @change="handleWorkshopChange">
            <a-select-option  v-for="(shop,index) in workshopList" :key="shop.id" :value="shop.id">{{shop.wsname}}</a-select-option>
          </a-select>

        </a-form-item>-->

        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="生产设备编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <!--<a-input v-decorator="[ 'workshopid', validatorRules.workshopid]" placeholder="请输入生产车间编号"></a-input>-->
              <a-select placeholder="请选择" v-decorator="['equipmentid',validatorRules.equipmentid]">
                <a-select-option  v-for="(ss,index) in rEquipmentList" :key="ss.id" :value="ss.id">{{ss.equipname}}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item label="工序编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <!--<a-input v-decorator="[ 'workprocessid', validatorRules.workprocessid]" placeholder="请输入工序编号"></a-input>-->
              <a-select placeholder="请选择" v-decorator="['workprocessid',validatorRules.workprocessid]">
                <a-select-option  v-for="(process,index) in processList" :key="process.id" :value="process.id">{{process.wpname}}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>

        <!--<a-form-item label="生产设备编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          &lt;!&ndash;<a-input v-decorator="[ 'workshopid', validatorRules.workshopid]" placeholder="请输入生产车间编号"></a-input>&ndash;&gt;
          <a-select placeholder="请选择" v-decorator="['equipmentid',validatorRules.equipmentid]">
            <a-select-option  v-for="(ss,index) in rEquipmentList" :key="ss.id" :value="ss.id">{{ss.equipname}}</a-select-option>
          </a-select>
        </a-form-item>
          
        <a-form-item label="工序编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          &lt;!&ndash;<a-input v-decorator="[ 'workprocessid', validatorRules.workprocessid]" placeholder="请输入工序编号"></a-input>&ndash;&gt;
          <a-select placeholder="请选择" v-decorator="['workprocessid',validatorRules.workprocessid]">
            <a-select-option  v-for="(process,index) in processList" :key="process.id" :value="process.id">{{process.wpname}}</a-select-option>
          </a-select>
        </a-form-item>-->

        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number :disabled="numberFlag" :min="0" v-decorator="[ 'productnumber', validatorRules.productnumber]" placeholder="请输入数量" style="width: 100%"/>
            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item label="操作员" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['salesman', validatorRules.salesman]" :trigger-change="true"/>
            </a-form-item>
          </a-col>
        </a-row>
          
        <!--<a-form-item label="数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number :disabled="numberFlag" :min="0" v-decorator="[ 'productnumber', validatorRules.productnumber]" placeholder="请输入数量" style="width: 100%"/>
        </a-form-item>

        <a-form-item label="业务员" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-select-user-by-dep v-decorator="['salesman', validatorRules.salesman]" :trigger-change="true"/>
        </a-form-item>-->
          
        <!--<a-form-item label="已完成数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number :min="0" v-decorator="[ 'finishednumber', validatorRules.finishednumber]" placeholder="请输入已完成数量" style="width: 100%"/>
        </a-form-item>-->

        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="计划开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date :disabled="planstarttimeFlag" placeholder="请选择计划开始时间" v-decorator="[ 'planstarttime', validatorRules.planstarttime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item label="计划结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择计划结束时间" v-decorator="[ 'planendtime', validatorRules.planendtime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
        </a-row>
          
        <!--<a-form-item label="计划开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date :disabled="planstarttimeFlag" placeholder="请选择计划开始时间" v-decorator="[ 'planstarttime', validatorRules.planstarttime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="计划结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择计划结束时间" v-decorator="[ 'planendtime', validatorRules.planendtime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>-->
          
        <!--<a-form-item label="实际开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择实际开始时间" v-decorator="[ 'realstarttime', validatorRules.realstarttime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="实际结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择实际结束时间" v-decorator="[ 'realendtime', validatorRules.realendtime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>-->

        <!--<a-row :gutter="24">
          <a-col :span="24">
            <a-form-item label="需要工时" :labelCol="labelCol1" :wrapperCol="wrapperCol1">
              <a-input-number :min="0" v-decorator="[ 'needworkhour', validatorRules.needworkhour]" placeholder="请输入需要工时" style="width: 100%"/>
            </a-form-item>
          </a-col>
        </a-row>-->

        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item label="备注" :labelCol="labelCol1" :wrapperCol="wrapperCol1">
              <a-textarea v-decorator="['disp', validatorRules.disp]" rows="4" placeholder="请输入备注"/>
            </a-form-item>
          </a-col>
        </a-row>
          
        <!--<a-form-item label="需要工时" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number :min="0" v-decorator="[ 'needworkhour', validatorRules.needworkhour]" placeholder="请输入需要工时" style="width: 100%"/>
        </a-form-item>-->
          
        <!--<a-form-item label="是否外协" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['isoutsource', validatorRules.isoutsource]" :trigger-change="true" dictCode="IsOutsource" placeholder="请选择是否外协"/>
        </a-form-item>-->
          
        <!--<a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['state']" :trigger-change="true" dictCode="State" placeholder="请选择状态"/>
        </a-form-item>-->
          
        <!--<a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['disp', validatorRules.disp]" rows="4" placeholder="请输入备注"/>
        </a-form-item>-->
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  
  export default {
    name: "MProductTaskOrderModal",
    components: { 
      JDate,
      JDictSelectTag,
      JSelectUserByDep,
    },
    data () {
      return {
        /*数量可编辑状态  默认false，选择计划明细是为true(不可编辑)*/
        numberFlag:false,
        /*物料可编辑状态  默认false，选择计划明细是为true(不可编辑)*/
        materialFlag:false,
        /*生产车间可编辑状态  默认false，选择计划明细是为true(不可编辑)*/
        workshopFlag:false,
        /*计划开始时间可编辑状态  默认false，选择计划明细是为true(不可编辑)*/
        planstarttimeFlag:false,
        /*车间计划标识，未选或请选择为false,否则true,默认false*/
        planFlag:false,
        /*车间编号*/
        workshopid:'',
        validatorRules:{
          //  sex:{initialValue:((!this.model.sex)?"": (this.model.sex+""))}
        },
        /*车间计划列表*/
        planList:[],
        /*车间计划明细列表*/
        shopplanList:[],
        /*工序列表*/
        processList:[],
        /*车间信息列表*/
        workshopList: [],
        /*物料信息列表*/
        materialList: [],
        /*设备列表*/
        rEquipmentList: [],
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 8 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        labelCol1: {
          xs: { span: 24 },
          sm: { span: 4 },
        },
        wrapperCol1: {
          xs: { span: 24 },
          sm: { span: 20 },
        },

        confirmLoading: false,
        validatorRules:{
          materialencodeid:{
            rules: [{
              required: true, message: '请选择物料!'
            }]
          },
          shopplanid:{
            rules: [{
              required: true, message: '请选择车间计划明细!'
            }]
          },
          workshopid:{
            rules: [{
              required: true, message: '请选择生产车间!'
            }]
          },
          equipmentid:{
            rules: [{
              required: true, message: '请选择设备!'
            }]
          },
          workprocessid:{},
          productnumber:{
            rules: [{
              required: true, message: '请输入数量!'
            }]
          },
          salesman:{
            rules: [{
              required: true, message: '请选择操作员!'
            }]
          },
          finishednumber:{},
          planstarttime:{
            rules: [{
              required: true, message: '请选择计划开始时间!'
            }]
          },
          planendtime:{
            rules: [{
              required: true, message: '请选择计划结束时间!'
            }]
          },
          realstarttime:{},
          realendtime:{},
          needworkhour:{},
          isoutsource:{},
          state:{},
          disp:{},
        },
        url: {
          add: "/system/mProductTaskOrder/add",
          edit: "/system/mProductTaskOrder/edit",
        }
     
      }
    },
    created () {
    },
    mounted () {

      this.getMaterialList ()
      this.getWorkshopList()
      this.getProcessList()
      this.getPlanList ()
    },
    methods: {
      /*选择车间计划明细查询详情*/
      changeShopplan (value) {
        console.log("车间计划详情值："+value)
        if (value != 0 && value != '0') {
          this.numberFlag = true //数量编辑状态
          this.materialFlag = true //物料编辑状态
          this.workshopFlag = true //生产车间编辑状态
          this.planstarttimeFlag = true //计划开始时间编辑状态
          getAction('/system/mShopplan/queryById',{"id":value}).then((res)=>{
            if(res.success){
              /*清空*/
              /*this.shopplanList.splice(0)
              this.shopplanList = res.result*/
              console.log("计划详情："+res.result.number)
              //this.model.productnumber = res.result.number
              this.form.setFieldsValue({
                'productnumber':res.result.number,
                'materialencodeid':res.result.materialencode,
                'workshopid':res.result.workshopid,
                'planstarttime':res.result.plantime
              });
              this.handleWorkshopChange(res.result.workshopid)//车间设备
              //console.log(JSON.stringify(this.model))
            }
          });

        } else {
          //this.numberFlag = true
          /*清空*/
          //this.shopplanList.splice(0)
        }
      },
      /*根据车间计划查询明细*/
      queryListBySohpplanid (shopplanid) {
        getAction('/system/mShopplan/queryListByMainId',{"shopplanid":shopplanid}).then((res)=>{
          if(res.success){
            /*清空*/
            this.shopplanList.splice(0)
            this.shopplanList = res.result
          }
        });
      },
      /*选择车间计划查询明细*/
      changePlan (value) {
        console.log("车间计划值："+value)
        if (value != 0 && value != '0') {
          this.planFlag = true
          /*根据车间计划选择*/
          this.queryListBySohpplanid (value)
        } else {


          this.numberFlag = false //数量编辑状态
          this.materialFlag = false //物料编辑状态
          this.workshopFlag = false //生产车间编辑状态
          this.planstarttimeFlag = false //计划开始时间编辑状态
          this.form.setFieldsValue({
            'productnumber':'',
            'materialencodeid':'',
            'workshopid':'',
            'planstarttime':''
          });

          this.planFlag = false
          /*清空*/
          this.shopplanList.splice(0)
        }
      },
      /*获取车间计划下拉框内容*/
      getPlanList () {
        getAction('/system/mShopplanabstract/getAll',null).then((res)=>{
          if(res.success){
            /*清空*/
            this.planList.splice(0)
            this.planList = res.result
          }
        });
      },
      /*获取工序下拉框内容*/
      getProcessList () {
        getAction('/system/mWorkprocess/getProcessList',null).then((res)=>{
          if(res.success){
            /*清空*/
            this.processList.splice(0)
            this.processList = res.result
          }
        });
      },
      /*获取车间下拉框内容*/
      getWorkshopList () {
        getAction('/system/mWorkShop/getAll',null).then((res)=>{
          if(res.success){
            /*清空*/
            this.workshopList.splice(0)
            this.workshopList = res.result
          }
        });
      },
      /*获取物料下拉框内容*/
      getMaterialList () {
        getAction('/system/mProductMap/getAllList',null).then((res)=>{
          if(res.success){
            /*清空*/
            this.materialList.splice(0)
            this.materialList = res.result
          }
        });
      },
      /*联动   车间变化*/
      handleWorkshopChange(value) {
        //console.log("车间编号："+value)
        /*this.cities = cityData[value];
        this.secondCity = cityData[value][0];*/
        getAction('/system/rEquipment/getListByWid',{'workshopid':value}).then((res)=>{
          if(res.success){
            /*清空*/
            //console.log("设备信息："+JSON.stringify(res.result))
            this.rEquipmentList.splice(0)
            this.rEquipmentList = res.result
          }
        });
      },
      add () {
        this.edit({});
      },
      edit (record) {
        //console.log("record:"+JSON.stringify(record))
        this.workshopid = record.workshopid
        this.handleWorkshopChange(this.workshopid)
        if (record.shopplanid != null && record.shopplanid != '') {
          /*初始化*/
          this.numberFlag = true //数量编辑状态
          this.materialFlag = true //物料编辑状态
          this.workshopFlag = true //生产车间编辑状态
          this.planstarttimeFlag = true //计划开始时间编辑状态
          this.planFlag = true;

          getAction('/system/mShopplan/queryById',{"id":record.shopplanid}).then((res)=>{
            if(res.success){
              //record.planid = res.result.shopplanid
              this.form.setFieldsValue({
                'planid':res.result.shopplanid,
              });
              this.queryListBySohpplanid (record.planid)
            }
          });


        }

        this.form.resetFields();
        this.model = Object.assign({}, record);
        //this.model = {'productnumber':10000};
        //console.log("编辑："+JSON.stringify(this.model))
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'shopplanid','materialencodeid','workshopid','equipmentid','workprocessid','productnumber','salesman','finishednumber','planstarttime','planendtime','realstarttime','realendtime','needworkhour','isoutsource','state','disp'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
        /*初始化*/
        this.numberFlag = false //数量编辑状态
        this.materialFlag = false //物料编辑状态
        this.workshopFlag = false //生产车间编辑状态
        this.planstarttimeFlag = false //计划开始时间编辑状态
        this.planFlag = false; this.shopplanList.splice(0)
        this.getMaterialList ()
        this.getWorkshopList()
        this.getProcessList()
        this.getPlanList ()

      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'materialencodeid','workshopid','equipmentid','workprocessid','productnumber','salesman','finishednumber','planstarttime','planendtime','realstarttime','realendtime','needworkhour','isoutsource','state','disp'))
      }
      
    }
  }
</script>