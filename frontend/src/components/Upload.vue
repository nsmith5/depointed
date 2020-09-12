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
    <button v-on:click="predict">Predict</button>
    <button v-on:click="train">Train</button><br>
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
      let label = this.label
      fetch(
        `/api/upload?label=${label}`,
        {
          method: "POST",
          mode: "same-origin",
          headers: { "Content-Type": "image/png" },
          body: image
        }
      ).then(this.clear)
    },
    clear() {
      this.$refs.canvas.clear() 
    },
    predict() {
      let image = this.image
      fetch(
        `/api/predict`,
        {
          method: "POST",
          mode: "same-origin",
          headers: { "Content-Type": "image/png" },
          body: image
        }
      ).then((resp) => {
        console.log(resp)
      })
    },
    train() {
      fetch(
        `/api/train`,
        {
          method: "POST",
          mode: "same-origin",
        }
      ).then((resp) => {
        console.log(resp)
      })
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
