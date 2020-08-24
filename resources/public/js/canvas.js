var state = {
  canvas: null,
  ctx: null,
  x: 0,
  y: 0,
  drawing: false,
  stroke: {
    color: "black",
    width: 2
  }
}

function draw(x, y) {
  state.ctx.beginPath()
  state.ctx.strokeStyle = state.stroke.color
  state.ctx.linewidth = state.stroke.width
  state.ctx.moveTo(state.x, state.y)
  state.ctx.lineTo(x, y)
  state.ctx.stroke()
  state.ctx.closePath()
}

function move(e) {
  if (state.drawing) { 
    draw(e.offsetX, e.offsetY)
    state.x = e.offsetX
    state.y = e.offsetY
  }
  console.log(e)
}

function down(e) {
  state.drawing = true
  state.x = e.offsetX
  state.y = e.offsetY
  console.log(e)
}

function up(e) {
  state.drawing = false
  console.log(e)
}

function out(e) {
  state.drawing = false
  console.log(e)
}

function init() {
  state.canvas = document.getElementById("can")
  state.ctx = state.canvas.getContext("2d")
  var handlers = [
    {e: "mousemove", f: move},
    {e: "mousedown", f: down},
    {e: "mouseup", f: up},
    {e: "mouseout", f: out},
    {e: "touchstart", f: down},
    {e: "touchend", f: up},
    {e: "touchcancel", f: out},
    {e: "touchmove", f: move}
  ]
  handlers.forEach( (obj) => {
    state.canvas.addEventListener(obj.e, obj.f, false)
  })
}

function erase() {
  state.ctx.clearRect(
    0,
    0,
    state.canvas.width,
    state.canvas.height
  ) 
}

function predict() {
  console.log("Not implimented")
}

function upload() {
  var label = document.getElementById("character").value
  state.canvas.toBlob(
    (blob) => {
      fetch(
        `/upload?label=${label}`,
        {
          method: "POST",
          mode: "same-origin",
          headers: { "Content-Type": "image/png" },
          body: blob
        }
      ).then( response => {
        // Clear image after upload 
        erase()
      })
    },
    "image/png"
  )
}
