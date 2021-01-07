<template>
  <div class="chart">
    <doughnut-chart v-bind:chart-data="finalChartData" v-bind:options="options"/>
  </div>
</template>

<script>
import DoughnutChart from '@/components/presentations/Charts/DoughnutChart'
import { idbGet } from '@/utils/section'
import { generateSingleColorSet } from '@/utils/color'

export default {
  name: 'DoughnutSubmissionAccept',
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
      labels: ['accepted', 'declined'],
      dataset: [],
      options: {},
      record: []
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
  mounted () {
    if (this.editable) {
      this.getChartData()
    } else {
      this.record = this.section.record
      this.fillData()
    }
    this.options = this.section.options
  },
  methods: {
    fillData () {
      this.dataset = {
        data: this.record,
        backgroundColor: generateSingleColorSet(2, 1, 0.8, 0.4),
        label: 'Submission Dataset'
      }
      this.labels = ['accepted', 'declined']
    },
    getDataCallback (result) {
      this.record = [0, 0]
      for (const i in result) {
        if (result[i].acceptance) {
          this.record[0]++
        } else {
          this.record[1]++
        }
      }
      this.$store.commit('updateChartData', {
        id: this.sectionNum,
        result: { record: this.record }
      })
      this.$store.dispatch('updatePresentationSection')
      this.fillData()
    },
    getChartData () {
      if (this.editable) {
        const storenames = ['submission']
        const callbacks = [this.getDataCallback]
        idbGet(this.dbName, storenames, callbacks)
      } else {
        this.record = this.section.record
        if (this.section.options) {
          this.options = this.section.options
        }
        if (this.section.numDisplay > 0) {
          this.numDisplay = this.section.numDisplay
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
