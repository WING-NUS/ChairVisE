<template>
  <div>
    <bar-chart :chart-data="chartData" :options="options"/>
    <pie-chart :chart-data="chartData" :options="options2"/>
    <el-button @click="getDataHandler(fillData)">Generate</el-button>
  </div>
</template>

<script>
import BarChart from '@/components/presentations/Charts/BarChart'
import PieChart from '@/components/presentations/Charts/PieChart'
import { generateBackgroundColor } from '@/utils/color'
export default {
  name: 'BarChartDemo',
  components: {
    BarChart,
    PieChart
  },
  mounted () {
  },
  data () {
    return {
      dataset: {
        data: [1, 2]
      },
      labels: [1, 2],
      options: {
        scales: {
          xAxes: [{
            gridLines: {
              offsetGridLines: true
            }
          }]
        }
      },
      options2: {
        hoverBorderWidth: 20
      }
    }
  },
  computed: {
    chartData: function () {
      return {
        labels: this.labels,
        datasets: [this.dataset]
      }
    }
  },
  methods: {
    getDataHandler: function (callback) {
      const indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB || window.shimIndexedDB
      const request = indexedDB.open('ChairVisE')
      request.onerror = function (event) {
        console.log('error opening indexedDB')
      }
      request.onsuccess = function (event) {
        const db = event.target.result
        const submissionTransaction = db.transaction('author')
        const submissionStore = submissionTransaction.objectStore('author')
        const getAllReq = submissionStore.getAll()
        const countryCnt = {}
        getAllReq.onsuccess = function (event) {
          const allRes = event.target.result
          for (const i in allRes) {
            const country = allRes[i].country
            console.log(country)
            if (country in countryCnt) {
              countryCnt[country]++
            } else {
              countryCnt[country] = 1
            }
          }
          callback(countryCnt)
        }
      }
    },
    fillData: function (countryCnt) {
      this.labels = Object.keys(countryCnt)
      this.dataset = { data: [], backgroundColor: [] }
      this.dataset.label = 'label1'
      for (const cn in countryCnt) {
        this.dataset.data.push(countryCnt[cn])
      }
      this.dataset.backgroundColor = generateBackgroundColor(this.dataset.data.length)
    }
  }
}
</script>

<style scoped>
div {
  width: 600px;
  height: 600px;
  margin: 50px;
}
</style>
