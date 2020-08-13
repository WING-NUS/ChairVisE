<template>
  <basic-section-detail :section-detail="sectionDetail" :presentation-id="presentationId" :has-data="hasData"
                        :version="version"
                        :extraFormItemsRules="{}"
                        @update-visualisation="updateVisualisation">
    <GraphNetwork :nodes="nodes" :links="links" :options="options"
            :nodeClick="showNodeDetails"
            :linkClick="showLinkDetails"></GraphNetwork>

    <template slot="extraFormItems" slot-scope="slotProps">
      <el-form-item label="Colorful Bar" prop="extraData.isColorfulBar" v-if="slotProps.isInAdvancedMode">
        <el-switch
          v-model="slotProps.extraData.isColorfulBar"
          active-text="Colorful Bar"
          inactive-text="Single Color Bar">
        </el-switch>
      </el-form-item>
      <el-form-item label="Num of result to display" prop="extraData.numOfResultToDisplay"
                    v-if="slotProps.isInAdvancedMode">
        <el-slider v-model="slotProps.extraData.numOfResultToDisplay" :min="5" :max="30"></el-slider>
      </el-form-item>
    </template>
  </basic-section-detail>
</template>

<script>
  import BasicSectionDetail from '@/components/sectionDetail/BasicSectionDetail.vue'
  import GraphNetwork from '@/components/sectionDetail/chart/GraphNetwork.vue'
  import {generateBackgroundColor} from '@/common/color'
  
  export default {
    name: "GraphNetworkSectionDetail",

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
        nodes: [],
        links: [],
        options: {},
        partialResult: [],
      }
    },

    computed: {
      hasData() {
        return this.nodes.length !== 0;
      },
    },

    methods: {
      showNodeDetails(event, node) {
        let newNodeMsg;
        if (this.$nodeMsg !== undefined) {
          newNodeMsg = this.$nodeMsg;
        }
        newNodeMsg += node._labelClass;
        this.$message({
          showClose: true,
          title: 'Information',
          message: newNodeMsg
        });
      },
      showLinkDetails(event, link) {
        var info = [this.nodes[link.sid]._labelClass, this.nodes[link.tid]._labelClass, link.name];
        var newMsg = "";
        for(var i = 0; i < info.length; i++) {
          newMsg += info[i];
          if (this.$linkMsg[i].msg !== undefined) {
            newMsg += this.$linkMsg[i].msg;
          }
        }
        this.$message({
          showClose: true,
          title: 'Information',
          message: newMsg
        });
      },
      updateVisualisation({result, selections, extraData}) {
        let i;
        this.partialResult = result.slice(0, extraData.numOfResultToDisplay);

        this.$nodeMsg = extraData.nodeMessage;
        this.$linkMsg = extraData.linkMessage;

        // Add distinct nodes to the network graph
        let distinctNodesSet = new Set();
        this.partialResult.forEach(res => {
          distinctNodesSet.add(res[selections[0].rename]);
          distinctNodesSet.add(res[selections[1].rename]);
        });

        let distinctNodesArray = Array.from(distinctNodesSet);
        let distinctNodes = [];
        for(i = 0; i < distinctNodesArray.length; i++) {
          distinctNodes[i] = {
            id: i,
            name: "#" + (i + 1),
            _labelClass: distinctNodesArray[i],
          };
        }

        var nodeSizeList = [];
        for (i = 0; i<distinctNodes.length; i++){
          nodeSizeList.push(0);
        }

        let graphLinks = [];
        this.partialResult.forEach((element, index) => {
          var num_entry = element[selections[2].rename];
          graphLinks[index] = {
            sid: distinctNodes.filter(x => x._labelClass == element[selections[0].rename]).map(x => x.id)[0],
            tid: distinctNodes.filter(x => x._labelClass == element[selections[1].rename]).map(x => x.id)[0],
            name: String(num_entry),
            _svgAttrs:{'stroke-width': -(8/num_entry)+10}
          };
          //console.log(graphLinks[index]['sid'],graphLinks[index]['tid'], num_entry);
          nodeSizeList[graphLinks[index]['sid']] += num_entry;
          nodeSizeList[graphLinks[index]['tid']] += num_entry;
          //console.log(nodeSizeList);
        });

        nodeSizeList.forEach((element, index) =>{
          distinctNodes[index]['_size'] = -(30/element)+40;
        });
        if (extraData.isColorfulBar) {
          distinctNodes.forEach((node,index) => {
            node._color = generateBackgroundColor(this.partialResult.length)[index % this.partialResult.length];
          })
          graphLinks.forEach((link, index) => {
            link._color = generateBackgroundColor(this.partialResult.length)[index % this.partialResult.length];
          })
        } else {
          distinctNodes.forEach(node => {
            node._color = generateBackgroundColor(this.partialResult.length)[this.partialResult.length - 1];
          })
          graphLinks.forEach(link => {
            link._color = generateBackgroundColor(this.partialResult.length)[this.partialResult.length - 1];
          })
        }

        this.nodes = distinctNodes;
        this.links = graphLinks;
        this.options = {
          force: 2000,
          nodeSize: 30,
          nodeLabels: true,
          linkLabels: false,
          linkWidth: 50,
          fontSize: 14,
          canvas: false
        }
        //console.log("data: " + this.nodes[0].name);
      }
    },

    components: {
      BasicSectionDetail,
      GraphNetwork
    }
  }
</script>

<style scoped>

</style>

<style>
.node{
  stroke:rgba(18,120,98,.7);
  stroke-width:2px;
  -webkit-transition:fill .5s ease;
  transition:fill .5s ease;
  fill:#dcfaf3
}
.node.selected{
  stroke:#caa455
}
.link{
  stroke:rgba(18,120,98,.3);
  /*stroke-width: 2px;*/
}
.link,.node{
  stroke-linecap:round
}
.link:hover,.node:hover{
  stroke-width:5px
}
.link.selected{
  stroke:rgba(202,164,85,.6)
}
.curve{
  fill:none
}
.link-label,.node-label{
  fill:rgba(0,0,0,.6)
}
.link-label{
  -webkit-transform:translateY(-.5em);
  transform:translateY(-.5em);
  text-anchor:middle
}
</style>