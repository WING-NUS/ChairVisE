<template>
  <div class="chart">
    <bar-chart v-bind:chart-data="finalChartData" v-bind:options="options"/>
  </div>
</template>

<script>
import BarChart from '@/components/presentations/Charts/HorizontalBarChart'
import { idbGet } from '@/utils/section'
import { arrayAvg } from '@/utils/utility'
import { generateSingleColorSet } from '@/utils/color'
export default {
  name: 'BarChartTopScoreSubmission',
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
      numDisplay: 10
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
        backgroundColor: generateSingleColorSet(numDisplay, 1, 0.6, 1)[0],
        label: 'Average Score'
      }
      const allLabels = Object.keys(this.record).sort((key1, key2) => {
        return this.record[key2] - this.record[key1]
      })
      for (const k in allLabels) {
        const tmpLabel = allLabels[k]
        if (k < numDisplay) {
          this.dataset.data.push(this.record[tmpLabel])
          this.labels.push(tmpLabel)
        }
      }
    },
    getDataCallback (result) {
      const reviewRecord = {}
      for (const i in result.review) {
        const r = result.review[i]
        const submissionId = r.submissionId
        if (submissionId in reviewRecord) {
          reviewRecord[submissionId].push(Number(r.overallScore))
        } else {
          reviewRecord[submissionId] = [Number(r.overallScore)]
        }
      }
      for (const k in reviewRecord) {
        reviewRecord[k] = arrayAvg(reviewRecord[k])
      }
      const record = {}
      for (const i in result.submission) {
        record[result.submission[i].title] = reviewRecord[result.submission[i].submissionId]
      }
      this.record = record
      this.$store.commit('updateChartData', {
        id: this.sectionNum,
        result: { record: this.record }
      })
      this.$store.dispatch('updatePresentationSection')
      this.fillData(this.numDisplay)
    },
    getChartData () {
      if (this.editable) {
        const storeNames = ['review', 'submission']
        const callbacks = [this.getDataCallback]
        idbGet(this.dbName, storeNames, callbacks)
      } else {
        this.record = this.section.record
        this.numDisplay = this.section.others.numDisplay
        this.fillData(this.numDisplay)
      }
      this.options = this.section.options
    }
  }
}
</script>

<style scoped>

</style>
