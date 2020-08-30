<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/logo.png">
    <Canvas height=256 width=256></Canvas>
  </div>
</template>

<script>
import Canvas from './components/Canvas.vue'

export default {
  name: 'App',
  components: {
    Canvas
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
