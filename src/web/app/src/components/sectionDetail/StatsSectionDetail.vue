<template>
  <basic-section-detail :section-detail="sectionDetail" :presentation-id="presentationId" :has-data="hasData"
                        :version="version"
                        :extraFormItemsRules="{}"
                        @update-visualisation="updateVisualisation">
    <el-table
      :data="tableData"
      stripe
      style="width: 100%">

      <el-table-column
        prop="type"
        label="Type" width="200">
      </el-table-column>

      <el-table-column
        prop="value"
        label="Value" width="100">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top-start">
            <p> No. of Reviewers: {{ scope.row.numIds }}</p>
            <div slot="reference" class="name-wrapper">
              <el-button class="hovered-text" type="text"> {{ scope.row.value }} </el-button> 
            </div>
          </el-popover>
        </template>
      </el-table-column>

     <el-table-column
        prop="reviewer"
        label="Corresponding Reviewer ID">
        <template slot-scope="scope">
        {{ scope.row.reviewer }}
        </template>
      </el-table-column>

    </el-table>
    <template slot="extraFormItems" slot-scope="slotProps">
      <el-form-item label="Type" v-if="slotProps.isInAdvancedMode">
        <el-select placeholder="type" v-model="slotProps.extraData.types" multiple>
          <el-option label="Min" value="min" reviewer="min"></el-option>
          <el-option label="Max" value="max" reviewer="max"></el-option>
          <el-option label="Sum" value="sum" reviewer="sum"></el-option>
          <el-option label="Average" value="avg" reviewer="avg"></el-option>
          <el-option label="Median" value="median" reviewer="median"></el-option>
          <el-option label="Standard Deviation" value="std" reviewer="std"></el-option>
        </el-select>
      </el-form-item>
    </template>
  </basic-section-detail>
</template>

<script>
  import {max, mean, median, min, standardDeviation, sum} from 'simple-statistics'
  import BasicSectionDetail from '@/components/sectionDetail/BasicSectionDetail.vue'

  export default {
    name: "StatsSectionDetail",

    props: {
      sectionDetail: {
        type: Object,
        required: true
      },
      presentationId: {
        type: String,
        required: true
      },
      version: {
        type: String,
        required: true
      }
    },

    data() {
      return {
        tableData: []
      }
    },

    computed: {
      hasData() {
        return this.tableData.length !== 0;
      }
    },

    methods: {
      updateVisualisation({result, selections, extraData}) {
        this.tableData = [];
        if (result.length === 0) {
          return
        }
        let data = result.map(r => r[selections[1].rename]);

        extraData.types.forEach(t => {
          var rIds = [];
          var rId;
          var stringBuilder;
          switch (t) {
            case 'min':
              stringBuilder = "";
              rIds = result.filter(r => r[selections[1].rename] == min(data)).map(
                r => r[selections[0].rename]);

              for (rId of rIds) {
                stringBuilder = stringBuilder + rId + ", ";
              }
              stringBuilder = stringBuilder.substring(0, stringBuilder.length-2);
              
              this.tableData.push({
                type: 'Min',
                value: min(data),
                numIds: rIds.length,
                reviewer: stringBuilder,
              });
              break;
            case 'max':
              stringBuilder = "";
              rIds = result.filter(r => r[selections[1].rename] == max(data)).map(
                r => r[selections[0].rename]);
              for (rId of rIds) {
                stringBuilder = stringBuilder + rId + ", ";
              }
              stringBuilder = stringBuilder.substring(0, stringBuilder.length-2);
              this.tableData.push({
                type: 'Max',
                value: max(data),
                numIds: rIds.length,
                reviewer: stringBuilder,
              });
              break;
            case 'sum':
              this.tableData.push({
                type: 'Sum',
                value: sum(data),
                numIds: result.length,
                reviewer: "-",
              });
              break;
            case 'avg':
              this.tableData.push({
                type: 'Mean',
                value: mean(data).toFixed(2),
                numIds: result.length,
                reviewer: "-",
              });
              break;
            case 'median':
              this.tableData.push({
                type: 'Median',
                value: median(data),
                numIds: result.length,
                reviewer: "-",
              });
              break;
            case 'std':
              this.tableData.push({
                type: 'ST.DEV',
                value: standardDeviation(data).toFixed(2),
                numIds: result.length,
                reviewer: "-", 
              });
              break; 
          }
        })
      }
    },

    components: {
      BasicSectionDetail,
    }
  }
</script>

<style scoped>
.hovered-text {
  color: #606266
}
</style>