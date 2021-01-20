<template>
  <div>
    <pie-chart :chart-data="chartData" :options="options"/>
    <el-button @click="getDataHandler(fillData)">Generate</el-button>
  </div>
</template>

<script>
import PieChart from '@/components/presentations/Charts/PieChart'
export default {
  name: 'PieChartDemo',
  components: {
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
      this.dataset = { data: [] }
      for (const cn in countryCnt) {
        this.dataset.data.push(countryCnt[cn])
      }
    }
  }
}
</script>

<style scoped>
div {
  max-width: 600px;
  margin: 150px auto;
}
</style>
