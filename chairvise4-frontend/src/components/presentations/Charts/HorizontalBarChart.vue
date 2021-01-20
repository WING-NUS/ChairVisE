<script>
import { HorizontalBar, mixins } from 'vue-chartjs'

const { reactiveProp } = mixins

export default {
  extends: HorizontalBar,
  mixins: [reactiveProp],
  props: {
    options: {
      type: Object,
      required: true
    }
  },
  mounted () {
    // this.chartData is created in the mixin
    this.renderChart(this.chartData, this.options)
  },
  updated () {
    this.renderChart(this.chartData, this.options)
  },
  watch: {
    options () {
      this.renderChart(this.chartData, this.options)
    }
  }
}
</script>

<style scoped>

</style>
