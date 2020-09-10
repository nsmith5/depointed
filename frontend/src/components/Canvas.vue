<template>
  <div class="canvas">
    <canvas
      ref='canvas'
      :width="width"
      :height="height"
      v-on:mousedown="mousedown"
      v-on:mousemove="mousemove"
      v-on:mouseup="mouseup"
      v-on:mouseout="mouseup"
      v-on:touchstart="touchstart"
      v-on:touchend="touchend"
      v-on:touchcancel="touchcancel"
      v-on:touchmove="touchmove"
    ></canvas>
  </div>
</template>

<script>
export default {
  name: 'Canvas',
  props: ['width', 'height'],
  data: () => {
    return {canvas: null, x: 0, y: 0, drawing: false}
  },
  methods: {
    // Drawing methods
    start(x, y) {
      this.drawing = true
      this.x = x
      this.y = y
    },
    draw(x, y) {
      let ctx = this.canvas.getContext('2d')
      ctx.beginPath()
      ctx.strokeStyle = 'black'
      ctx.lineWidth = 3
      ctx.moveTo(this.x, this.y)
      ctx.lineTo(x, y)
      ctx.stroke()
      ctx.closePath()
      this.x = x
      this.y = y
    },
    stop() {
      this.drawing = false
      this.canvas.toBlob(
        (blob) => {
          this.$emit("update:canvas", blob)
        },
        'image/png'
      )
    },
    clear() {
      let ctx = this.canvas.getContext('2d')
      ctx.clearRect(0, 0, this.canvas.width, this.canvas.height) 
    },
    
    // Mouse event handlers
    mousedown(e) {
      this.start(e.offsetX, e.offsetY)
    },
    mousemove(e) {
      if (this.drawing) {
        let x = e.offsetX
        let y = e.offsetY
        this.draw(x, y)
      }
    },
    mouseup() {
      this.stop()
    },
    touchstart(e) {
      let touch = e.changedTouches[0]
      let x = touch.pageX - this.canvas.offsetLeft
      let y = touch.pageY - this.canvas.offsetTop
      this.start(x, y)
    },

    // Touch even handlers
    touchend() {
      this.stop()
    },
    touchcancel() {
      this.stop()
    },
    touchmove(e) {
      let touch = e.changedTouches[0]
      let x = touch.pageX - this.canvas.offsetLeft
      let y = touch.pageY - this.canvas.offsetTop
      this.draw(x, y)
    }
  },
  mounted() {
      this.canvas = this.$refs.canvas
  }
}
</script>

<style scoped>
canvas {
  border: 2px gray solid;
  border-radius: 5px;
}
</style>
