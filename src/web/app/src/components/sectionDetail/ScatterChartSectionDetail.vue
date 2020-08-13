<template>
  <basic-section-detail :section-detail="sectionDetail" :presentation-id="presentationId" :has-data="hasData"
                        :version="version"
                        :edit-form-selections-rule="editFormSelectionsRule"
                        :edit-form-involved-records-rule="editFormInvolvedRecordsRule"
                        :edit-form-filters-rule="editFormFiltersRule"
                        :edit-form-groupers-rule="editFormGroupersRule"
                        :edit-form-sorters-rule="editFormSortersRule"
                        :extraFormItemsRules="extraFormItemsRules"
                        @update-visualisation="updateVisualisation">
    <scatter-chart :chart-data="chartData" :options="options"></scatter-chart>

    <template slot="extraFormItems" slot-scope="slotProps">
      <el-form-item label="xAxis Field Name" prop="extraData.xAxisFieldName" v-if="slotProps.isInAdvancedMode">
        <el-select placeholder="xAxisFieldName" v-model="slotProps.extraData.xAxisFieldName">
          <el-option
            v-for="selection in slotProps.editForm.selections"
            :key="selection.rename"
            :label="selection.rename"
            :value="selection.rename">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="yAxis Field Name" prop="extraData.yAxisFieldName" v-if="slotProps.isInAdvancedMode">
        <el-select placeholder="yAxisFieldName" v-model="slotProps.extraData.yAxisFieldName">
          <el-option
            v-for="selection in slotProps.editForm.selections"
            :key="selection.rename"
            :label="selection.rename"
            :value="selection.rename">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Legend Label Name" prop="extraData.dataSetLabel" v-if="slotProps.isInAdvancedMode">
        <el-input v-model="slotProps.extraData.dataSetLabel" placeholder="Label Name"></el-input>
      </el-form-item>
      <el-form-item label="Colorful Bar" prop="extraData.isColorfulBar" v-if="slotProps.isInAdvancedMode">
        <el-switch
          v-model="slotProps.extraData.isColorfulBar"
          active-text="Colorful Bar"
          inactive-text="Single Color Bar">
        </el-switch>
      </el-form-item>
      <el-form-item label="Num of result to display" prop="extraData.numOfResultToDisplay"
                    v-if="slotProps.isInAdvancedMode">
        <el-slider v-model="slotProps.extraData.numOfResultToDisplay" :min="5" :max="30"></el-slider>
      </el-form-item>
      <el-form-item v-if="slotProps.isInAdvancedMode"
                    v-for="(tooltip, index) in slotProps.extraData.fieldsShownInToolTips" :label="'Tooltips ' + index"
                    :key="'tooltips' + index">
        <el-select placeholder="Field" v-model="tooltip.field">
          <el-option
            v-for="selection in slotProps.editForm.selections"
            :key="selection.rename"
            :label="selection.rename"
            :value="selection.rename">
          </el-option>
        </el-select>&nbsp;
        <el-input v-model="tooltip.label" placeholder="Label Name" style="width: 150px"></el-input>&nbsp;
        <el-button type="danger" icon="el-icon-delete" circle
                   @click="removeTooltip(slotProps.extraData.fieldsShownInToolTips, tooltip)"></el-button>
      </el-form-item>
      <el-form-item v-if="slotProps.isInAdvancedMode">
        <el-button type="success" plain @click="addTooltip(slotProps.extraData.fieldsShownInToolTips)">Add Tooltip
        </el-button>
      </el-form-item>
    </template>
  </basic-section-detail>
</template>

<script>
  import ScatterChart from '@/components/sectionDetail/chart/ScatterChart.vue'
  import BasicSectionDetail from '@/components/sectionDetail/BasicSectionDetail.vue'
  import {generateBorderColor, generateBackgroundColor} from '@/common/color'

  export default {
    name: "ScatterChartSectionDetail",

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
        editFormSelectionsRule: [{
          validator: (rule, value, callback) => {
            if (value.expression.length === 0 || value.expression.rename === 0) {
              return callback(new Error('Please specify all field for the selection'))
            }
            callback();
          },
          trigger: 'blur',
        }],
        editFormInvolvedRecordsRule: [{
          validator: (rule, value, callback) => {
            if (value.length < 1) {
              return callback(new Error('There must be one record involved'))
            }
            callback();
          },
          trigger: 'change',
        }],
        editFormFiltersRule: [{
          validator: (rule, value, callback) => {
            if (value.field.length === 0 || value.comparator.length === 0 || value.value.length === 0) {
              return callback(new Error('Please specify all fields'))
            }
            callback();
          },
          trigger: 'blur',
        }],
        editFormSortersRule: [{
          validator: (rule, value, callback) => {
            if (value.field.length === 0 || value.order.length === 0) {
              return callback(new Error('Please specify all fields'))
            }
            callback();
          },
          trigger: 'blur',
        }],
        editFormGroupersRule: [],

        extraFormItemsRules: {
          xAxisFieldName: [{
            required: true,
            message: 'There should be one field to map x axis',
            trigger: 'blur',
          }],
          yAxisFieldName: [{
            required: true,
            message: 'There should be one field to map y axis',
            trigger: 'blur',
          }],
        },

        labels: [],
        dataset: {},
        partialResult: [],
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
        this.partialResult = result.slice(0, extraData.numOfResultToDisplay);
        // process x axis
        this.labels = this.partialResult.map(record => record[extraData.xAxisFieldName]);
        
        var xyArray = [];
        for(var i = 0; i < this.partialResult.length; i++) {
            xyArray.push({
                x: this.partialResult[i][extraData.xAxisFieldName],
                y: this.partialResult[i][extraData.yAxisFieldName]
            });
        }
        // process y axis
        this.dataset = {
          borderWidth: 1,
          label: extraData.dataSetLabel,
          data: xyArray,
          pointRadius: 5,
          pointBackgroundColor: generateBackgroundColor(this.partialResult.length),
          pointBorderColor: generateBorderColor(this.partialResult.length),
        };
        // generate color
        if (extraData.isColorfulBar) {
          this.dataset.pointBackgroundColor = generateBackgroundColor(this.partialResult.length);
          this.dataset.pointBorderColor = generateBorderColor(this.partialResult.length);
        } else {
          // choose a color in random
          this.dataset.pointBackgroundColor = generateBackgroundColor(this.partialResult.length)[this.partialResult.length - 1];
          this.dataset.pointBorderColor = generateBorderColor(this.partialResult.length)[this.partialResult.length - 1];
        }

        // to display more data
        let toolTipLabelCallback = (tooltipItems) => {
          return extraData.dataSetLabel + ": " + tooltipItems.yLabel;
        }
        let toolTipTitleCallback = (tooltipItems) => {
          let currentIndex = tooltipItems[0].index;
          return extraData.fieldsShownInToolTips.map(f => `${f.label}: ${this.partialResult[currentIndex][f.field]}`);
        };

        // process tooltip callback
        this.options = {
          scales: {
            yAxes: [{
              ticks: {
                beginAtZero: true
              },
              gridLines: {
                display: true
              }
            }],
            xAxes: [{
              gridLines: {
                display: false
              },
              ticks: {
                beginAtZero: true,
                stepSize: 1
              }
            }]
          },
          legend: {
            display: true,
            position: 'bottom'
          },
          layout: {
            padding: {
              top: 30,
            }
          },
          responsive: true,
          maintainAspectRatio: false,
          tooltips: {
            mode: 'index',
            callbacks: {
              title: toolTipTitleCallback,
              label: toolTipLabelCallback
            }
          },
          plugins: {
            datalabels: {
              anchor: 'end',
              align: 'end',
              // show both value and percentage
              formatter: () => {
                return ``;
              },
            }
          }
        }
      },

      addTooltip(tooltips) {
        tooltips.push({
          label: '',
          field: '',
        })
      },

      removeTooltip(tooltips, tooltipToRemove) {
        let index = tooltips.indexOf(tooltipToRemove);
        tooltips.splice(index, 1)
      },
    },

    components: {
      BasicSectionDetail,
      ScatterChart
    }
  }
</script>

<style scoped>
</style>