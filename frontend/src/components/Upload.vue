<template>
  <div class="upload">
    <Canvas
      width=256
      height=256
      v-on:update:canvas="image = $event"
      ref="canvas"
    ></Canvas>
    <button v-on:click="upload">Upload</button>
    <button v-on:click="clear">Clear</button><br>
    <label>Character you'd like to draw and upload</label><br>
    <input v-model='label' placeholder="文"/>
  </div> 
</template>

<script>
import Canvas from './Canvas.vue'

export default {
  name: 'Upload',
  props: ['label'],
  components: {
    Canvas
  },
  data: () => {
    return {
      image: null,
      label: ''
    }
  },
  methods: {
    upload() {
      let image = this.image
      let codePoint = this.label.codePointAt(0).toString(16)
      fetch(
        `/api/upload?label=${codePoint}`,
        {
          method: "POST",
          mode: "same-origin",
          headers: { "Content-Type": "image/png" },
          body: image
        }
      )
    },
    clear() {
      this.$refs.canvas.clear() 
    }
  }
}
</script>

<style>
input {
  font-size: 60px;
  width: 60px; 
}
</style>
