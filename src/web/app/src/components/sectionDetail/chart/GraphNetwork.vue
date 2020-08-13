<template>
    <d3-network :net-nodes="nodes" :net-links="links" :options="options"
            @node-click="nodeClick"
            @link-click="linkClick" />
</template>

<script>

  import D3Network from 'vue-d3-network'

  export default {
    components: {
      D3Network
    },
    props: {
      nodes: {
        type: Array,
        required: true
      },
      links: {
        type: Array,
        required: true
      },
      options: {
        type: Object,
        required: true
      },
      nodeClick: {
        type: Function
      },
      linkClick: {
        type: Function
      }
    }
  }
</script>

<style scoped>

</style>
