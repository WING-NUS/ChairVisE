<template>
  <div>
    <el-alert
      title="You need to login-in to view the page"
      type="error"
      v-if="!isLogin && !isAppLoading"
    >
      &nbsp;<el-button type="warning" plain size="mini" @click="navigateToHomePage">Return to the Home Page</el-button>
    </el-alert>
    <div v-if="isLogin">
      <mapping-tool v-if="isReadyForMapping" ref="mapTool"></mapping-tool>
      <div v-else class="upload-box">
        <el-select v-model="formatType" placeholder="Format Type">
          <el-option :key="'EasyChair'" :label="'EasyChair'" :value="1"></el-option>
          <el-option :key="'SoftConf'" :label="'SoftConf'" :value="2"></el-option>
        </el-select>
        <el-select v-model="tableType" placeholder="Table Type">
          <el-option v-for="(schema, idx) in dbSchemas"
                     :key="schema.name"
                     :label="schema.name"
                     :value="idx">
          </el-option>
        </el-select>
        <el-select v-model="hasHeader" placeholder="Has header?">
          <el-option :key="'Yes'" :label="'Yes'" :value="true"></el-option>
          <el-option :key="'No'" :label="'No'" :value="false"></el-option>
        </el-select>
        <el-select v-model="predefinedMappingId" placeholder="Predefined Mapping">
          <el-option v-for="(mapping, idx) in predefinedMappings"
                     :key="mapping.name"
                     :label="mapping.name"
                     :value="idx">
          </el-option>
        </el-select>
        <el-upload v-if="isReadyForUpload" drag action=""
                   :auto-upload="false"
                   :show-file-list="false"
                   :multiple="false"
                   :on-change="fileUploadHandler">
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">Drop file here or <em>click to upload</em></div>
          <div class="el-upload__tip" slot="tip">Please upload .csv files with filed names.</div>
        </el-upload>
      </div>
    </div>
  </div>
</template>

<script>
  import MappingTool from "@/components/MappingTool.vue";
  import Papa from "papaparse";
  import {REVIEW_DATE_DAY_FIELD, REVIEW_DATE_TIME_FIELD, REVIEW_TABLE_ID} from "@/common/const"
  import {deepCopy} from "@/common/utility"
  import PredefinedMappings from "@/store/data/predefinedMapping"
  import moment from "moment"

  export default {
    name: "ImportData",
    data() {
      return {
        predefinedMappings: PredefinedMappings
      };
    },
    mounted() {
      // When page is loaded, fetch all the database fields
      this.$store.dispatch('fetchDBMetaDataEntities');
    },
    computed: {
      isLogin() {
        return this.$store.state.userInfo.isLogin;
      },
      isAppLoading: function () {
        return this.$store.state.isPageLoading || this.$store.state.dbMetaData.entitiesStatus.isLoading;
      },
      dbSchemas: function () {
        return this.$store.state.dbMetaData.entities;
      },
      formatType: {
        get: function () {
          return this.$store.state.dataMapping.data.formatType;
        },
        set: function (newValue) {
          this.$store.commit("setFormatType", newValue);
        }
      },
      tableType: {
        get: function () {
          return this.$store.state.dataMapping.data.tableType;
        },
        set: function (newValue) {
          let dbSchema = deepCopy(this.dbSchemas[newValue]);
          if (newValue === REVIEW_TABLE_ID) {
            dbSchema.fieldMetaDataList.push(REVIEW_DATE_DAY_FIELD);
            dbSchema.fieldMetaDataList.push(REVIEW_DATE_TIME_FIELD);
          }
          this.$store.commit("setTableType", newValue);
          this.$store.commit("setDBSchema", dbSchema);
        }
      },
      hasHeader: {
        get: function () {
          return this.$store.state.dataMapping.data.hasHeader;
        },
        set: function (newValue) {
          this.$store.commit("setHasHeader", newValue);
        }
      },
      predefinedMappingId: {
        get: function () {
          return this.$store.state.dataMapping.data.predefinedMappingId;
        },
        set: function (newValue) {
          this.$store.commit("setPredefinedMapping", {id: newValue, mapping: PredefinedMappings[newValue].mapping});
        }
      },
      isReadyForMapping: function () {
        return this.$store.state.dataMapping.hasFileUploaded
          && this.$store.state.dataMapping.hasFormatTypeSpecified
          && this.$store.state.dataMapping.hasTableTypeSelected
          && this.$store.state.dataMapping.hasHeaderSpecified
          && this.$store.state.dataMapping.hasPredefinedSpecified;
      },
      uploaded: function () {
        return this.$store.state.dataMapping.hasFileUploaded;
      },
      isReadyForUpload: function () {
        return this.$store.state.dataMapping.hasFormatTypeSpecified
         && this.$store.state.dataMapping.hasTableTypeSelected
         && this.$store.state.dataMapping.hasHeaderSpecified
         && this.$store.state.dataMapping.hasPredefinedSpecified;
      }
    },
    methods: {
      navigateToHomePage() {
        this.$router.replace("/home");
      },
      fileUploadHandler: function (file) {
        // show loading and go parsing
        this.$store.commit("setPageLoadingStatus", true);
        Papa.parse(file.raw, {
          // ignoring empty lines in csv file
          skipEmptyLines: true,
          complete: function (result) {
          var res=result;
          var res2=res.data;
          //console.log((res.data)[0]);
          //console.log(res.data);
          //console.log(this.$store.state.dataMapping.data.tableType)
          //author file preprocessing

          if( this.$store.state.dataMapping.data.tableType=="0" ){
              //ACL file preprocessing
              if(this.$store.state.dataMapping.data.formatType=="2"){
              var authorres=[];
              authorres.push(["submission #","first name","last name","email","country","organization","Web page","person #","corresponding?"]);

              for (var i = 1; i < res2.length; i++) {
                var x = res2[i];
                //console.log(x);
                var k=0,j=14,element=[],corr="",country="";
                while(x[j]!=""){
                    if(x[j]==x[65] && x[j+1]==x[66]){
                    corr = "yes";
                    country=x[78];
                    }
                    else {
                    corr="no";
                    country="";
                    }
                    element[k]= [x[0],x[j],x[j+1],x[j+2],country,x[j+3],"","",corr];
                    authorres.push(element[k]);
                    k+=1;
                    j=14+k*5;
                 }
                //var element1=[x[0],x[14],x[15],x[16],"",x[17],"",""];

              }
                res2=authorres;
                //console.log(authorres)
              }
                //author anonymization - Both formats
                var convertstring=require("convert-string");
                for(var m=1;m<res2.length;m++){
                    var conv1=convertstring.stringToBytes(res2[m][1]);
                    var conv2=convertstring.stringToBytes(res2[m][2]);
                    var firstname="";
                    var lastname="";
                    for(var a=0;a<conv1.length;a++){
                        firstname=firstname.concat(String(conv1[a]+18));
                    }
                    for(var w=0;w<conv2.length;w++){
                        lastname=lastname.concat(String(conv2[w]+18));
                    }
                    res2[m][1]=firstname;
                    res2[m][2]=lastname;
                }
                //console.log(res2);
           }

          //review file preprocessing
          if( this.$store.state.dataMapping.data.tableType=="1" ){
              if(this.$store.state.dataMapping.data.formatType=="2"){
                var reviewres=[];
                reviewres.push(["Review Id","Submission Id", "Num Review Assignment", "Reviewer Name", "Expertise Level", "Review Comment","Confidence Level", "Overall Evaluation Score", "Column 9","Column 10","Column 11","Column 12", "Day of the Review Date", "Time of the Review Date", "Has Recommended for the Best Paper"]);

                for (var q = 1; q < res2.length; q++) {
                    var z = res2[q];
                    z[32]="confidence: "+z[32];
                    //console.log(typeof(z[7]));
                    //var str=z[7].toString();
                    var date_time=z[7].split(" ");
                    //console.log(date_time);
                    var date=date_time[0];
                    var time=date_time[1].split(":")[0]+":"+date_time[1].split(":")[1];
                    //console.log(date,time);
                    element=["",z[0],"","","",z[38],z[32],z[31],"","","","",date,time,""];
                    reviewres.push(element);
                }
                res2=reviewres;
                //console.log(reviewres);
             }

             //author anonymization - JCDL
             if(this.$store.state.dataMapping.data.formatType=="1"){
                var convert_string=require("convert-string");
                for(var index=1;index<res2.length;index++){
                    var convert=convert_string.stringToBytes(res2[index][3]);
                    var name="";
                    for(var idx=0;idx<convert.length;idx++){
                        name=name.concat(String(convert[idx]+18));
                    }
                    res2[index][3]=name;
                }
             }
             //console.log(res2);
           }

           //ACL submission file processing
          if( this.$store.state.dataMapping.data.tableType=="2" ){
              if(this.$store.state.dataMapping.data.formatType=="2"){
              var submissionres=[];
              submissionres.push(["#", "track #", "track name", "title", "authors", "submitted","last updated", "form fields", "keywords", "decision", "notified", "reviews sent", "abstract"]);

              for (var l = 1; l < res2.length; l++) {
                var y = res2[l];
                var dt = moment(y[10], "D MMM YYYY HH:mm:ss").format("YYYY-M-D H:m");
                if(y[6].includes("Reject")){y[6]="reject";}
                else {y[6]="accept";}
                //console.log(x);
                element=[y[0],"",y[4],y[2],y[3],dt,dt,"",y[13],y[6],"","",y[9]];
                submissionres.push(element);
              }
                res2=submissionres;
                //console.log(submissionres);
             }
                //author anonymization
                //console.log(res2);
           }
          console.log(res2);
            this.$store.commit("setUploadedFile",res2);
            this.$store.commit("setPageLoadingStatus", false);
          }.bind(this)
        });
      }
    },
    components: {
      MappingTool
    }
  };
</script>

<style scoped>
  .upload-box {
    width: 100%;
    text-align: center;
    padding-top: 100px;
  }

  .upload-box .el-select {
    margin-top: 20px;
    margin-left: 15px;
  }

  .button-row {
    margin-top: 30px;
  }
</style>
