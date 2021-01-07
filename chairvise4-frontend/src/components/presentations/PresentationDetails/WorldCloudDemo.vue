<template>
  <wordcloud :data="words" nameKey="name" :rotate="rotateConfig" valueKey="value" :showTooltip="true"/>
</template>

<script>
import wordcloud from 'vue-wordcloud'

export default {
  name: 'WorldCloudDetails',
  data () {
    return {
      words: [],
      rotateConfig: {
        from: 0,
        to: 0,
        numOfOrientation: 1
      }
    }
  },
  components: {
    wordcloud
  },
  mounted () {
    this.wordCloudHandler(this.updateWords)
  },
  methods: {
    wordCloudHandler: function (callback) {
      const indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB || window.shimIndexedDB
      const request = indexedDB.open('ChairVisE')
      request.onerror = function (event) {
        console.log('error opening indexedDB')
      }
      request.onsuccess = function (event) {
        const db = event.target.result
        const submissionTransaction = db.transaction('submission')
        const submissionStore = submissionTransaction.objectStore('submission')
        const getAllReq = submissionStore.getAll()
        const tmpWords = []
        getAllReq.onsuccess = function (event) {
          const allRes = event.target.result
          for (const i in allRes) {
            tmpWords.push(allRes[i].keywords)
          }
        }
        // const cursorReq = submissionStore.openCursor()
        // const tmpWords = []
        // cursorReq.onsuccess = function (event) {
        //   const cursor = event.target.result
        //   if (cursor) {
        //     const curSubmission = cursor.value.keywords
        //     tmpWords.push(curSubmission)
        //     cursor.continue()
        //   }
        // }
        submissionTransaction.oncomplete = function (event) {
          callback(tmpWords)
        }
      }
    },
    updateWords: function (result) {
      const wordsCount = {}
      // const delimiterRegex = new RegExp('\r'.join('|'), 'g');
      // will only require at least one selection
      // count the occurrence of word
      result.forEach(r => {
        // console.log(r)
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
      // console.log(wordsCount)
      // generate format as VueWordCloud required
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
      // console.log(this.words)
    },
    capitalizeFirstWord (str) {
      return str.replace(/\b\w/g, l => l.toUpperCase())
    }
  }
}
</script>

<style scoped>

</style>
