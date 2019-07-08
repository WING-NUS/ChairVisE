<template>
  <basic-section-detail :section-detail="sectionDetail" :presentation-id="presentationId" :has-data="hasData"
                        :extraFormItemsRules="{}"
                        @update-visualisation="updateVisualisation">
    <pie-chart :chart-data="chartData" :options="options"></pie-chart>

    <template slot="extraFormItems" slot-scope="slotProps">
      <el-form-item label="Category Field Name" prop="extraData.categoryFieldName" v-if="slotProps.isInAdvancedMode">
        <el-select placeholder="categoryFieldName" v-model="slotProps.extraData.categoryFieldName">
          <el-option
            v-for="selection in slotProps.editForm.selections"
            :key="selection.rename"
            :label="selection.rename"
            :value="selection.rename">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Value Field Name" prop="extraData.valueFieldName" v-if="slotProps.isInAdvancedMode">
        <el-select placeholder="valueFieldName" v-model="slotProps.extraData.valueFieldName">
          <el-option
            v-for="selection in slotProps.editForm.selections"
            :key="selection.rename"
            :label="selection.rename"
            :value="selection.rename">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Num of result to display" prop="extraData.numOfResultToDisplay"
                    v-if="slotProps.isInAdvancedMode">
        <el-slider v-model="slotProps.extraData.numOfResultToDisplay" :min="5" :max="30"></el-slider>
      </el-form-item>
    </template>
  </basic-section-detail>
</template>

<script>
  import BasicSectionDetail from '@/components/sectionDetail/BasicSectionDetail.vue'
  import PieChart from '@/components/sectionDetail/chart/PieChart.vue'
  import {generateBorderColor, generateBackgroundColor} from '@/common/color'

  export default {
    name: "PieChartSectionDetail",

    props: {
      sectionDetail: {
        type: Object,
        required: true
      },
      presentationId: {
        type: String,
        required: true
      }
    },

    data() {
      return {

        labels: [],
        dataset: {},
        options: {},
      }
    },

    computed: {
      hasData() {
        return this.labels.length !== 0;
      },

      chartData() {
        return {
          labels: this.labels,
          datasets: [this.dataset]
        }
      }
    },

    methods: {
      updateVisualisation({result, extraData}) {
        let displayedResult = result.slice(0, extraData.numOfResultToDisplay);
        let remainedResult = result.slice(extraData.numOfResultToDisplay, result.length);

        // process category
        this.labels = displayedResult.map(record => record[extraData.categoryFieldName]);
        if (remainedResult.length !== 0) {
          this.labels.push("Other");
        }

        // process value
        let data = displayedResult.map(record => record[extraData.valueFieldName]);
        if (remainedResult.length !== 0) {
          // take sum of the rest
          data.push(remainedResult.map(record => record[extraData.valueFieldName]).reduce((a, b) => a + b, 0));
        }

        // generate dataset
        this.dataset = {
          borderWidth: 1,
          label: extraData.dataSetLabel,
          data,
          backgroundColor: generateBackgroundColor(data.length),
          borderColor: generateBorderColor(data.length),
        };

        // process options
        this.options = {
          legend: {
            display: true,
            position: 'bottom'
          },
          layout: {
            padding: {
              top: 15,
            }
          },
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            datalabels: {
              // show both value and percentage
              formatter: (value, ctx) => {
                let sum = 0;
                let dataArr = ctx.chart.data.datasets[0].data;
                dataArr.map(data => {
                  sum += data;
                });
                let percentage = (value * 100 / sum).toFixed(2) + "%";
                return `${percentage} (${value})`;
              },
            }
          }
        }
      }
    },

    components: {
      BasicSectionDetail,
      PieChart
    }
  }
</script>

<style scoped>

</style>