import flask

bp = flask.Blueprint('api', __name__, url_prefix='/api')


@bp.route('/upload', methods=['POST'])
def upload():
    """upload accepts a labelled image and adds it to the data set for
    training.
    """
    return flask.Response(
        '{"msg": "Not Implemented"}',
        status=501,
        mimetype="application/json",
    )


@bp.route('/train', methods=['POST'])
def train():
    """train triggers and update of the model by training on the current data
    set.
    """
    return flask.Response(
        '{"msg": "Not Implemented"}',
        status=501,
        mimetype="application/json",
    )


@bp.route('/predict', methods=['POST'])
def predict():
    """Predict character using model and uploaded image"""
    return flask.Response(
        '{"msg": "Not Implemented"}',
        status=501,
        mimetype='application/json',
    )
