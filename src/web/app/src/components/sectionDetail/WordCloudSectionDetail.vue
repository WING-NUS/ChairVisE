<template>
  <basic-section-detail :section-detail="sectionDetail" :presentation-id="presentationId" :has-data="hasData"
                        :version="version"
                        :edit-form-selections-rule="editFormSelectionsRule"
                        :edit-form-involved-records-rule="editFormInvolvedRecordsRule"
                        :edit-form-filters-rule="editFormFiltersRule"
                        :extraFormItemsRules="extraFormItemsRules"
                        @update-visualisation="updateVisualisation">
    <word-cloud
      :data="words">
    </word-cloud>

    <template slot="extraFormItems" slot-scope="slotProps">
      <el-form-item label="Delimiter to Generate Word" prop="extraData.delimiters" v-if="slotProps.isInAdvancedMode">
        <el-select multiple v-model="slotProps.extraData.delimiters">
          <el-option label="\r" value="\r"/>
          <el-option label="\n" value="\n"/>
          <el-option label="Space" value="\s"/>
        </el-select>
      </el-form-item>
      <el-form-item label="Word to Ignore" prop="extraData.ignoreWords" v-if="slotProps.isInAdvancedMode">
        <el-select multiple v-model="slotProps.extraData.ignoreWords" filterable allow-create>
        </el-select>
      </el-form-item>
    </template>
  </basic-section-detail>
</template>

<script>
  import WordCloud from '@/components/sectionDetail/chart/WordCloud.vue'
  import BasicSectionDetail from '@/components/sectionDetail/BasicSectionDetail.vue'

  export default {
    props: {
      sectionDetail: {
        type: Object,
        required: true
      },
      presentationId: {
        type: String,
        required: true
      },
      version: {
        type: String,
        required: true
      }
    },

    data() {
      return {
        editFormSelectionsRule: [{
          validator: (rule, value, callback) => {
            if (value.expression.length === 0 || value.expression.rename === 0) {
              return callback(new Error('Please specify all fields'))
            }
            callback();
          },
          trigger: 'blur',
        }],
        editFormInvolvedRecordsRule: [{
          validator: (rule, value, callback) => {
            if (value.length >= 2 || value.length < 1) {
              return callback(new Error('There must be only one record involved'))
            }
            return callback();
          },
          trigger: 'change',
        }],
        editFormFiltersRule: [{
          validator: (rule, value, callback) => {
            if (value.field.length === 0 || value.comparator.length === 0 || value.value.length === 0) {
              return callback(new Error('Please specify all fields'))
            }
            callback();
          },
          trigger: 'blur',
        }],

        extraFormItemsRules: {
          delimiters: [
            {
              validator: (rule, value, callback) => {
                if (value.length === 0) {
                  return callback(new Error('Please specify at least one delimiter'))
                }
                callback();
              },
              trigger: 'blur',
            }
          ],
        },

        // word cloud related field
        words: [],
      }
    },

    computed: {
      hasData() {
        return this.words.length !== 0;
      }
    },

    methods: {
      updateVisualisation({result, selections, extraData}) {
        let fieldName = selections[0].rename;
        let wordsCount = {};
        let delimiterRegex = new RegExp(extraData.delimiters.join('|'), 'g');
        // will only require at least one selection
        // count the occurrence of word
        result.forEach(r => {
          r[fieldName].split(delimiterRegex)
            .filter(w => !extraData.ignoreWords.includes(w.toLowerCase())) // filter ignoreWords
            .forEach(w => {
              // skip empty string
              if (w.length === 0) {
                return
              }
              // normalized word e.g. 'digital world' -> `Digital World`
              let normalizedW = this.capitalizeFirstWord(w);
              // put in the count map
              if (wordsCount.hasOwnProperty(normalizedW)) {
                wordsCount[normalizedW]++
              } else {
                wordsCount[normalizedW] = 1;
              }
            })
        });
        // generate format as VueWordCloud required
        let words = [];
        for (let word in wordsCount) {
          if (wordsCount.hasOwnProperty(word)) {
            words.push([word, wordsCount[word]])
          }
        }
        // sort and keep the first twenty words
        words.sort((a, b) => {
          return b[1] - a[1]
        });
        words = words.slice(0, 20);
        this.words = words;
      },

      capitalizeFirstWord(str) {
        return str.replace(/\b\w/g, l => l.toUpperCase());
      }
    },

    components: {
      WordCloud,
      BasicSectionDetail
    }
  }
</script>