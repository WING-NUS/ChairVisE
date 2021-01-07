<template>
  <div class="chart">
    <wordcloud :data="words" nameKey="name" :color="myColors" v-bind:rotate="rotateConfig" valueKey="value" :showTooltip="true"/>
  </div>
</template>

<script>
import wordcloud from 'vue-wordcloud'
import { idbGet } from '@/utils/section'
import { generateSingleColorSet } from '@/utils/color'
export default {
  name: 'WordCloudSubmissionKeywords',
  components: {
    wordcloud
  },
  props: {
    sectionNum: Number,
    metadata: Object,
    dbName: Number
  },
  data () {
    return {
      words: [],
      rotateConfig: {
        from: 0,
        to: 0,
        numOfOrientation: 1
      },
      myColors: generateSingleColorSet(5, 1, 0.9, 0.9)
    }
  },
  computed: {
    editable () {
      return this.$store.state.presentation.editable
    },
    section () {
      return this.$store.state.presentation.sectionList[this.sectionNum]
    }
  },
  mounted () {
    // this.rotateConfig = this.section.options
    if (this.editable) {
      this.getData()
    } else {
      this.words = this.section.record
    }
    console.log(this.rotateConfig)
  },
  methods: {
    getDataCallback (result) {
      const wordsCount = {}
      // const delimiterRegex = new RegExp('\r'.join('|'), 'g');
      // will only require at least one selection
      // count the occurrence of word
      result.forEach(r => {
        r = r.keywords
        r.split('\n')
          .forEach(w => {
            // skip empty string
            if (w.length === 0) {
              return
            }
            // normalized word e.g. 'digital world' -> `Digital World`
            const normalizedW = this.capitalizeFirstWord(w)
            // put in the count map
            // eslint-disable-next-line no-prototype-builtins
            if (wordsCount.hasOwnProperty(normalizedW)) {
              wordsCount[normalizedW]++
            } else {
              wordsCount[normalizedW] = 1
            }
          })
      })
      // generate format as WordCloud required
      let words = []
      for (const word in wordsCount) {
        // eslint-disable-next-line no-prototype-builtins
        if (wordsCount.hasOwnProperty(word)) {
          words.push([word, wordsCount[word]])
        }
      }
      // sort and keep the first twenty words
      words.sort((a, b) => {
        return b[1] - a[1]
      })
      words = words.slice(0, 20)
      words = words.map((a) => {
        return {
          name: a[0],
          value: a[1]
        }
      })
      this.words = words
      this.$store.commit('updateChartData', {
        id: this.sectionNum,
        result: { record: this.words }
      })
      this.$store.dispatch('updatePresentationSection')
    },
    getData () {
      const storenames = ['submission']
      const callbacks = [this.getDataCallback]
      idbGet(this.dbName, storenames, callbacks)
    },
    capitalizeFirstWord (str) {
      return str.replace(/\b\w/g, l => l.toUpperCase())
    }
  }
}
</script>

<style scoped>

</style>
