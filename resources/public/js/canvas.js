var state = {
  canvas: null,
  x: 0,
  y: 0,
  drawing: false,
}

function draw(state, x, y) {
  if (!state.drawing) {
    return state
  }
 
  let ctx = state.canvas.getContext('2d')
  ctx.beginPath()
  ctx.strokeStyle = "black"
  ctx.linewidth = 3
  ctx.moveTo(state.x, state.y)
  ctx.lineTo(x, y)
  ctx.stroke()
  ctx.closePath()
  
  return {
    ...state,
    x,
    y,
  }
}

function startDraw(state, x, y) {
  return {
    ...state,
    x,
    y,
    drawing: true
  }
}

function stopDraw(state) {
  return {
    ...state,
    drawing: false
  }
}

function init() {
  state.canvas = document.getElementById("can")
  var handlers = [
    {e: "mousemove", f: (e) => { state = draw(state, e.offsetX, e.offsetY) }},
    {e: "mousedown", f: (e) => { state = startDraw(state, e.offsetX, e.offsetY) }},
    {e: "mouseup", f: (e) => { state = stopDraw(state) }},
    {e: "mouseout", f: (e) => { state = stopDraw(state) }},
    {e: "touchstart", f: (e) => {
      let touch = e.changedTouches[0]
      let canvas = state.canvas
      state = startDraw(state, touch.pageX - canvas.offsetLeft, touch.pageY - canvas.offsetTop)
    }},
    {e: "touchend", f: (e) => { state = stopDraw(state) }},
    {e: "touchcancel", f: (e) => { state = stopDraw(state) }},
    {e: "touchmove", f: (e) => {
      let touch = e.changedTouches[0]
      let canvas = state.canvas
      state = draw(state, touch.pageX - canvas.offsetLeft, touch.pageY - canvas.offsetTop)
    }}
  ]
  handlers.forEach( (obj) => {
    state.canvas.addEventListener(obj.e, obj.f, false)
  })
}

function erase() {
  let ctx = state.canvas.getContext('2d')
  ctx.clearRect(
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
  let label = document.getElementById("character").value
  let codePoint = label.codePointAt(0).toString(16) 
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
