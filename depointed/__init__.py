import flask

def create_app():
    app = flask.Flask(__name__)
    @app.route('/')
    def hello():
        return "Hello!"
    return app
