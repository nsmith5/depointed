import flask

bp = flask.Blueprint('spa', __name__, url_prefix='/')


@bp.route('/', methods=['GET'])
def index():
    return flask.send_from_directory('./../public/', 'index.html')


@bp.route('/<path:path>', methods=['GET'])
def spa(path):
    return flask.send_from_directory('./../public/', path) 
