<template>
  <div class="chart">
    <doughnut-chart v-bind:chart-data="finalChartData" v-bind:options="options"/>
  </div>
</template>

<script>
import DoughnutChart from '@/components/presentations/Charts/DoughnutChart'
import { idbGet } from '@/utils/section'
import { objValueSort } from '@/utils/utility'
import { generateSingleColorSet } from '@/utils/color'
// import PredefinedQueries from '@/utils/predefinedQueries'

export default {
  name: 'BarChartAuthorCountryRank',
  components: {
    DoughnutChart
  },
  props: {
    sectionNum: Number,
    metadata: Object,
    dbName: Number
  },
  data () {
    return {
      labels: [],
      dataset: [],
      options: {},
      record: [],
      numDisplay: 6
    }
  },
  computed: {
    editable () {
      return this.$store.state.presentation.editable
    },
    section () {
      return this.$store.state.presentation.sectionList[this.sectionNum]
    },
    finalChartData () {
      return {
        labels: this.labels,
        datasets: [this.dataset]
      }
    }
  },
  watch: {
  },
  mounted () {
    if (this.editable) {
      this.getChartData()
    } else {
      this.record = this.section.record
      this.numDisplay = this.section.others.numDisplay
      this.fillData(this.numDisplay)
    }
    this.options = this.section.options
    // this.sectionName = PredefinedQueries[this.section.id].name
    // this.description = PredefinedQueries[this.section.id].description
  },
  methods: {
    fillData (numDisplay) {
      const allLabels = Object.keys(this.record)
      let others = 0
      this.dataset = {
        data: [],
        backgroundColor: generateSingleColorSet(numDisplay + 1, 1, 0.8, 0.8),
        label: 'Author Dataset'
      }
      for (const i in allLabels) {
        const tmpLabel = allLabels[i]
        if (i < numDisplay) {
          this.labels.push(tmpLabel)
          this.dataset.data.push(this.record[tmpLabel])
        } else {
          others = others + this.record[tmpLabel]
        }
      }
      this.labels.push('other')
      this.dataset.data.push(others)
    },
    getDataCallback (result) {
      const records = {}
      for (const i in result) {
        const e = result[i]
        if (e.country in records) {
          records[e.country]++
        } else {
          records[e.country] = 1
        }
      }
      this.record = objValueSort(records)
      this.$store.commit('updateChartData', {
        id: this.sectionNum,
        result: { record: this.record }
      })
      this.$store.dispatch('updatePresentationSection')
      this.fillData(this.numDisplay)
    },
    getChartData () {
      const storenames = ['author']
      const callbacks = [this.getDataCallback]
      idbGet(this.dbName, storenames, callbacks)
    }
  }
}
</script>

<style scoped>
.el-card {
  width: 500px;
  height: 500px;
}

</style>
