<template>
  <div class="chart">
    <bar-chart v-bind:chart-data="finalChartData" v-bind:options="options"/>
  </div>
</template>

<script>
import BarChart from '@/components/presentations/Charts/BarChart'
import { idbGet } from '@/utils/section'
import { generateSingleColorSet } from '@/utils/color'
// import PredefinedQueries from '@/utils/predefinedQueries'

export default {
  name: 'BarChartAuthorCountryRank',
  components: {
    BarChart
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
    numDisplay (val) {
      this.getChartData()
    }
  },
  mounted () {
    this.getChartData()
    this.options = this.section.options
    this.numDisplay = this.section.others.numDisplay
    // this.sectionName = PredefinedQueries[this.section.id].name
    // this.description = PredefinedQueries[this.section.id].description
  },
  methods: {
    fillData (numDisplay) {
      this.dataset = {
        data: [],
        backgroundColor: generateSingleColorSet(1, 1, 0.6, 1)[0],
        label: 'Author Dataset'
      }
      const allLabels = Object.keys(this.record).sort((key1, key2) => {
        return this.record[key2] - this.record[key1]
      })
      let others = 0
      for (const i in allLabels) {
        const tmpLabel = allLabels[i]
        if (i < numDisplay) {
          this.labels.push(tmpLabel)
          this.dataset.data.push(this.record[tmpLabel])
        } else {
          others = others + this.record[tmpLabel]
        }
      }
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
      this.record = records
      this.$store.commit('updateChartData', {
        id: this.sectionNum,
        result: { record: this.record }
      })
      this.$store.dispatch('updatePresentationSection')
      this.fillData(this.numDisplay)
    },
    getChartData () {
      if (this.editable) {
        const storenames = ['author']
        const callbacks = [this.getDataCallback]
        idbGet(this.dbName, storenames, callbacks)
      } else {
        this.record = this.section.record
      }
      if (this.section.options) {
        this.options = this.section.options
      }
      if (this.section.numDisplay > 0) {
        this.numDisplay = this.section.numDisplay
      }
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
