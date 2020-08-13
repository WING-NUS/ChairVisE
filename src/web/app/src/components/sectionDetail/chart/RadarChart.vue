<script>
  import {Radar, mixins} from 'vue-chartjs'

  const {reactiveProp} = mixins;

  export default {
    extends: Radar,
    mixins: [reactiveProp],
    props: {
      options: {
        type: Object,
        required: true
      },
    },
    mounted() {
      // this.chartData is created in the mixin
      this.renderChart(this.chartData, this.options)
    },
    watch: {
      options() {
        this.renderChart(this.chartData, this.options);
      }
    }
  }
</script>