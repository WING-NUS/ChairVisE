<template>
  <div id="new-conf-div">
    <el-row type="flex" id="new-conf-button-row">
      <h2>Conference List</h2>
      <el-button type="primary" id="new-conf-button" @click="handleCreate">New Conference</el-button>
    </el-row>
    <el-row>
      <el-table :data="conferenceList" style="width: 100%; font-size: 15px" :cell-style="cellStyle">
        <el-table-column label="Name" prop="name"/>
        <el-table-column label="Year" prop="year" min-width="40px"/>
        <el-table-column label="Presentation">
          <template slot-scope="props">
            <div v-if="props.row.presentations && props.row.presentations.length > 0">
              <ul>
                <li v-for="(p, i) in props.row.presentations" v-bind:key="i">
                  <el-button type="text" style="padding: 3px" @click="handleToPresentation(p)">
                    {{ p.name }}
                  </el-button>
                </li>
              </ul>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="" prop="desc" min-width="150px">
          <template slot-scope="props">
            <!--            <el-button size="mini" type="primary" round @click="handleEdit(props.$index, props.row)">-->
            <!--              Edit-->
            <!--            </el-button>-->
            <el-button size="mini" type="primary" round @click="handleUpdate(props.$index, props.row)">
              Update Data
            </el-button>
            <el-button type="primary" size="mini" round @click="handleCreatePresentation(props.row)">
              New Presentation
            </el-button>
            <el-button size="mini" type="danger" round @click="handleDelete(props.$index, props.row)">
              Delete
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
  </div>
</template>

<script>

export default {
  name: 'ConferenceList',
  data () {
    return {
      tableData: [],
      dialogFormVisible: false,
      form: {
        name: ''
      },
      formLabelWidth: '160px'
    }
  },
  components: {},
  computed: {
    conferenceList () {
      return this.$store.state.conference.conferenceList
    }
  },
  mounted () {
    // console.log(this.conferenceList)
  },
  created () {
    this.$store.dispatch('getConferenceList')
  },
  methods: {
    handleCreate: function () {
      this.$router.push('/import')
    },
    handleEdit (index, row) {
      console.log(row)
      this.$router.push('/import')
    },
    handleDelete (index, row) {
      console.log(row)
      this.$store.dispatch('deleteConference', row)
    },
    handleUpdate (index, row) {
      // console.log(index, row)
      this.$router.push('/import/' + row.id)
    },
    handleToPresentation (presentation) {
      // console.log(presentation)
      // this.$router.push({
      //   name: 'PresentationDetail',
      //   params: presentation
      // })
      this.$router.push('/presentation/' + presentation.id)
    },
    handleCreatePresentation (conference) {
      this.$router.push({
        name: 'NewPresentation',
        params: {
          conferenceId: conference.id,
          conferenceName: conference.name,
          conferenceYear: conference.year
        }
      })
    },
    cellStyle (row, column, rowIndex, columnIndex) {
      if (row.column.label === 'Conference Name') {
        return 'font-weight: 600'
      } else {
        return ''
      }
    }
  }
}
</script>

<style scoped>

#new-conf-div {
  width: 100%;
}

#new-conf-button-row {
  height: 80px;
  padding-left: 40px;
  padding-right: 20px;
  padding-top: 20px;
}

#new-conf-button {
  width: 150px;
  height: 40px;
  margin-left: 30px;
  margin-top: 10px;
}

.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #545c64;
  font-weight: 600;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}

ul {
  margin: 0;
  padding: 0;
}
</style>
