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
          label="所属流水线设备编号">
          <a-input placeholder="请输入所属流水线设备编号" v-decorator="['asequipid', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="所属类别编号">
          <a-input placeholder="请输入所属类别编号" v-decorator="['categoryid', {}]" />
        </a-form-item>-->
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="删除标识0-正常,1-已删除">
          <a-input placeholder="请输入删除标识0-正常,1-已删除" v-decorator="['delFlag', {}]" />
        </a-form-item>-->
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="所属部门编号">
          &lt;!&ndash;<a-input placeholder="请输入所属部门编号" v-decorator="['deptid', {}]" />&ndash;&gt;
          <j-select-depart v-decorator="['deptid']" :trigger-change="true"/>
        </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="设备编号">
          <a-input :disabled="true" placeholder="请输入设备编号" v-decorator="['id', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="设备名称">
          <a-input placeholder="请输入设备名称" v-decorator="['equipname', {}]" />
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
          label="设备用途">
          <a-input placeholder="请输入设备用途" v-decorator="['rusage', {}]" />
        </a-form-item>
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="所属流水工序号">
          <a-input-number v-decorator="[ 'location', {}]" />
        </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="制造厂家">
          <a-input placeholder="请输入制造厂家" v-decorator="['manufacturer', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="产出系数">
          <a-input-number :min="0" v-decorator="[ 'outcoefficient', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="产出类型">
          <a-input placeholder="请输入产出类型" v-decorator="['outtype', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="放置位置">
          <a-input placeholder="请输入放置位置" v-decorator="['placement', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="功率">
          <a-input-number :min="0" v-decorator="[ 'power', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="额定电压">
          <a-input-number :min="0" v-decorator="[ 'ratedVoltage', {}]" />
        </a-form-item>
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="状态">
          <a-input placeholder="请输入状态" v-decorator="['state', {}]" />
        </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="备注">
          <!--<a-input placeholder="请输入备注" v-decorator="['disp', {}]" />-->
          <a-textarea v-decorator="['disp', {}]" rows="4" placeholder="请输入备注"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  import moment from "moment"

  export default {
    name: "REquipmentModal",
    components: {
      JSelectDepart,
    },
    data () {
      return {
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
          /*workshopid:{rule: [{ required: true, message: '请选择车间!'}]},*/
          workshopid:{
            rules: [{
              required: true, message: '请选择车间!'
            }]
          },
        },
        url: {
          add: "/system/rEquipment/add",
          edit: "/system/rEquipment/edit",
        },
      }
    },
    created () {
    },
    mounted () {
      this.getWorkshopList()
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'id','asequipid','categoryid','delFlag','deptid','disp','equipname','location','manufacturer','outcoefficient','outtype','placement','power','ratedVoltage','state','rusage','workshopid'))
		  //时间格式化
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


    }
  }
</script>

<style lang="less" scoped>

</style>