<template>
  <highcharts :options="chartOptions" ref="highcharts"></highcharts>
</template>

<script>
import { Chart } from "highcharts-vue";
import Highcharts from "highcharts";
import dependencyInit from "highcharts/modules/dependency-wheel";
import sanKeyInit from "highcharts/modules/sankey";
import exporting from "highcharts/modules/exporting";
exporting(Highcharts);
sanKeyInit(Highcharts);
dependencyInit(Highcharts);

export default {
  name: "DependencyChart",
  props: {
    chartData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      // default chart settings
      chartOptions: {
        exporting: {
          enabled: false
        },
        title: {
          text: ""
        },
        series: [
          {
            keys: ["from", "to", "weight"],
            data: [],

            type: "dependencywheel",

            name: "Dependency wheel series",
            dataLabels: {
              color: "#333",
              textPath: {
                enabled: true,
                attributes: {
                  dy: 5
                }
              },
              distance: 10
            },
            size: "95%"
          }
        ]
      }
    };
  },
  components: {
    highcharts: Chart
  },
  watch: {
    chartData() {
      this.renderDependencyChart(this.chartData);
    }
  },
  mounted() {
    this.renderDependencyChart(this.chartData);
  },
  methods: {
    // pass the extract data from query and pass it into the chart
    renderDependencyChart(chartData) {
      let chart = this.$refs.highcharts.chart;
      var xField1 = [];
      var xField2 = [];
      var submissionCount = [];

      // get the submission for each row i from query data
      chartData.datasets[0].data.map(data => {
        submissionCount.push(data);
      });
      // get the xField1 name for row i from query data
      chartData.labels.map(data => {
        xField1.push(data);
      });
      // get the xField2 name for row i from query data
      chartData.labels2.map(data => {
        xField2.push(data);
      });

      // put each row i extracted from query data into the chart
      var seriesData = [];
      for (var i = 0; i < xField1.length; i++) {
        seriesData.push([xField1[i], xField2[i], submissionCount[i]]);
      }
      // set the chart data
      chart.series[0].setData(seriesData, true);
    }
  }
};
</script>

<style scoped></style>
