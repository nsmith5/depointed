<template>
  <div class="upload">
    <Canvas width=256 height=256 v-on:update:canvas="image = $event"></Canvas>
    <button v-on:click="upload">Upload</button>
    <button>Clear</button>
  </div> 
</template>

<script>
import Canvas from './Canvas.vue'

export default {
  name: 'Upload',
  components: {
    Canvas
  },
  data: () => { return { image: null } },
  methods: {
    upload() {
      let image = this.image
      let codePoint = 'example'
      fetch(
        `/api/upload?label=${codePoint}`,
        {
          method: "POST",
          mode: "same-origin",
          headers: { "Content-Type": "image/png" },
          body: image
        }
      )
    }
  }
}

</script>
