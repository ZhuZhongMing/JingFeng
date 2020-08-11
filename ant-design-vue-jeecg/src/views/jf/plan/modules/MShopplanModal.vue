<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
      
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="车间计划Id">
          <a-input placeholder="请输入车间计划Id" v-decorator="['shopplanid', {}]" />
        </a-form-item>-->
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="主计划流水号">
          <a-input placeholder="请输入主计划流水号" v-decorator="['mainplanid', {}]" />
        </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="订单编号">
          <!--<a-input placeholder="请输入订单编号" v-decorator="['orderid', {}]" />-->

          <a-select placeholder="请选择" v-decorator="['orderid', validatorRules.orderid]">
            <a-select-option  v-for="(shop,index) in orderList" :key="shop.id" :value="shop.id">{{shop.id}}</a-select-option>
          </a-select>

        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="车间编号">
          <!--<a-input placeholder="请输入车间编号" v-decorator="['workshopid', {}]" />-->

          <a-select placeholder="请选择" v-decorator="['workshopid', validatorRules.workshopid]">
            <a-select-option  v-for="(shop,index) in workshopList" :key="shop.id" :value="shop.id">{{shop.wsname}}</a-select-option>
          </a-select>

        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="待加工物料编号">
          <!--<a-input placeholder="请输入待加工物料编号" v-decorator="['materialencode', {}]" />-->

          <a-select placeholder="请选择" v-decorator="['materialencode', validatorRules.materialencode]">
            <a-select-option  v-for="(material,index) in materialList" :key="material.id" :value="material.id">{{material.materialname}}</a-select-option>
          </a-select>

        </a-form-item>
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="加工类型">
          <a-input placeholder="请输入加工类型" v-decorator="['stype', {}]" />
        </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="数量">
          <a-input-number :min="1" v-decorator="[ 'number', validatorRules.number]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="安排时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'plantime', validatorRules.plantime]" />
        </a-form-item>
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="开始时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'starttime', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="完成时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'finishtime', {}]" />
        </a-form-item>-->

        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="计划状态">
          <a-input placeholder="请输入计划状态" v-decorator="['planstatus', {}]" />
        </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="备注">
          <!--<a-input placeholder="请输入备注" v-decorator="['disp', {}]" />-->
          <a-textarea v-decorator="['disp', {}]" rows="4" placeholder="请输入备注"/>
        </a-form-item>
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="删除标识0-正常,1-已删除">
          <a-input placeholder="请输入删除标识0-正常,1-已删除" v-decorator="['delFlag', {}]" />
        </a-form-item>-->
		
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "MShopplanModal",
    data () {
      return {
        /*订单信息列表*/
        orderList: [],
        /*物料信息列表*/
        materialList: [],
        /*车间信息列表*/
        workshopList: [],
        title:"操作",
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
          orderid:{
            /*rules: [{
              required: true, message: '请选择订单!'
            }]*/
          },
          workshopid:{
            rules: [{
              required: true, message: '请选择生产车间!'
            }]
          },
          materialencode:{
            rules: [{
              required: true, message: '请选择代加工物料!'
            }]
          },
          number:{
            rules: [{
              required: true, message: '请输入数量!'
            }]
          },
          plantime:{
            rules: [{
              required: true, message: '请选择计划时间!'
            }]
          },
        },
        url: {
          add: "/system/mShopplan/add",
          edit: "/system/mShopplan/edit",
        },
      }
    },
    created () {
    },
    mounted () {
      this.getMaterialList ()
      this.getWorkshopList()
      this.getOrderList()
    },
    methods: {
      /*获取订单下拉内容*/
      getOrderList () {
        getAction('/system/mOrder/getAll',null).then((res)=>{
          if(res.success){
            /*清空*/
            this.orderList.splice(0)
            this.orderList = res.result
          }
        });
      },
      /*获取物料下拉框内容*/
      getMaterialList () {
        getAction('/system/mMaterial/getMaterialList',null).then((res)=>{
          if(res.success){
            /*清空*/
            this.materialList.splice(0)
            this.materialList = res.result
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
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'shopplanid','workshopid','materialencode','stype','number','mainplanid','orderid','planstatus','disp','delFlag'))
		  //时间格式化
          this.form.setFieldsValue({plantime:this.model.plantime?moment(this.model.plantime):null})
          this.form.setFieldsValue({starttime:this.model.starttime?moment(this.model.starttime):null})
          this.form.setFieldsValue({finishtime:this.model.finishtime?moment(this.model.finishtime):null})
        });

      },
      close () {
        this.$emit('close');
        this.visible = false;
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
            //时间格式化
            formData.plantime = formData.plantime?formData.plantime.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.starttime = formData.starttime?formData.starttime.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.finishtime = formData.finishtime?formData.finishtime.format('YYYY-MM-DD HH:mm:ss'):null;
            
            console.log(formData)
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


    }
  }
</script>

<style lang="less" scoped>

</style>