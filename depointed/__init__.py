import flask
from . import api
from . import spa


def create_app():
    app = flask.Flask(__name__)
    app.register_blueprint(api.bp)
    app.register_blueprint(spa.bp) 
    return app
