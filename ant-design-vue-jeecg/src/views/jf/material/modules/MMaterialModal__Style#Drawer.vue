<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="物料名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'materialname', validatorRules.materialname]" placeholder="请输入物料名称"></a-input>
        </a-form-item>
        <a-form-item label="别名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'alias', validatorRules.alias]" placeholder="请输入别名"></a-input>
        </a-form-item>
        <a-form-item label="英文名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'englishname', validatorRules.englishname]" placeholder="请输入英文名"></a-input>
        </a-form-item>
        <a-form-item label="检索码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'indexcode', validatorRules.indexcode]" placeholder="请输入检索码"></a-input>
        </a-form-item>
        <a-form-item label="等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'level', validatorRules.level]" placeholder="请输入等级"></a-input>
        </a-form-item>
        <a-form-item label="型号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'model', validatorRules.model]" placeholder="请输入型号"></a-input>
        </a-form-item>
        <a-form-item label="规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'special', validatorRules.special]" placeholder="请输入规格"></a-input>
        </a-form-item>
        <a-form-item label="通用规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'gspecial', validatorRules.gspecial]" placeholder="请输入通用规格"></a-input>
        </a-form-item>
        <a-form-item label="商标" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'brand', validatorRules.brand]" placeholder="请输入商标"></a-input>
        </a-form-item>
        <a-form-item label="ABC类" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'abccategory', validatorRules.abccategory]" placeholder="请输入ABC类"></a-input>
        </a-form-item>
        <a-form-item label="颜色" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'color', validatorRules.color]" placeholder="请输入颜色"></a-input>
        </a-form-item>
        <a-form-item label="计算特性1" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'spe1', validatorRules.spe1]" placeholder="请输入计算特性1" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="计算特性2" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'spe2', validatorRules.spe2]" placeholder="请输入计算特性2" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="条形码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'barcode', validatorRules.barcode]" placeholder="请输入条形码"></a-input>
        </a-form-item>
        <a-form-item label="物料分类编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['materialtypeencode']" :trigger-change="true" dictCode="" placeholder="请选择物料分类编码"/>
        </a-form-item>
        <a-form-item label="单位Id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'unitid', validatorRules.unitid]" placeholder="请输入单位Id"></a-input>
        </a-form-item>
        <a-form-item label="是否虚拟件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['isvirtalpart']" :trigger-change="true" dictCode="IsVirtalPart" placeholder="请选择是否虚拟件"/>
        </a-form-item>
        <a-form-item label="是否外购件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['ispurchasepart']" :trigger-change="true" dictCode="IsPurchasePart" placeholder="请选择是否外购件"/>
        </a-form-item>
        <a-form-item label="是否通用件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['isgeneralpart']" :trigger-change="true" dictCode="IsGeneralPart" placeholder="请选择是否通用件"/>
        </a-form-item>
        <a-form-item label="安全库存" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'minwhstorge', validatorRules.minwhstorge]" placeholder="请输入安全库存" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="最高库存" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'maxwhstorge', validatorRules.maxwhstorge]" placeholder="请输入最高库存" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="当前库存" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'currentwhstogre', validatorRules.currentwhstogre]" placeholder="请输入当前库存" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="允许销售" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['issell']" :trigger-change="true" dictCode="IsSell" placeholder="请选择允许销售"/>
        </a-form-item>
        <a-form-item label="序列号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'serialnumber', validatorRules.serialnumber]" placeholder="请输入序列号"></a-input>
        </a-form-item>
        <a-form-item label="采购单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'purchaseorganize', validatorRules.purchaseorganize]" placeholder="请输入采购单位"></a-input>
        </a-form-item>
        <a-form-item label="产地" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'productaddress', validatorRules.productaddress]" placeholder="请输入产地"></a-input>
        </a-form-item>
        <a-form-item label="是否入库检验" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['iswhinspec']" :trigger-change="true" dictCode="IsWHInSpec" placeholder="请选择是否入库检验"/>
        </a-form-item>
        <a-form-item label="成本日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择成本日期" v-decorator="[ 'costdate', validatorRules.costdate]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="标准成本价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'standardcost', validatorRules.standardcost]" placeholder="请输入标准成本价" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="销售价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'salescost', validatorRules.salescost]" placeholder="请输入销售价" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="采购价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'purchasecost', validatorRules.purchasecost]" placeholder="请输入采购价" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="采购提前天数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'preparedays', validatorRules.preparedays]" placeholder="请输入采购提前天数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="工序编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['processid']" :trigger-change="true" dictCode="" placeholder="请选择工序编号"/>
        </a-form-item>
        <a-form-item label="是否停用" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['isstop']" :trigger-change="true" dictCode="IsStop" placeholder="请选择是否停用"/>
        </a-form-item>
        <a-form-item label="起初数值" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'startnum', validatorRules.startnum]" placeholder="请输入起初数值" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['figurepath']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['disp']" rows="4" placeholder="请输入备注"/>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JUpload from '@/components/jeecg/JUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "MMaterialModal",
    components: { 
      JDate,
      JUpload,
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
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
        validatorRules:{
        materialname:{},
        alias:{},
        englishname:{},
        indexcode:{},
        level:{},
        model:{},
        special:{},
        gspecial:{},
        brand:{},
        abccategory:{},
        color:{},
        spe1:{},
        spe2:{},
        barcode:{},
        materialtypeencode:{},
        unitid:{},
        isvirtalpart:{},
        ispurchasepart:{},
        isgeneralpart:{},
        minwhstorge:{},
        maxwhstorge:{},
        currentwhstogre:{},
        issell:{},
        serialnumber:{},
        purchaseorganize:{},
        productaddress:{},
        iswhinspec:{},
        costdate:{},
        standardcost:{},
        salescost:{},
        purchasecost:{},
        preparedays:{},
        processid:{},
        isstop:{},
        startnum:{},
        figurepath:{},
        disp:{},
        },
        url: {
          add: "/system/mMaterial/add",
          edit: "/system/mMaterial/edit",
        }
     
      }
    },
    created () {
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
          this.form.setFieldsValue(pick(this.model,'materialname','alias','englishname','indexcode','level','model','special','gspecial','brand','abccategory','color','spe1','spe2','barcode','materialtypeencode','unitid','isvirtalpart','ispurchasepart','isgeneralpart','minwhstorge','maxwhstorge','currentwhstogre','issell','serialnumber','purchaseorganize','productaddress','iswhinspec','costdate','standardcost','salescost','purchasecost','preparedays','processid','isstop','startnum','figurepath','disp'))
        })
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
        this.form.setFieldsValue(pick(row,'materialname','alias','englishname','indexcode','level','model','special','gspecial','brand','abccategory','color','spe1','spe2','barcode','materialtypeencode','unitid','isvirtalpart','ispurchasepart','isgeneralpart','minwhstorge','maxwhstorge','currentwhstogre','issell','serialnumber','purchaseorganize','productaddress','iswhinspec','costdate','standardcost','salescost','purchasecost','preparedays','processid','isstop','startnum','figurepath','disp'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>